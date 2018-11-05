package org.smart4j.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.util.ReflectionUtil;

/**
 * @author: Daniels Gao
 * @date: 2018/11/5 21:20
 */
public class BeanHelper {

  private static final Logger LOG = LoggerFactory.getLogger(BeanHelper.class);
  private static final Map<Class<?>, Object> BEAN_MAP = new HashMap();

  static {
    Set<Class<?>> beanClasses = ClassHelper.getAllBeanClasses();
    beanClasses.forEach(c -> {
      Object obj = ReflectionUtil.newInstance(c);
      BEAN_MAP.put(c, obj);
    });
  }

  public static Map<Class<?>, Object> getAllBeans(){
    return BEAN_MAP;
  }

  public static Object getBeanByClass(Class<?> cls){
    if(!BEAN_MAP.containsKey(cls)){
      throw  new RuntimeException(String.format("Can't get bean by class[%s]", cls.getName()));
    }
    return BEAN_MAP.get(cls);
  }
}
