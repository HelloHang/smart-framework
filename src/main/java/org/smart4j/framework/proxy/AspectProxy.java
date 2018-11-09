package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


public class AspectProxy implements Proxy
{
	private static final Logger LOG = LoggerFactory.getLogger(AspectProxy.class);

	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable
	{
		Object result;

		Class<?> targetClass = proxyChain.getClass();
		Method targetMethod = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();

		begin();

		try{
			if(intercept(targetClass, targetMethod, params)){
				before(targetClass, targetMethod, params);
				result = proxyChain.doProxyChain();
				after(targetClass, targetMethod, params, result);
			}
			else {
				result = proxyChain.doProxyChain();
			}
		}catch (Exception e){
			LOG.error("Proxy failed.", e);
			error(targetClass, targetMethod, params, e);
			throw e;
		}finally
		{
			end();
		}
		return result;
	}

	public void end()
	{
	}

	public void error(Class<?> targetClass, Method targetMethod, Object[] params, Exception e)
	{
	}

	public void after(Class<?> targetClass, Method targetMethod, Object[] params, Object result)
	{
	}

	public void before(Class<?> targetClass, Method targetMethod, Object[] params)
	{
	}

	public boolean intercept(Class<?> targetClass, Method targetMethod, Object[] params)
	{
		return true;
	}

	public void begin()
	{
	}
}
