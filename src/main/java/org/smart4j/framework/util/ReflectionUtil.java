package org.smart4j.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Daniels Gao
 * @date: 2018/11/5 21:08
 */
public class ReflectionUtil {

  private static final Logger LOG = LoggerFactory.getLogger(ReflectionUtil.class);

  public static Object newInstance(Class<?> cls) {
    Object object;
    try {
      object = cls.newInstance();
    } catch (Exception e) {
      LOG.error(String.format("Create instance failure with class[%s]", cls.getName()), e);
      throw new RuntimeException(e);
    }
    return object;
  }

  public static Object invokeMethod(Object object, Method method, Object... args) {
    Object result;
    try {
      result = method.invoke(object, args);
    } catch (Exception e) {
      LOG.error(String
          .format("Object[%s] invoke method[%s] failure", object.getClass(), method.getName()), e);
      throw new RuntimeException(e);
    }
    return result;
  }

  public static void setField(Object object, Field field, Object value) {
    try {
      field.set(object, value);
    } catch (Exception e) {
      LOG.error(String.format("Object[%s] set field[%s] with value[%s] failure.", object.getClass(),
          field.getName(), value), e);
      throw new RuntimeException(e);
    }
  }
}
