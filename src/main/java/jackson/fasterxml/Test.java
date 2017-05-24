package jackson.fasterxml;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Jary on 2017/5/10 0010.
 */
public class Test {

    /*
    1、@JsonAutoDetect:看上面自动检测，不再重复
    2、@JsonIgnore: a.作用在字段或方法上，用来完全忽略被注解的字段和方法对应的属性，即便这个字段或方法可以被自动检测到或者还有其他的注解
                    b.@JsonIgnore不管注解在getters上还是setters上都会忽略对应的属性
举例
     */
    public static void jsonIgnoreTest() throws Exception {
        TestPOJO testPOJO = new TestPOJO();
        testPOJO.setId(111);
        testPOJO.setName("myName");
        testPOJO.setCount(22);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(testPOJO);
        System.out.println("{\"id\":111}" + "    " + jsonStr);

        String jsonStr2 = "{\"id\":111,\"name\":\"myName\",\"count\":22}";
        TestPOJO testPOJO2 = objectMapper.readValue(jsonStr2, TestPOJO.class);
        System.out.println(111 + "   " + testPOJO2.getId());
        System.out.println(testPOJO2.getName());
        System.out.println(0 + "  " + testPOJO2.getCount());
    }

    /*
    3、@JsonProperty: a.作用在字段或方法上，用来对属性的序列化/反序列化，可以用来避免遗漏属性，
                      b.同时提供对属性名称重命名，比如在很多场景下Java对象的属性是按照规范的驼峰书写，但是实际展示的却是类似C-style或C++/Microsolft style
     */
    public static void jsonPropertyTest() throws Exception {
        TestPOJO1 testPOJO = new TestPOJO1();
        testPOJO.wahaha(111);
        testPOJO.setFirstName("myName");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(testPOJO);
        System.out.println("{\"id\":111,\"first_name\":\"myName\"}" + jsonStr);

        String jsonStr2 = "{\"id\":111,\"first_name\":\"myName\"}";
        TestPOJO1 testPOJO2 = objectMapper.readValue(jsonStr2, TestPOJO1.class);
        System.out.println(111 + testPOJO2.wahaha());
        System.out.println("myName " + testPOJO2.getFirstName());
    }

    public static class TestPOJO1 {
        @JsonProperty//注意这里必须得有该注解，因为没有提供对应的getId和setId函数，而是其他的getter和setter，防止遗漏该属性
        private int id;
        @JsonProperty("first_name")
        private String firstName;

        public int wahaha() {
            return id;
        }

        public void wahaha(int id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    }

    /*
    4、@JsonIgnoreProperties:作用在类上，用来说明有些属性在序列化/反序列化时需要忽略掉，可以将它看做是@JsonIgnore的批量操作，但它的功能比@JsonIgnore要强
     */
    public static void JsonIgnoreProperties() throws Exception {
        TestPOJO2 testPOJO = new TestPOJO2();
        testPOJO.setId(111);
        testPOJO.setName("myName");
        testPOJO.setAge(22);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(testPOJO);
        System.out.println("{\"id\":111}" + jsonStr);//name和age被忽略掉了

        String jsonStr2 = "{\"id\":111,\"name\":\"myName\",\"age\":22,\"title\":\"myTitle\"}";
        TestPOJO2 testPOJO2 = objectMapper.readValue(jsonStr2, TestPOJO2.class);
        System.out.println(111 + " " + testPOJO2.getId());
        System.out.println(testPOJO2.getName());
        System.out.println(0 + testPOJO2.getAge());
        String jsonStr3 = "{\"id\":111,\"name\":\"myName\",\"count\":33}";//这里有个未知的count属性，反序列化会报错
        objectMapper.readValue(jsonStr3, TestPOJO2.class);
    }

    @JsonIgnoreProperties({"name", "age", "title"})
    public static class TestPOJO2 {
        private int id;
        private String name;
        private int age;

        //getters、setters省略
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int count) {
            this.age = count;
        }
    }

    /*
    5、@JsonUnwrapped:作用在属性字段或方法上，用来将子JSON对象的属性添加到封闭的JSON对象，说起来比较难懂，看个例子就很清楚了，不多解释
举例
     */
    public static void jsonUnwrapped() throws Exception {
        TestPOJO3 testPOJO = new TestPOJO3();
        testPOJO.setId(111);
        TestName testName = new TestName();
        testName.setFirstName("张");
        testName.setSecondName("三");
        testPOJO.setName(testName);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(testPOJO);
        //如果没有@JsonUnwrapped，序列化后将为{"id":111,"name":{"firstName":"张","secondName":"三"}}
        //因为在name属性上加了@JsonUnwrapped，所以name的子属性firstName和secondName将不会包含在name中。
        System.out.println("{\"id\":111,\"firstName\":\"张\" \"secondName\":\"三\"}" + jsonStr);
        String jsonStr2 = "{\"id\":111,\"firstName\":\"张\",\"secondName\":\"三\"}";
        TestPOJO3 testPOJO2 = objectMapper.readValue(jsonStr2, TestPOJO3.class);
        System.out.println(111 + "" + testPOJO2.getId());
        System.out.println("张" + testPOJO2.getName().getFirstName());
        System.out.println("三" + testPOJO2.getName().getSecondName());
    }

