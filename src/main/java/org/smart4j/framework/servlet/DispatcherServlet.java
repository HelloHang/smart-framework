package org.smart4j.framework.servlet;

import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.constant.RequestMethod;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = "/", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet
{
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		ServletContext servletContext = config.getServletContext();
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath().concat("*"));
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath().concat("*"));
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		RequestMethod  requestMethod = RequestMethod.valueOf(req.getMethod().toUpperCase());
		String requestPath = req.getPathInfo();
		Handler handler = ControllerHelper.getHandler(requestPath, requestMethod);
		if(handler != null){
			Class<?> controllerClass = handler.getControllerClass();
			Object object = BeanHelper.getBeanByClass(controllerClass);
			//创建请求参数
			Map<String, Object> param = new HashMap<>();
			Enumeration<String> paramNames = req.getParameterNames();
			while (paramNames.hasMoreElements()){
				String paramName = paramNames.nextElement();
				String paramValue = req.getParameter(paramName);
				param.put(paramName, paramValue);
			}


			req.getInputStream();
		}

	}
}
