package classloader.jar;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Jary on 2018/2/7 0007.
 */
public class ClassLoadResourceJar extends URLClassLoader {

    private static ConcurrentHashMap<String, Class<?>> classCacheMap =
            new ConcurrentHashMap<String, Class<?>>();

    private URL[] urls;

    public ClassLoadResourceJar(URL[] urls, ClassLoader parent) {
        super(urls, ClassLoadResourceJar.class.getClassLoader());
        this.urls = urls;

        init();
    }


    private void init() {
        for (URL url : urls) {
            initClassName(url);
        }
    }
    private static URL[] parseServiceDir(String serviceDir) {
        try {
            URL url = ParseUtil.class.getClassLoader().getResource(serviceDir);
            if (url != null) {
                serviceDir = url.getPath();
                System.out.println("dir:"+serviceDir);
            }
            List<URL> urlList = new ArrayList<URL>();
            File dir = new File(serviceDir);
            if (!dir.isDirectory()) {
                throw new IllegalStateException(dir.getPath() + " is not dir");
            }
            for (File file : dir.listFiles()) {
                if (file.getPath().endsWith(".jar")) {
                    urlList.add(new URL("file:" + file.getPath()));
                    System.out.println("jar:"+file.getName());
                }
            }
            if (urlList.isEmpty()) {
                throw new IllegalStateException("urlList is empty");
            }
            return urlList.toArray(new java.net.URL[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void initClassName(java.net.URL url) {
        String path = url.getPath();
        try {
            JarFile jarFile = new JarFile(path);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String classFileName = jarEntry.getName();
                if (classFileName.endsWith(".class")) {
                    classFileName = classFileName.replace("/", ".");
                    String className = classFileName.substring(0, classFileName.lastIndexOf("."));
                    loadClass(className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> clazz = classCacheMap.get(name);
        if (clazz == null) {
            clazz = super.loadClass(name);
            classCacheMap.put(name, clazz);
        }
        return clazz;
    }
}
