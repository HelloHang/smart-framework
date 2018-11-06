package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;
import org.smart4j.framework.constant.RequestMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ControllerHelper
{
	private static final Map<Request, Handler> ACTION_MAP = new HashMap();

	static
	{
		Set<Class<?>> controllerClasses = ClassHelper.getControllerClasses();
		for (Class<?> cls : controllerClasses)
		{
			Method[] methods = cls.getDeclaredMethods();
			for (Method method : methods)
			{
				if (method.isAnnotationPresent(Action.class))
				{
					Action action = method.getAnnotation(Action.class);
					String path = action.value();
					RequestMethod requestMethod = action.method();
					Request request = new Request(requestMethod, path);
					Handler handler = new Handler(cls, method);
					ACTION_MAP.put(request, handler);
				}
			}
		}

	}

	public static Handler getHandler(String requestPath, RequestMethod requestMethod){
		Request request = new Request(requestMethod, requestPath);
		return ACTION_MAP.get(request);
	}
}
