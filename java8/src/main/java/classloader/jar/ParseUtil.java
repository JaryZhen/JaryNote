package classloader.jar;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jary on 2018/2/7 0007.
 */
public class ParseUtil {

    private static String DEFAULT_SERVICE_DIR = "test/classes";//resource
    private static ConcurrentHashMap<String, Class<?>> classCacheMap =  new ConcurrentHashMap<String, Class<?>>();

    public static void main(String[] args) {
        ClassLoader parent = ParseUtil.class.getClassLoader();
        // 任意绝对路径
//         ServiceContainer.start("C:/services"); E:\classes
        // 非classpath
//         ServiceContainer.start("services/");
        String pa = "data/";
        // classpath ---META-INF/services/
        ClassLoadResourceJar serviceClassLoader = new ClassLoadResourceJar(parseJarDir(pa), parent);
    }
    private static URL[] parseJarDir(String serviceDir) {
        try {
            URL url = ParseUtil.class.getClassLoader().getResource(serviceDir);
            if (url != null) {
                serviceDir = url.getPath();
                System.out.println("dir:"+url.getPath());// /D:/IdeaProjects/JaryNote/java8/target/classes/test/classes/
            }
            List<java.net.URL> urlList = new ArrayList<java.net.URL>();
            File dir = new File(serviceDir);
            if (!dir.isDirectory()) {
                throw new IllegalStateException(dir.getPath() + " is not dir");
            }
            for (File file : dir.listFiles()) {
                if (file.getPath().endsWith(".jar")) {
                    urlList.add(new URL("file:" + file.getPath()));
                    System.out.println("jar:"+file.getPath());// D:\IdeaProjects\JaryNote\java8\target\classes\test\classes\hello-service-0.0.1-SNAPSHOT.jar
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

}
