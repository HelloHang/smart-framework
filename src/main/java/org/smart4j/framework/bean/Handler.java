package org.smart4j.framework.bean;

import java.lang.reflect.Method;


public class Handler
{

	public Handler(Class<?> controllerClass, Method actionMethod)
	{
		this.controllerClass = controllerClass;
		this.actionMethod = actionMethod;
	}

	private Method actionMethod;

	private Class<?> controllerClass;

	public Class<?> getControllerClass()
	{
		return controllerClass;
	}

	public void setControllerClass(Class<?> controllerClass)
	{
		this.controllerClass = controllerClass;
	}

	public Method getActionMethod()
	{
		return actionMethod;
	}

	public void setActionMethod(Method actionMethod)
	{
		this.actionMethod = actionMethod;
	}
}
