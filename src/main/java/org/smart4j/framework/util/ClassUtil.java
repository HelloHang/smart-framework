package org.smart4j.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Daniels Gao
 * @date: 2018/10/31 19:27
 */
public class ClassUtil {

  private static final Logger LOG = LoggerFactory.getLogger(ClassUtil.class);

  public static ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

  public static Class<?> loadClass(final String className, final boolean isInitialized) {
    Class<?> cls;
    try {
      cls = Class.forName(className, isInitialized, getClassLoader());
    } catch (ClassNotFoundException e) {
      LOG.error(String.format("Load class[%s] failed.", className), e);
      throw new RuntimeException(e);
    }
    return cls;
  }

  public static Set<Class<?>> getAllClassFromPackage(final String packageName) {
    Set<Class<?>> classes = new HashSet<Class<?>>();
    try {
      Enumeration<URL> urls = getClassLoader()
          .getResources(StringUtil.replace(packageName, ".", "/"));
      while (urls.hasMoreElements()) {
        URL url = urls.nextElement();
        if (url != null) {
          String protocol = url.getProtocol();
          if ("file".equals(protocol)) {
            String packagePath = url.getPath().replaceAll("%20", " ");
            addClass(classes, packagePath, packageName);
          } else if ("jar".equals(protocol)) {
            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
            if (jarURLConnection != null) {
              JarFile jarFile = jarURLConnection.getJarFile();
              if (jarFile != null) {
                Enumeration<JarEntry> jarEntries = jarFile.entries();
                while (jarEntries.hasMoreElements()) {
                  JarEntry jarEntry = jarEntries.nextElement();
                  String jarEntryName = jarEntry.getName();
                  if (jarEntryName.endsWith(".class")) {
                    String className = jarEntryName.substring(0, jarEntryName.lastIndexOf("."));
                    classes.add(loadClass(className, false));
                  }
                }
              }
            }

          }

        }
      }
    } catch (Exception e) {
      LOG.error(String.format("Get all classed failed from package[%s]", packageName));
    }

    return classes;
  }

  public static void addClass(final Set<Class<?>> classes, String packagePath, String packageName) {
    File[] files = new File(packagePath).listFiles(
        file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
    for (File file : files) {
      String fileName = file.getName();
      if (file.isFile()) {
        String className = fileName.substring(0, fileName.lastIndexOf("."));
        if (StringUtil.isNotEmpty(packageName)) {
          className = packageName + "." + className;
        }
        classes.add(loadClass(className, false));
      } else {
        String subPackagePath = packagePath + "/" + fileName;
        String subPackageName = packageName + "." + fileName;
        addClass(classes, subPackagePath, subPackageName);
      }
    }
  }
}
