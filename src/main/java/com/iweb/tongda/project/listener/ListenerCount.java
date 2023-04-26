package com.iweb.tongda.project.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import  com.iweb.tongda.project.bean.Admin;
import com.iweb.tongda.project.bean.Admin;
import com.iweb.tongda.project.util.DateUtil;
import  com.iweb.tongda.project.util.DateUtil;

/**
 * Application Lifecycle Listener implementation class ListenerCount
 *
 */
@WebListener
public class ListenerCount implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
	ServletContext application;

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    @Override
	public void sessionCreated(HttpSessionEvent se)  {
    	//记录系统访问的人数；
        Integer vCount=(Integer) this.application.getAttribute("vCount");
         vCount++;
         this.application.setAttribute("vCount", vCount);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    @Override
	public void sessionDestroyed(HttpSessionEvent se)  {
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
	public void contextDestroyed(ServletContextEvent sce)  {
         
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    @Override
	public void attributeAdded(HttpSessionBindingEvent se)  {
    	List<String> olUser=(List<String>) this.application.getAttribute("olUser");
          String name=se.getName();
          if("userInfo".equals(name)){
        	  Admin user=(Admin) se.getValue();
        	  olUser.add(user.getUserName());
        	  this.application.setAttribute("olUser", olUser);
        	  System.out.println(DateUtil.show()+">>> 用户："+user.getUserName()+"登录");
          }
          
    }
    
	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    @Override
	public void attributeRemoved(HttpSessionBindingEvent se)  {
    	List<String> olUser=(List<String>) this.application.getAttribute("olUser");
    	String name=se.getName();
    	if("userInfo".equals(name)){
    		Admin user=(Admin) se.getValue();
    		if(olUser.contains(user.getUserName())){
    			olUser.remove(user.getUserName());
    			this.application.setAttribute("olUser", olUser);
    		}
    		System.out.println(DateUtil.show()+">>> 用户："+user.getUserName()+"退出登录");
    	}
    	
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    @Override
	public void attributeReplaced(HttpSessionBindingEvent se)  {
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @Override
	public void contextInitialized(ServletContextEvent sce)  {
    	this.application=sce.getServletContext();
        this.application.setAttribute("vCount", 0);
        this.application.setAttribute("olUser", new ArrayList<String>());
        
    }
	
}
