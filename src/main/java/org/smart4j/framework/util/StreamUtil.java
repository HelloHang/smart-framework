package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class StreamUtil
{
	private static final Logger LOG = LoggerFactory.getLogger(StreamUtil.class);

	public static String getString(InputStream inputStream)
	{
		StringBuilder builder = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = reader.readLine()) != null)
			{
				builder.append(line);
			}
		}
		catch (Exception e)
		{
			LOG.error("Get String from inputstream failure", e);
			throw new RuntimeException(e);
		}
		return builder.toString();
	}
}
