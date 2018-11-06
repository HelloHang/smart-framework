package org.smart4j.framework.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonUtil
{
	private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static <T> String toJson(T t)
	{
		String json;
		try
		{
			json = OBJECT_MAPPER.writeValueAsString(t);
		}
		catch (Exception e)
		{
			LOG.error("Convert object to json failure.", e);
			throw new RuntimeException(e);
		}
		return json;
	}

	public static <T> T fromJson(String json, Class<T> type)
	{
		T object;
		try
		{
			object = OBJECT_MAPPER.readValue(json, type);
		}
		catch (Exception e)
		{
			LOG.error("Convert json to pojo failure", e);
			throw new RuntimeException(e);
		}
		return object;
	}
}
