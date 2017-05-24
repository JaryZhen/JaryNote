package jackson.fasterxml;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Jary on 2017/5/10 0010.
 */
public class Test {

    /*
    1��@JsonAutoDetect:�������Զ���⣬�����ظ�
    2��@JsonIgnore: a.�������ֶλ򷽷��ϣ�������ȫ���Ա�ע����ֶκͷ�����Ӧ�����ԣ���������ֶλ򷽷����Ա��Զ���⵽���߻���������ע��
                    b.@JsonIgnore����ע����getters�ϻ���setters�϶�����Զ�Ӧ������
����
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
    3��@JsonProperty: a.�������ֶλ򷽷��ϣ����������Ե����л�/�����л�����������������©���ԣ�
                      b.ͬʱ�ṩ�����������������������ںܶೡ����Java����������ǰ��չ淶���շ���д������ʵ��չʾ��ȴ������C-style��C++/Microsolft style
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
        @JsonProperty//ע�����������и�ע�⣬��Ϊû���ṩ��Ӧ��getId��setId����������������getter��setter����ֹ��©������
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
    4��@JsonIgnoreProperties:���������ϣ�����˵����Щ���������л�/�����л�ʱ��Ҫ���Ե������Խ���������@JsonIgnore�����������������Ĺ��ܱ�@JsonIgnoreҪǿ
     */
    public static void JsonIgnoreProperties() throws Exception {
        TestPOJO2 testPOJO = new TestPOJO2();
        testPOJO.setId(111);
        testPOJO.setName("myName");
        testPOJO.setAge(22);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(testPOJO);
        System.out.println("{\"id\":111}" + jsonStr);//name��age�����Ե���

        String jsonStr2 = "{\"id\":111,\"name\":\"myName\",\"age\":22,\"title\":\"myTitle\"}";
        TestPOJO2 testPOJO2 = objectMapper.readValue(jsonStr2, TestPOJO2.class);
        System.out.println(111 + " " + testPOJO2.getId());
        System.out.println(testPOJO2.getName());
        System.out.println(0 + testPOJO2.getAge());
        String jsonStr3 = "{\"id\":111,\"name\":\"myName\",\"count\":33}";//�����и�δ֪��count���ԣ������л��ᱨ��
        objectMapper.readValue(jsonStr3, TestPOJO2.class);
    }

    @JsonIgnoreProperties({"name", "age", "title"})
    public static class TestPOJO2 {
        private int id;
        private String name;
        private int age;

        //getters��settersʡ��
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
    5��@JsonUnwrapped:�����������ֶλ򷽷��ϣ���������JSON�����������ӵ���յ�JSON����˵�����Ƚ��Ѷ����������Ӿͺ�����ˣ��������
����
     */
    public static void jsonUnwrapped() throws Exception {
        TestPOJO3 testPOJO = new TestPOJO3();
        testPOJO.setId(111);
        TestName testName = new TestName();
        testName.setFirstName("��");
        testName.setSecondName("��");
        testPOJO.setName(testName);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(testPOJO);
        //���û��@JsonUnwrapped�����л���Ϊ{"id":111,"name":{"firstName":"��","secondName":"��"}}
        //��Ϊ��name�����ϼ���@JsonUnwrapped������name��������firstName��secondName�����������name�С�
        System.out.println("{\"id\":111,\"firstName\":\"��\" \"secondName\":\"��\"}" + jsonStr);
        String jsonStr2 = "{\"id\":111,\"firstName\":\"��\",\"secondName\":\"��\"}";
        TestPOJO3 testPOJO2 = objectMapper.readValue(jsonStr2, TestPOJO3.class);
        System.out.println(111 + "" + testPOJO2.getId());
        System.out.println("��" + testPOJO2.getName().getFirstName());
        System.out.println("��" + testPOJO2.getName().getSecondName());
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
        //getters��settersʡ��
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
    6��@JsonIdentityInfo:2.0+�汾��ע�⣬��������������ϣ������������л�/�����л�ʱΪ�ö�����ֶ����һ������ʶ���룬ͨ�����������ѭ��Ƕ�׵����⣬
    �������ݿ��еĶ�Զ��ϵ��ͨ����������generator��ȷ��ʶ�������ɵķ�ʽ���м򵥵ģ���������property��ȷ��ʶ��������ƣ�ʶ��������û�����ơ�
    ����ʶ�������������ģ���������JSON�У�������POJO��һ���֣�������������ǿ������ʹ��ע��
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
    //��������JSON�У�������POJO��һ���֣�������������ǿ������ʹ��ע��
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
    7��@JsonNaming
jackson 2.1+�汾��ע�⣬��������򷽷���ע�����ע������jackson-databind���ж�������jackson-annotations������������㶨�������������ԣ����ú�ǰ���ᵽ��@JsonProperty������������������ͬ������
����һ��JSON��{"in_reply_to_user_id":"abc123"}����Ҫ�����л�ΪPOJO��POJOһ�����������Ҫ���д
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
��������/�ӿڣ�������������̬���ʹ����Ի���/�ӿں�����/ʵ���඼��Ч
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




