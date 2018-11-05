package org.smart4j.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ReflectionUtil;

/**
 * @author: Daniels Gao
 * @date: 2018/11/5 21:31
 */
public class IOCHelper {

  static {
    Map<Class<?>, Object> beans = BeanHelper.getAllBeans();
    for (Map.Entry<Class<?>, Object> entry : beans.entrySet()) {
      Class<?> cls = entry.getClass();
      Object bean = entry.getValue();
      Field[] fields = cls.getDeclaredFields();
      for (Field field : fields) {
        if(field.isAnnotationPresent(Inject.class)){
          Class<?> fieldType = field.getType();
          Object fieldBean =beans.get(fieldType);
          if (fieldBean != null){
            ReflectionUtil.setField(bean, field, fieldBean);
          }
        }
      }
    }
  }
}
