package org.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.util.ClassUtil;


/**
 * @author: Daniels Gao
 * @date: 2018/10/31 21:01
 */
public class ClassHelper
{

	private static final Set<Class<?>> CLASSES;

	static
	{
		String basePackage = ConfigHelper.getAppBasePackage();
		CLASSES = ClassUtil.getAllClassFromPackage(basePackage);
	}

	public static Set<Class<?>> getAllClasses()
	{
		return CLASSES;
	}

	public static Set<Class<?>> getServiceClasses()
	{
		return CLASSES.stream().filter(c -> c.isAnnotationPresent(Service.class)).collect(Collectors.toSet());
	}

	public static Set<Class<?>> getControllerClasses()
	{
		return CLASSES.stream().filter(c -> c.isAnnotationPresent(Controller.class)).collect(Collectors.toSet());
	}

	public static Set<Class<?>> getAllBeanClasses()
	{
		return CLASSES.stream().filter(c -> c.isAnnotationPresent(Service.class) || c.isAnnotationPresent(Controller.class))
			  .collect(Collectors.toSet());
	}

	public static Set<Class<?>> getSubClassBySuper(final Class<?> superClass)
	{
		return CLASSES.stream().filter(c -> c.isAssignableFrom(superClass) && !Objects.equals(c, superClass))
			  .collect(Collectors.toSet());
	}

	public static Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> cls)
	{
		return CLASSES.stream().filter(c -> c.isAnnotationPresent(cls)).collect(Collectors.toSet());
	}

}
