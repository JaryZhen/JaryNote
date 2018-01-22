package note.kafka;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import note.kafka.Res;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Bind java objects to custom javascript objects.
 *
 * @author Benjamin Winterberg
 */
public class Jstest {

    public static void main(String[] args) throws Exception {

        //window();
        single();
    }

    private static void window() {
        //适合预编译好的js
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {

            engine.eval(new FileReader(Res.getJS("BasisFuc.js")));
            ScriptContext defaultCtx = engine.getContext();

            //1, without bindings But giving the exct args
            Object result = engine.eval("Sum(1,2,3,4)", defaultCtx);
            System.out.println(result);

            //2, hava bindings
            Bindings bi = defaultCtx.getBindings(ScriptContext.ENGINE_SCOPE);

            bi.put("a", 1);
            bi.put("b", 2);
            bi.put("a", 2);

            Object result2 = null;//Sum(p1,P2) > 23 Sum(p1+P2) > 5

            result2 = engine.eval("Sum(a,b)", bi);

            System.out.println(result2);





            //3, looks like the way 1
            Invocable invocable = (Invocable) engine;
            Object result3 = invocable.invokeFunction("Sum", 1, 2, 3);
            System.out.println(result3);

            // 1m  Sum(p1) > 5   => Sum(1,2,3,4,5,6,7,8) > 5
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void single() {
        //4.CompiledScript 适合单条数据

        CompiledScript engine;
        String script = "typeof temperature !== 'undefined' && temperature >= 0";
        String script2 ="temperature >= 0";
        try {
            NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
            ScriptEngine enginet = factory.getScriptEngine(new String[]{"--no-java"});
            Compilable compEngine = (Compilable) enginet;

            engine = compEngine.compile(script2);
            Map<String, Integer> m = new HashMap<>();
            m.put("temperature", 1);
            m.put("b", 2);
            m.put("a", 2);

            Bindings bi4 = toBindings(m);

            Object result4 = engine.eval(bi4);//Sum(p1,P2) > 23 Sum(p1+P2) > 5
            System.out.println(result4);
        } catch (ScriptException e) {
            e.printStackTrace();
        }


    }

    public static Bindings toBindings(Map<String, Integer> map) {
        return toBindings(new SimpleBindings(), map);
    }

    public static Bindings toBindings(Bindings bindings, Map<String, Integer> map) {

        bindings.putAll(map);
        return bindings;
    }
}