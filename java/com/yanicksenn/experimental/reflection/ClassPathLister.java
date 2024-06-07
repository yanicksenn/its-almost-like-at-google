package com.yanicksenn.experimental.reflection;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import java.io.IOException;
import java.lang.ClassLoader;
import java.net.URL;
import java.util.Set;

public final class ClassPathLister {
  public static void main(String[] args) throws IOException {
    ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
    classPath.getAllClasses().stream()
        .map(ClassInfo::getName)
        .sorted()
        .filter(e -> e.contains("yanicksenn"))
        .forEach(System.out::println);
  }
}
