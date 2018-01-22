package fastxml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Jary on 2018/1/18 0018.
 * JSON转Java类[JSON反序列化]
 */
public class Json2POJO {
    public static void main(String[] args) throws ParseException, IOException {
        String json = "{" +
                "\"name\":\"小民\"," +
                "\"age\":20," +
                "\"birthday\":844099200000," +
                "\"email\":\"xiaomin@sina.com\"" +
                "}";

        /**
         * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
         */
        ObjectMapper mapper = new ObjectMapper();

        User user = mapper.readValue(json, User.class);

        //json string to jsonNode
        ObjectNode opb  = (ObjectNode) mapper.readTree(json);

        System.out.println(opb);
    }
}
