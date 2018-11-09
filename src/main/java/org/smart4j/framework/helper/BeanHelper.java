package org.smart4j.framework.helper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;
import org.smart4j.framework.util.ReflectionUtil;


/**
 * @author: Daniels Gao
 * @date: 2018/11/5 21:20
 */
public class BeanHelper
{

	private static final Logger LOG = LoggerFactory.getLogger(BeanHelper.class);
	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap();

	static
	{
		Set<Class<?>> beanClasses = ClassHelper.getAllBeanClasses();
		beanClasses.forEach(c -> {
			Object obj = ReflectionUtil.newInstance(c);
			BEAN_MAP.put(c, obj);
		});

		//AOP
		Map<Class<?>, List<Proxy>> targetMap = AopHelper.createTargetMap();
		for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet())
		{
			Object proxy = ProxyManager.createProxy(entry.getKey(), entry.getValue());
			setBean(entry.getKey(), proxy);
		}

		//注入依赖对象
		for (Map.Entry<Class<?>, Object> entry : BEAN_MAP.entrySet())
		{
			Class<?> cls = entry.getClass();
			Object bean = entry.getValue();
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields)
			{
				if (field.isAnnotationPresent(Inject.class))
				{
					Class<?> fieldType = field.getType();
					Object fieldBean = BEAN_MAP.get(fieldType);
					if (fieldBean != null)
					{
						ReflectionUtil.setField(bean, field, fieldBean);
					}
				}
			}
		}
	}

	public static Map<Class<?>, Object> getAllBeans()
	{
		return BEAN_MAP;
	}

	public static Object getBeanByClass(Class<?> cls)
	{
		if (!BEAN_MAP.containsKey(cls))
		{
			LOG.error(String.format("Can't get bean by class[%s]", cls.getName()));
			throw new RuntimeException(String.format("Can't get bean by class[%s]", cls.getName()));
		}
		return BEAN_MAP.get(cls);
	}

	public static void setBean(Class<?> cls, Object object)
	{
		BEAN_MAP.put(cls, object);
	}
}
