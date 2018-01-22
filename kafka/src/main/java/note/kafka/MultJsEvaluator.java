package note.kafka;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import lombok.extern.slf4j.Slf4j;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

/**
 * Created by Jary on 2017/12/22 0022.
 */
@Slf4j
public class MultJsEvaluator {

    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    static ScriptContext defaultCtx;

    public MultJsEvaluator() {
        try {
            engine.eval(new FileReader(Res.getJS("BasisFuc.js")));
            defaultCtx = engine.getContext();

        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Bindings toBindings(Map<String, Integer> map) {

        Bindings bi = defaultCtx.getBindings(ScriptContext.ENGINE_SCOPE);
        bi.putAll(map);
        return  bi;
    }

    public void execute(Bindings bindings) throws ScriptException {
        Object result = engine.eval("Sum(a,b)", bindings);
        System.out.println("result: "+result);

    }

    public void destroy() {
        engine = null;
    }
}
