/**
 * Copyright © 2016-2017 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package note.kafka;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import lombok.extern.slf4j.Slf4j;

import javax.script.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrew Shvayka
 */
@Slf4j
public class NashornJsEvaluator {

    public static final String CLIENT_SIDE = "cs";
    public static final String SERVER_SIDE = "ss";
    public static final String SHARED = "shared";
    private static NashornScriptEngineFactory factory = new NashornScriptEngineFactory();

    private CompiledScript engine;

    //typeof temperature !== 'undefined' && temperature >= 100
    public NashornJsEvaluator(String script) {
        engine = compileScript(script);
    }

    private static CompiledScript compileScript(String script) {
        ScriptEngine engine = factory.getScriptEngine(new String[]{"--no-java"});
        Compilable compEngine = (Compilable) engine;

        try {
            return compEngine.compile(script);
        } catch (ScriptException e) {
            log.warn("Failed to compile filter script: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Can't compile script: " + e.getMessage());
        }
    }

    public static Bindings toBindings(String ... entries) {
        return toBindings(new SimpleBindings(), entries);
    }

    public static Bindings toBindings(Map<String, Integer> map) {
        return toBindings(new SimpleBindings(), map);
    }

    public static Bindings toBindings(Bindings bindings,Map<String,Integer> map) {

        bindings.putAll(map);
        return bindings;
    }

    public static Bindings toBindings(Bindings bindings,String ...  entries) {

        int len = entries.length;

        Map<String ,Integer> a = new HashMap<>();

        //bindings.put(entry,entry);
        return bindings;
    }


    public void execute(Bindings bindings) throws ScriptException {
        Object eval = engine.eval(bindings);
        System.out.println("result: "+eval);
        /*if (eval instanceof Boolean) {
            return (Boolean) eval;
        } else {
            log.warn("Wrong result type: {}", eval);
            throw new ScriptException("Wrong result type: " + eval);
        }*/
    }

    public void destroy() {
        engine = null;
    }
}
