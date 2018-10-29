package org.smart4j.framework;

/**
 * @author: Daniels Gao
 * @date: 2018/10/29 20:05
 */
public interface ConfigConstant {

  String CONFIG_FILE = "smart.properties";

  interface JDBC {

    String DRIVER = "smart.framework.jdbc.driver";

    String URL = "smart.framework.jdbc.url";

    String UER_NAME = "smart.framework.jdbc.username";

    String PASSWORD = "smart.framework.jdbc.password";
  }

  interface APP {

    String BASE_PACKAGE = "smart.framework.app.base.package";

    String JSP_PATH = "smart.framework.app.jsp.path";
    
    String ASSET_PATH = "smart.framework.app.asset.path";
  }

}
