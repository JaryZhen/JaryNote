package js;

import javax.script.*;

/**
 * Created by Jary on 2017/6/21 0021.
 */
public class TestJS {
    public static void main(String[] args) {
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
            Compilable compilable = (Compilable) engine;
            Bindings bindings = engine.createBindings(); //Local级别的Binding


            String script =
                    "function add(op1,op2,c)" +
                            "{return op1+op2+c} " +
                            "add(a, b,c)"; //定义函数并调用
            CompiledScript JSFunction = compilable.compile(script); //解析编译脚本函数
            bindings.put("a", 1);
            bindings.put("b", 2); //通过Bindings加入参数
            bindings.put("a", 11);
            bindings.put("b", 22); //通过Bindings加入参数
            bindings.put("a", 11);
            bindings.put("c", 1);

            Object result = JSFunction.eval(bindings);
            System.out.println(result); //调用缓存着的脚本函数对象，Bindings作为参数容器传入

            String script2 = "typeof temperature !== 'undefined' && temperature >= 100";
            CompiledScript JSFunction2 = compilable.compile(script2); //解析编译脚本函数
            bindings.put("temperature", 1);
            bindings.put("temperature2", 111);

            Object result2 = JSFunction2.eval(bindings);
            System.out.println(result2);

        } catch (ScriptException e) {
        }
    }
}
