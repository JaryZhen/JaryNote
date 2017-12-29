package com.winterbe.java8.samples.nashorn;

import com.winterbe.java8.samples.lambda.Person;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Benjamin Winterberg
 */
public class Nashorn8 {
    public static void main(String[] args) throws ScriptException, NoSuchMethodException, FileNotFoundException {
        NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader(Res.getJS("res/nashorn8.js")));

        engine.invokeFunction("evaluate1");                             // [object global]
        engine.invokeFunction("evaluate2");                             // [object Object]
        engine.invokeFunction("evaluate3", "Foobar");                   // Foobar
        engine.invokeFunction("evaluate3", new Person("John", "Doe"));  // [object global] <- ???????
    }

}
