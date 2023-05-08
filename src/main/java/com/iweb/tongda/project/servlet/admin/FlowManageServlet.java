package com.iweb.tongda.project.servlet.admin;

import com.iweb.tongda.project.bean.Catalog;
import com.iweb.tongda.project.bean.Flow;
import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.bean.UpLoadImg;
import com.iweb.tongda.project.dao.CatalogDao;
import com.iweb.tongda.project.dao.FlowDao;
import com.iweb.tongda.project.dao.UpLoadImgDao;
import com.iweb.tongda.project.dao.impl.CatalogDaoImpl;
import com.iweb.tongda.project.dao.impl.FlowDaoImpl;
import com.iweb.tongda.project.dao.impl.UpLoadImgDaoImpl;
import com.iweb.tongda.project.util.DateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-08 10:49
 */
@WebServlet("/jsp/admin/FlowManageServlet")
//@MultipartConfig  //文件和参数一起提交到servlet要写这个,不然会null
public class FlowManageServlet extends HttpServlet {
    private static final int MAX_PAGE_SIZE = 8;
    private static final String FLOWLIST_PATH = "flowManage/flowList.jsp";
    private static final String FLOWDETAIL_PATH = "flowManage/flowDetail.jsp";
    private static final String FLOWADD_PATH = "flowManage/flowAdd.jsp";
    private static final String FLOWUPDATE_PATH = "flowManage/flowEdit.jsp";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取行为
        String action = request.getParameter("action");
        switch (action) {
            case "list":
                flowList(request,response);
                break;
            case "detail":
                flowDetail(request,response);
                break;
            case "addReq":
                flowAddReq(request,response);
                break;
            case "add":
                flowAdd(request,response);
                break;
            case "find":
                flowAjaxUrlFind(request,response);
                break;
            case "edit":
                flowEdit(request,response);
                break;
            case "updateImg":
                flowUpdateImg(request,response);
                break;
            case "update":
                flowUpdate(request,response);
                break;
            case "del":
                flowDel(request,response);
                break;
            case "batDel":
                flowBatDel(request,response);
                break;
            case "search":
                flowSearchByLike(request,response);
                break;
        }
    }

    /**
     * 模糊查询
     * @param request
     * @param response
     */
    private void flowSearchByLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传过来的参数
        String flowName = request.getParameter("flowname");
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }

        //创建FlowDao对象
        FlowDao flowDao = new FlowDaoImpl();
        if (flowName != null && !"".equals(flowName)){
            //鲜花名称不为空则进行模糊查询
            //创建分页对象
            PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,flowDao.readFlowCountByLike(flowName));
            //根据分页对象进行模糊查询
            List<Flow> flowByLike = flowDao.findFlowByLike(pageBean, flowName);
            //将信息存入request域中
            request.setAttribute("flowList",flowByLike);
            request.setAttribute("pageBean",pageBean);
            //转发到鲜花列表界面
            request.getRequestDispatcher(FLOWLIST_PATH).forward(request,response);
        }else {
            //鲜花名称为空则调用鲜花列表函数
            flowList(request,response);
        }
    }

    /**
     * 批量删除
     * @param request
     * @param response
     */
    private void flowBatDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传来的鲜花ids
        String ids = request.getParameter("ids");
        String[] idsArr = ids.split(",");
        int[] idsArrInt = new int[idsArr.length];
        for (int i = 0; i < idsArrInt.length; i++) {
            idsArrInt[i] = Integer.parseInt(idsArr[i]);
        }
        //创建FlowDao对象
        FlowDao flowDao = new FlowDaoImpl();
        //设置删除成功状态判断标识
        boolean flag = true;
        for (int flowId : idsArrInt) {
            if (!flowDao.deleteFlowById(flowId)){
                flag = false;
            }
        }
        if (flag) {
            //批量删除成功
            //将提示信息存入request域中
            request.setAttribute("flowMessage","商品鲜花批量删除成功~");
            //转发到列表页面，因为图片要刷新
            flowList(request, response);
        }else {
            //将提示信息存入request域中
            request.setAttribute("flowMessage","商品鲜花批量删除失败!");
            //转发到列表页面，因为图片要刷新
            flowList(request, response);
        }
    }

    /**
     * 删除单个鲜花
     * @param request
     * @param response
     */
    private void flowDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传来的鲜花id
        int flowId = Integer.parseInt(request.getParameter("id"));
        //创建FlowDao对象
        FlowDao flowDao = new FlowDaoImpl();
        if (flowDao.deleteFlowById(flowId)){
            //删除成功
            //将提示信息存入request域中
            request.setAttribute("flowMessage","商品鲜花删除成功~");
            //转发到列表页面，因为图片要刷新
            flowList(request, response);
        }else {
            //将提示信息存入request域中
            request.setAttribute("flowMessage","商品鲜花删除失败!");
            //转发到列表页面，因为图片要刷新
            flowList(request, response);
        }
    }

    /**
     * 修改鲜花信息
     * @param request
     * @param response
     */
    private void flowUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台表单信息,并封装成表单对象
        Flow flow = new Flow(
                Integer.parseInt(request.getParameter("flowId")),
                Double.parseDouble(request.getParameter("price")),
                request.getParameter("desc"),
                Integer.parseInt(request.getParameter("catalog")),
                request.getParameter("keywords")
        );
        //创建FlowDao对象
        FlowDao flowDao = new FlowDaoImpl();
        if (flowDao.updateFlow(flow)){
            //修改成功
            //将提示信息存入request域中
            request.setAttribute("flowMessage","商品信息修改成功~");
            //转发到列表页面，因为图片要刷新
            flowList(request, response);
        }else {
            //修改失败
            //将提示信息存入request域中
            request.setAttribute("flowMessage","商品信息修改失败!");
            //转发到列表页面，因为图片要刷新
            flowList(request, response);
        }

    }

    /**
     * 在修改鲜花信息页面修改鲜花图片
     * @param request
     * @param response
     */
    private void flowUpdateImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用文件上传方法得到鲜花id和图片对象
        getUploadImg(request, response);
        UpLoadImg upLoadImg = (UpLoadImg) request.getAttribute("upLoadImg");
        //获取鲜花参数
        List<String> flowParams = (List<String>) request.getAttribute("flowParams");
        //int flowId = Integer.parseInt(flowParams.get(0));
        System.out.println(flowParams.size());
        int flowId = Integer.parseInt(request.getParameter("id"));
        //将图片对象存入数据库中,创建UpLoadImgDao对象
        UpLoadImgDao uploadImgDao = new UpLoadImgDaoImpl();
        uploadImgDao.addImg(upLoadImg);
        //根据uploadImg查询到对应图片id
        int imgId = uploadImgDao.getImgId(upLoadImg);
        //根据鲜花id修改对应鲜花的图片id
        //创建FlowDao对象
        FlowDao flowDao = new FlowDaoImpl();
        if (flowDao.updateImgIdAtFlow(flowId,imgId)){
            //修改成功
            //将提示信息存入request域中
            request.setAttribute("flowMessage","商品图片修改成功~");
            //转发到列表页面，因为图片要刷新
            flowList(request, response);
        }else {
            //修改失败
            //将提示信息存入request域中
            request.setAttribute("flowMessage","商品图片修改失败！");
            //转发到修改页面
            flowList(request, response);
        }
    }

    /**
     * 鲜花信息修改
     * @param request
     * @param response
     */
    private void flowEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传来的鲜花id
        int flowId = Integer.parseInt(request.getParameter("id"));
        //创建FlowDao对象
        FlowDao flowDao = new FlowDaoImpl();
        //这里直接将图片信息对象一起封装好了
        Flow flowById = flowDao.findFlowById(flowId);
        //创建CatalogDao对象
        CatalogDao catalogDao = new CatalogDaoImpl();

        //获取分类列表
        List<Catalog> catalog = catalogDao.getCatalog();
        //获取当前鲜花的分类信息
        Catalog curCatalog = flowDao.getCatalogByFlowId(flowId);

        //将鲜花分类对象装进鲜花对象
        flowById.setCatalog(curCatalog);
        //将鲜花信息和鲜花分类列表信息存入request域中
        request.setAttribute("flowInfo",flowById);
        request.setAttribute("catalog",catalog);
        //转发到鲜花修改页面
        request.getRequestDispatcher(FLOWUPDATE_PATH).forward(request, response);
    }

    /**
     * 验证鲜花名称是否可用
     * @param request
     * @param response
     */
    private void flowAjaxUrlFind(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取前台传递来的鲜花名是否存在
        //获取参数
        String flowname = request.getParameter("param");
        //获取键名
        String name = request.getParameter("name");
        //将鲜花名传入dao调用方法
        FlowDao flowDao = new FlowDaoImpl();

        //创建Json对象
        JSONObject json = new JSONObject();
        if (flowname == null || "".equals(flowname)) {
            json.put("info", "other");
        } else {
            if (flowDao.findFlowByFlowname(flowname)) {
                json.put("info", "鲜花名已存在");
                json.put("status","n");
            } else {
                json.put("info", "鲜花名可以使用");
                json.put("status","y");
            }
        }
        //设置格式和编码
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json.toString());
    }

    /**
     * 添加鲜花商品
     * @param request
     * @param response
     */
    private void flowAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台表单提交的数据
        //处理上传的图片和参数
        getUploadImg(request, response);
        //从域中取出uploadImg
        UpLoadImg upLoadImg = (UpLoadImg) request.getAttribute("upLoadImg");
        //从域中取出鲜花参数集合
        List<String> flowParams = (List<String>) request.getAttribute("flowParams");
        //从集合中拿到鲜花的值并装进Flow对象里
        String flowName = flowParams.get(0);
        int catalog = Integer.parseInt(flowParams.get(1));
        double price = Double.parseDouble(flowParams.get(2));
        String keywords = flowParams.get(3);
        String desc = flowParams.get(4);
        //将图片对象存入数据库中,创建UpLoadImgDao对象
        UpLoadImgDao uploadImgDao = new UpLoadImgDaoImpl();
        uploadImgDao.addImg(upLoadImg);
        //根据uploadImg查询到对应图片id
        int imgId = uploadImgDao.getImgId(upLoadImg);
        //封装成鲜花对象
        Flow flow = new Flow(flowName,price,desc,catalog,keywords);
        //设置添加时间
        flow.setAddTime(DateUtil.getTimestamp());
        //还需要把当前文件的图片id存进去
        flow.setImgId(imgId);
        //创建FlowDao对象
        FlowDao flowDao = new FlowDaoImpl();
        if (flowDao.addFlow(flow)){
            //添加成功
            //将提示信息存入request域中
            request.setAttribute("flowMessage","商品添加成功~");
            //转发到花卉页面,调用列表函数
            flowList(request, response);
        }else {
            //添加失败
            //将提示信息存入request域中
            request.setAttribute("flowMessage","商品添加失败！");
            //转发到花卉页面
            flowList(request, response);
        }
    }

    /**
     * 获取前台上传的图片
     * @param request
     * @param response
     */
    private void getUploadImg(HttpServletRequest request, HttpServletResponse response){
        try {
            // 先获取到要上传的文件目录
            String path = request.getSession().getServletContext().getRealPath("/images/flow/flowimg");
            System.out.println(path);
            // 创建File对象，一会向该路径下上传文件
            File file = new File(path);
            // 判断路径是否存在，如果不存在，创建该路径
            if(!file.exists()) {
                file.mkdirs();
            }
            // 创建磁盘文件项工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            //创建UpLoadImg对象
            UpLoadImg upLoadImg = new UpLoadImg();
            List<String> flowParams = new ArrayList<>();
            List<FileItem> list = null;

            // 解析request对象
            list = fileUpload.parseRequest(request);
            // 遍历
            for (FileItem fileItem : list) {
                // 判断文件项是普通字段，还是上传的文件
                if(fileItem.isFormField()) {
                    // 普通表单项, 当 enctype="multipart/form-data"时, request的getParameter()方法 无法获取参数
                    String fieldName = fileItem.getFieldName(); // 获取表单文本框中name的属性值
                    String value = fileItem.getString("utf-8"); // 获取utf-8编码之后表单文本框中的内容
                    System.out.println(fieldName + " = " + value);
                    flowParams.add(value);
                }else {
                    // 上传文件项
                    // 获取到上传文件的名称
                    String filename = fileItem.getName();
                    System.out.println(filename);
                    // 上传文件
                    fileItem.write(new File(file, filename));
                    // 删除临时文件
                    fileItem.delete();
                    //传到webapp下
                    FileInputStream fileInputStream = new FileInputStream(path+"/" +filename);
                    String absolutePath = "D:\\airui\\studydocuments\\servlet_shop\\src\\main\\webapp\\images\\flow\\flowimg";
                    FileOutputStream fileOutputStream = new FileOutputStream(absolutePath+"/" + filename);
                    byte[] bytes = new byte[1024];
                    int data;
                    while ((data = fileInputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes,0,data);
                        System.out.println("上传完毕,已经到webapp下了~");
                    }
                    //为uploadImg赋值
                    upLoadImg.setImgName(filename);
                    upLoadImg.setImgSrc("images/flow/flowimg/"+filename);
                    upLoadImg.setImgType(this.getServletContext().getMimeType(filename));
                }
                //将upLoadImg存到request域中并转发到添加servlet
                request.setAttribute("upLoadImg",upLoadImg);
                //将flowParams存入request域中
                request.setAttribute("flowParams",flowParams);
            }
        } catch (Exception e) {
            throw new RuntimeException("上传servlet这边有问题!!");
        }
    }

    /**
     * 添加鲜花商品的请求(附带分类鲜花信息跳转添加页面)
     * @param request
     * @param response
     */
    private void flowAddReq(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //创建CatalogDao对象
        CatalogDao catalogDao = new CatalogDaoImpl();
        //存入request域中
        request.setAttribute("catalog",catalogDao.getCatalog());
        //转发到添加鲜花商品页面
        request.getRequestDispatcher(FLOWADD_PATH).forward(request, response);
    }

    /**
     * 查看鲜花详情
     * @param request
     * @param response
     */
    private void flowDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传来的鲜花id
        int flowId = Integer.parseInt(request.getParameter("id"));
        //通过id查询到数据库鲜花信息
        //创建FlowDao对象
        FlowDao flowDao = new FlowDaoImpl();
        //存入request域中
        request.setAttribute("flowInfo",flowDao.findFlowById(flowId));
        //转发到鲜花详情页面
        request.getRequestDispatcher(FLOWDETAIL_PATH).forward(request, response);
    }

    /**
     * 鲜花列表
     * @param request
     * @param response
     */
    private void flowList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置分页对象
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建FlowDao对象
        FlowDao flowDao = new FlowDaoImpl();
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,flowDao.allFlowList());

        //将数据存入request域中
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("flowList",flowDao.flowList(pageBean));
        //转发到鲜花列表界面
        request.getRequestDispatcher(FLOWLIST_PATH).forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
