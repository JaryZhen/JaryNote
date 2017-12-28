package js;

import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.script.*;

/**
 * Created by Jary on 2017/12/27 0027.
 */
public class Others {

    public static void main(String[] args) throws Exception {
    }

    private static NashornScriptEngine createEngine() {
        return (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
    }

    public static void nashorn(String parser, String code) throws ScriptException, NoSuchMethodException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("nashorn");
        engine.eval(parser);
        Invocable inv = (Invocable) engine;
        Object esprima = engine.get("esprima");
        Object tree = new Object();
        Object tokens = new Object();
        for (int i = 0; i < 232; ++i) {
            long start = System.nanoTime();
            tree = inv.invokeMethod(esprima, "parse", code);
            tokens = inv.invokeMethod(esprima, "tokenize", code);
            long stop = System.nanoTime();
            System.out.println("Run #" + (i + 1) + ": " + Math.round((stop - start) / 1e6) + " ms");
        } // System.out.println("Data is " + tokens.toString() + " and " + tree.toString());
    }
}
