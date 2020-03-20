package com.dtstack.ws.common;
import org.apache.commons.io.FilenameUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

/**
 * @author xiaohe
 * @date 2019/11/9
 */
public class ResourceUtils {
    public static URL getResourceWithAbsolutePackagePath(String absolutePackagePath, String resourceName) {
        return getResourceWithAbsolutePackagePath(ResourceUtils.class, absolutePackagePath, resourceName);
    }

    private static class Path {
        String packagePath = "";
        String resourcePath = "";
    }

    private static String getFullPath(String resourcePath) {
        int linuxIndex = resourcePath.lastIndexOf("/");
        int windowsIndex = resourcePath.lastIndexOf("\\");
        int index = Math.max(linuxIndex, windowsIndex);
        if (index < 0) {
            return "";
        }
        return resourcePath.substring(0, index);
    }

    private static Path parsePath(String resourcePath) {
        Objects.requireNonNull(resourcePath, "resourcePath cannot be null");
        Path path = new Path();
        path.packagePath = getFullPath(resourcePath);
        path.resourcePath = FilenameUtils.getName(resourcePath);
        return path;
    }

    public static URL getResource(String resourcePath) {
        Path path = parsePath(resourcePath);
        return getResourceWithAbsolutePackagePath(path.packagePath, path.resourcePath);
    }

    public static URL getResource(Class<?> clazz, String resourcePath) {
        Path path = parsePath(resourcePath);
        return getResourceWithAbsolutePackagePath(clazz, path.packagePath, path.resourcePath);
    }

    public static InputStream getResourceAsStream(String resourcePath) {
        Path path = parsePath(resourcePath);
        return getResourceWithAbsolutePackagePathAsStream(path.packagePath, path.resourcePath);
    }

    public static InputStream getResourceAsStream(Class<?> clazz, String resourcePath) {
        Path path = parsePath(resourcePath);
        return getResourceWithAbsolutePackagePathAsStream(clazz, path.packagePath, path.resourcePath);
    }

    public static URL getResourceWithAbsolutePackagePath(Class<?> clazz, String absolutePackagePath, String resourceName) {
        Objects.requireNonNull(clazz, "clazz cannot be null");
        String resourcePath = getResourcePath(absolutePackagePath, resourceName);
        URL resource = null;
        // first attempt - outside/inside jar file
        resource = clazz.getClass().getResource(resourcePath);
        // second attempt - servlet container - inside application lib folder
        if (resource == null) {
            if (resourcePath.charAt(0) == '/') {
                String resourcePathWithoutLeadingSlash = resourcePath.substring(1);
                resource = Thread.currentThread().getContextClassLoader().getResource(resourcePathWithoutLeadingSlash);
            }
        }
        Checks.checkArgument(resource != null, String.format("Resource [%s] loading failed", resourcePath));
        return resource;
    }

    public static InputStream getResourceWithAbsolutePackagePathAsStream(String absolutePackagePath, String resourceName) {
        return getResourceWithAbsolutePackagePathAsStream(ResourceUtils.class, absolutePackagePath, resourceName);
    }

    public static InputStream getResourceWithAbsolutePackagePathAsStream(Class<?> clazz, String absolutePackagePath, String resourceName) {
        Objects.requireNonNull(clazz, "clazz cannot be null");
        String resourcePath = getResourcePath(absolutePackagePath, resourceName);
        InputStream resource = null;
        // first attempt - outside/inside jar file
        resource = clazz.getClass().getResourceAsStream(resourcePath);
        // second attempt - servlet container - inside application lib folder
        if (resource == null) {
            ClassLoader classLoader = clazz.getClass().getClassLoader();
            if (classLoader != null){
                resource = classLoader.getResourceAsStream(resourcePath);
            }
        }
        Checks.checkArgument(resource != null, String.format("Resource [%s] loading failed", resourcePath));
        return resource;
    }

    private static String getResourcePath(String absolutePackagePath, String resourceName) {
        Objects.requireNonNull(absolutePackagePath, "absolutePackagePath cannot be null");
        Objects.requireNonNull(resourceName, "resourceName cannot be null");
        absolutePackagePath = formatArgument(absolutePackagePath);
        resourceName = formatArgument(resourceName);
        return constructResourcePath(absolutePackagePath, resourceName);
    }

    private static String formatArgument(String argument) {
        String argumentWithoutWhiteSpaces = argument.trim();
        return argumentWithoutWhiteSpaces;
    }

    private static String constructResourcePath(String packagePath, String resourceName) {
        String resourcePath = String.format("/%s/%s", packagePath, resourceName);
        String resourcePathUnixSeparators = FilenameUtils.separatorsToUnix(resourcePath);
        String resourcePathNoLeadingSeparators = removeLeadingUnixSeparators(resourcePathUnixSeparators);
        String normalizedResourcePath = FilenameUtils.normalizeNoEndSeparator(resourcePathNoLeadingSeparators, true);
        return normalizedResourcePath;
    }

    private static String removeLeadingUnixSeparators(String argument) {
        return argument.replaceAll("/+", "/");
    }

}
