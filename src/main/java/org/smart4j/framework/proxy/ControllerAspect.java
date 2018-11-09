package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;

import java.lang.reflect.Method;


@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy
{
	private static final Logger LOG = LoggerFactory.getLogger(ControllerAspect.class);
	private long begin;

	@Override
	public void before(Class<?> targetClass, Method targetMethod, Object[] params)
	{
		LOG.debug("--------------begin-------------------");
		begin = System.currentTimeMillis();
	}

	@Override public void after(Class<?> targetClass, Method targetMethod, Object[] params, Object result)
	{
		LOG.debug(String.format("spend time: %s", System.currentTimeMillis() - begin));
		LOG.debug("--------------end-------------------");
	}

}
