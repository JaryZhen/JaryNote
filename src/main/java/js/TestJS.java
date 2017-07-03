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
            Bindings bindings = engine.createBindings(); //Local�����Binding


            String script =
                    "function add(op1,op2,c)" +
                            "{return op1+op2+c} " +
                            "add(a, b,c)"; //���庯��������
            CompiledScript JSFunction = compilable.compile(script); //��������ű�����
            bindings.put("a", 1);
            bindings.put("b", 2); //ͨ��Bindings�������
            bindings.put("a", 11);
            bindings.put("b", 22); //ͨ��Bindings�������
            bindings.put("a", 11);
            bindings.put("c", 1);

            Object result = JSFunction.eval(bindings);
            System.out.println(result); //���û����ŵĽű���������Bindings��Ϊ������������

            String script2 = "typeof temperature !== 'undefined' && temperature >= 100";
            CompiledScript JSFunction2 = compilable.compile(script2); //��������ű�����
            bindings.put("temperature", 1);
            bindings.put("temperature2", 111);

            Object result2 = JSFunction2.eval(bindings);
            System.out.println(result2);

        } catch (ScriptException e) {
        }
    }
}
