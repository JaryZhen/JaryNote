package jsonschema;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;

public class JsonValidation {

    public static void main(String[] args) {
        JsonValidation jv = new JsonValidation();
        try (InputStream inputStream = jv.getClass().getResourceAsStream("data/path/subject.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject("{\"hello\" : \"world\"}")); // throws a ValidationException if this object is invalid

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
