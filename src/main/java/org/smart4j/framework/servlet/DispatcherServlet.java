package org.smart4j.framework.servlet;

import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.constant.RequestMethod;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = "/", loadOnStartup = 0) public class DispatcherServlet extends HttpServlet
{
	@Override public void init(ServletConfig config) throws ServletException
	{
		ServletContext servletContext = config.getServletContext();
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath().concat("*"));
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath().concat("*"));
	}

	@Override protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		RequestMethod requestMethod = RequestMethod.valueOf(req.getMethod().toUpperCase());
		String requestPath = req.getPathInfo();
		Handler handler = ControllerHelper.getHandler(requestPath, requestMethod);
		if (handler != null)
		{
			Class<?> controllerClass = handler.getControllerClass();
			Object controllerBean = BeanHelper.getBeanByClass(controllerClass);
			//创建请求参数
			Map<String, Object> paramMap = new HashMap<>();
			Enumeration<String> paramNames = req.getParameterNames();
			while (paramNames.hasMoreElements())
			{
				String paramName = paramNames.nextElement();
				String paramValue = req.getParameter(paramName);
				paramMap.put(paramName, paramValue);
			}

			String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
			if (StringUtil.isNotEmpty(body))
			{
				String[] params = StringUtil.split(body, "&");
				if (ArrayUtil.isNotEmpty(params))
				{
					for (String param : params)
					{
						String[] keyAndValue = StringUtil.split(param, "=");
						if (ArrayUtil.isNotEmpty(keyAndValue) && keyAndValue.length == 2)
						{
							paramMap.put(keyAndValue[0], keyAndValue[1]);
						}
					}
				}
			}
			Param param = new Param(paramMap);
			Method actionMethod = handler.getActionMethod();
			Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
			if (result instanceof View)
			{
				View view = (View) result;
				String path = view.getPath();
				if (StringUtil.isNotEmpty(path))
				{
					if (path.startsWith("/"))
					{
						resp.sendRedirect(req.getContextPath().concat(path));
					}
					else
					{
						Map<String, Object> model = view.getModel();
						for (Map.Entry<String, Object> entry : model.entrySet())
						{
							req.setAttribute(entry.getKey(), entry.getValue());
						}
						req.getRequestDispatcher(ConfigHelper.getAppJspPath().concat(path)).forward(req, resp);
					}
				}
			}
			else if (result instanceof Data)
			{
				Data model = (Data) result;
				Object object = model.getModel();
				if(object != null){
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					PrintWriter writer = resp.getWriter();
					writer.write(JsonUtil.toJson(object));
					writer.flush();
					writer.close();
				}
			}

		}

	}
}