    public static class TestPOJO3 {
        private int id;
        @JsonUnwrapped
        private TestName name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public TestName getName() {
            return name;
        }

        public void setName(TestName name) {
            this.name = name;
        }
        //getters、setters省略
    }

    public static class TestName {
        private String firstName;
        private String secondName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getSecondName() {
            return secondName;
        }

        public void setSecondName(String secondName) {
            this.secondName = secondName;
        }
    }

    /*
    6、@JsonIdentityInfo:2.0+版本新注解，作用于类或属性上，被用来在序列化/反序列化时为该对象或字段添加一个对象识别码，通常是用来解决循环嵌套的问题，
    比如数据库中的多对多关系，通过配置属性generator来确定识别码生成的方式，有简单的，配置属性property来确定识别码的名称，识别码名称没有限制。
    对象识别码可以是虚拟的，即存在在JSON中，但不是POJO的一部分，这种情况下我们可以如此使用注解
     */
    public static void jsonIdentityInfo() throws Exception {
        Parent parent = new Parent();
        parent.setName("jack");
        Child child = new Child();
        child.setName("mike");
        Child[] children = new Child[]{child};
        parent.setChildren(children);
        child.setParent(parent);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(parent);
        System.out.println("{\"@id\":1,\"name\":\"jack\",\"children\":[{\"name\":\"mike\",\"parent\":1}]}" + jsonStr);

        Parent testPOJO2 = objectMapper.readValue(jsonStr, Parent.class);
        System.out.println(testPOJO2.getName());

    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "dfgid")
    //即存在在JSON中，但不是POJO的一部分，这种情况下我们可以如此使用注解
    public static class Parent {
        private String name;
        private Child[] children;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Child[] getChildren() {
            return children;
        }

        public void setChildren(Child[] children) {
            this.children = children;
        }
    }

    public static class Child {
        private String name;
        private Parent parent;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Parent getParent() {
            return parent;
        }

        public void setParent(Parent parent) {
            this.parent = parent;
        }
    }

    /*
    7、@JsonNaming
jackson 2.1+版本的注解，作用于类或方法，注意这个注解是在jackson-databind包中而不是在jackson-annotations包里，它可以让你定制属性命名策略，作用和前面提到的@JsonProperty的重命名属性名称相同。比如
你有一个JSON串{"in_reply_to_user_id":"abc123"}，需要反序列化为POJO，POJO一般情况下则需要如此写
     */

    public static void jsonTypeInfo() throws Exception {
        Sub1 sub1 = new Sub1();
        sub1.setId(1);
        sub1.setName("sub1Name");
        Sub2 sub2 = new Sub2();
        sub2.setId(2);
        sub2.setAge(33);
        ObjectMapper objectMapper = new ObjectMapper();
        TestPOJO4 testPOJO = new TestPOJO4();
        testPOJO.setMyIns(new MyIn[]{sub1, sub2});
        String jsonStr = objectMapper.writeValueAsString(testPOJO);
        //System.out.println("{\"myIns\":[{\"id\":1,\"name\":\"sub1Name\"},{\"id\":2,\"age\":33}]}"+ ",,,,,  "+jsonStr);
        System.out.println(jsonStr);
    }

    public static class TestPOJO4 {
        private int id;
        private MyIn[] myIns;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public MyIn[] getMyIns() {
            return myIns;
        }

        public void setMyIns(MyIn[] myIn) {
            this.myIns = myIn;
        }
    }

    /*
作用于类/接口，被用来开启多态类型处理，对基类/接口和子类/实现类都有效
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "jtypeName")
    @JsonSubTypes({@JsonSubTypes.Type(value = Sub1.class, name = "jsub1"), @JsonSubTypes.Type(value = Sub2.class, name = "jsub2")})
    public static abstract class MyIn {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Sub1 extends MyIn {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Sub2 extends MyIn {
        private int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static void main(String[] agr) {
        try {

            //jsonIgnoreTest();
            //jsonPropertyTest();
            //JsonIgnoreProperties();
            //jsonUnwrapped();
            //jsonIdentityInfo();
            jsonTypeInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




