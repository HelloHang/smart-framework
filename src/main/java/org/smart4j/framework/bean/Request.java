package org.smart4j.framework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.smart4j.framework.constant.RequestMethod;



public class Request
{


	public Request(RequestMethod requestMethod, String requestPath)
	{
		this.requestMethod = requestMethod;
		this.requestPath = requestPath;
	}

	private RequestMethod requestMethod;

	private String requestPath;

	@Override public boolean equals(Object o)
	{
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override public int hashCode()
	{

		return HashCodeBuilder.reflectionHashCode(this);
	}

	public RequestMethod getRequestMethod()
	{
		return requestMethod;
	}

	public void setRequestMethod(RequestMethod requestMethod)
	{
		this.requestMethod = requestMethod;
	}

	public String getRequestPath()
	{
		return requestPath;
	}

	public void setRequestPath(String requestPath)
	{
		this.requestPath = requestPath;
	}

}
