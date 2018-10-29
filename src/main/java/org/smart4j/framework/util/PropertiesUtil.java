package org.smart4j.framework.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Daniels Gao
 * @date: 2018/10/29 20:17
 */
public class PropertiesUtil {

  private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtil.class);

  public static Properties loadProperties(final String fileName) {
    InputStream in = null;
    Properties properties = new Properties();
    try {
      in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
      if (in == null) {
        throw new FileNotFoundException(String.format("File[%s] is not found!", fileName));
      }
      properties.load(in);
    } catch (IOException e) {
      LOG.error("Load properties file failed!", e);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          LOG.error("Close input stream failed!", e);
        }
      }
    }
    return properties;
  }

  public static String getString(final Properties properties, final String key) {
    return getString(properties, key, StringUtils.EMPTY);
  }

  public static String getString(final Properties properties, final String key,
      final String defaultValue) {
    String value = defaultValue;
    if (properties.containsKey(key)) {
      value = properties.getProperty(key);
    }
    return value;
  }

  public static int getInt(final Properties properties, final String key) {
    return getInt(properties, key, 0);
  }

  public static int getInt(final Properties properties, final String key, final int defaultValue) {
    int value = defaultValue;
    if (properties.containsKey(key)) {
      value = CastUtil.castInt(properties.getProperty(key));
    }
    return value;
  }

  public static boolean getBoolean(final Properties properties, final String key) {
    return getBoolean(properties, key, Boolean.FALSE);
  }

  public static boolean getBoolean(final Properties properties, final String key,
      final Boolean defaultValue) {
    boolean value = defaultValue;
    if (properties.containsKey(key)) {
      value = CastUtil.castBoolean(properties.getProperty(key));
    }
    return value;
  }

}
