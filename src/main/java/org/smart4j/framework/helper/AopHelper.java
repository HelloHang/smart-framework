package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.util.*;


public final class AopHelper
{
	private static Set<Class<?>> createTargetClasses(Aspect aspect)
	{
		Class<? extends Annotation> annotation = aspect.value();
		Set<Class<?>> targetClasses = new HashSet<>();
		if (annotation != null && !Objects.equals(annotation, Aspect.class))
		{
			targetClasses.addAll(ClassHelper.getClassesByAnnotation(annotation));
		}
		return targetClasses;
	}

	private static Map<Class<?>, Set<Class<?>>> createProxyMap()
	{
		Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
		Set<Class<?>> proxySet = ClassHelper.getSubClassBySuper(Aspect.class);
		for (Class<?> proxyClass : proxySet)
		{
			if (proxyClass.isAnnotationPresent(Aspect.class))
			{
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				Set<Class<?>> targetClasses = createTargetClasses(aspect);
				proxyMap.put(proxyClass, targetClasses);
			}
		}
		return proxyMap;
	}

	public static Map<Class<?>, List<Proxy>> createTargetMap()
	{
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
		Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
		for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet())
		{
			Class<?> proxyClass = entry.getKey();
			Set<Class<?>> targetClasses = entry.getValue();
			for (Class<?> targetClass : targetClasses)
			{
				Proxy proxy = (Proxy) ReflectionUtil.newInstance(proxyClass);
				if (targetMap.containsKey(targetClass))
				{
					targetMap.get(targetClass).add(proxy);
				}
				else
				{
					List<Proxy> proxies = new ArrayList<>();
					proxies.add(proxy);
					targetMap.put(targetClass, proxies);
				}
			}
		}
		return targetMap;
	}
}
