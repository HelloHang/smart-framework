package org.smart4j.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author: Daniels Gao
 * @date: 2018/11/5 21:43
 */
public class ArrayUtil {

  public static boolean isNotEmpty(Object[] array) {
    return ArrayUtils.isNotEmpty(array);
  }

  public static boolean isEmpty(Object[] array) {
    return ArrayUtils.isEmpty(array);
  }
}
