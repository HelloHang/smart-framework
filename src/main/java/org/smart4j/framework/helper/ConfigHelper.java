package org.smart4j.framework.helper;

import java.util.Properties;
import org.smart4j.framework.ConfigConstant;
import org.smart4j.framework.ConfigConstant.APP;
import org.smart4j.framework.ConfigConstant.JDBC;
import org.smart4j.framework.util.PropertiesUtil;

/**
 * @author: Daniels Gao
 * @date: 2018/10/29 20:12
 */
public final class ConfigHelper {

  private static final String DEFAULT_APP_JSP_PATH = "/WEB-INF/view/";
  private static final String DEFAULT_APP_ASSET_PATH = "/asset/";
  private static final Properties config = PropertiesUtil
      .loadProperties(ConfigConstant.CONFIG_FILE);

  public static String getJdbcDriver() {
    return PropertiesUtil.getString(config, JDBC.DRIVER);
  }

  public static String getJdbcUrl() {
    return PropertiesUtil.getString(config, JDBC.URL);
  }

  public static String getJdbcUserName() {
    return PropertiesUtil.getString(config, JDBC.UER_NAME);
  }

  public static String getJdbcPassword() {
    return PropertiesUtil.getString(config, JDBC.PASSWORD);
  }

  public static String getAppBasePackage() {
    return PropertiesUtil.getString(config, APP.BASE_PACKAGE);
  }

  public static String getAppJspPath() {
    return PropertiesUtil.getString(config, APP.JSP_PATH, DEFAULT_APP_JSP_PATH);
  }

  public static String getAppAssetPath() {
    return PropertiesUtil.getString(config, APP.ASSET_PATH, DEFAULT_APP_ASSET_PATH);
  }
}
