package org.smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author: Daniels Gao
 * @date: 2018/7/3 21:57
 */
public class StringUtil {

  public static boolean isEmpty(String str) {
    if (str != null) {
      str = str.trim();
    }
    return StringUtils.isEmpty(str);
  }

  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  public static String replace(String origin, String target, String replacement) {
    return StringUtils.replace(origin, target, replacement);
  }
}
