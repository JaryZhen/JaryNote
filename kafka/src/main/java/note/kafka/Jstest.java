package note.kafka;

import note.kafka.Res;

import javax.script.*;
import java.io.FileReader;

/**
 * Bind java objects to custom javascript objects.
 *
 * @author Benjamin Winterberg
 */
public class Jstest {

    public static void main(String[] args) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader(Res.getJS("BasisFuc.js")));
        ScriptContext defaultCtx = engine.getContext();

        //1 without bindings But giving the exct args
        Object result = engine.eval("Sum(1,2) > 22", defaultCtx);
        System.out.println(result);

        Bindings bi = defaultCtx.getBindings(ScriptContext.ENGINE_SCOPE);
        bi.put("q", 1);
        bi.put("b", 22);
        Object result2 = engine.eval("(Sum(q)+Sum(b))>23", bi);
        System.out.println(result2);


        Invocable invocable = (Invocable) engine;
        Object result3 = invocable.invokeFunction("Sum", "awe", 22);
        System.out.println(result3);


        // 1m  Sum(p1) > 5   => Sum(1,2,3,4,5,6,7,8) > 5

    }

}