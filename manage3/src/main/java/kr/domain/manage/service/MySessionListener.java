package kr.domain.manage.service;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

import kr.domain.manage.vo.CategoryVO;

/**
 * Application Lifecycle Listener implementation class MySessionListener
 *
 */

public class MySessionListener implements HttpSessionListener, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(MySessionListener.class);
	
    /**
     * Default constructor. 
     */
    public MySessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  {
    	logger.info("********MySessionListener - SessionCreated");
         se.getSession().setAttribute("grpList", getCategoryService(se).selectGrp());
         se.getSession().setAttribute("seqList", getCategoryService(se).selectSeq());
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	logger.info("********MySessionListener - sessionDestroyed ");
    }
    
    
    private CategoryService getCategoryService(HttpSessionEvent se) {
    	WebApplicationContext context = 
			      WebApplicationContextUtils.getWebApplicationContext(se.getSession().getServletContext(),
			    		  FrameworkServlet.SERVLET_CONTEXT_PREFIX+"appServlet");
		return context.getBean(CategoryService.class);
    }
	
}
