package note.kafka;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Jary on 2017/12/28 0028.
 */
public class Res {

    public static File getJS(String path){
        ClassLoader classLoader = Res.class.getClassLoader();
        URL resource = classLoader.getResource(path);

        File file = null;
        try {
            file = new File(resource.toURI());
            System.out.println(file.getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return file;
    }
}
