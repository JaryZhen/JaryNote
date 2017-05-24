package jackson.fasterxml;

import java.io.File;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jackson.fasterxml.inter.Com;
import jackson.fasterxml.inter.Department;
import jackson.fasterxml.inter.KafkaCom;

/**
 * Created by Jary on 2017/5/10 0010.
 */
public class DeserializationExample {
    public static void main(String[] args) throws Exception {
        //Company();
        Mult();
    }


    public static void Mult() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); //禁止未知属性打断反序列化
        // mapper.registerSubtypes(new NamedType(company.class,"sub1"));

        Com kafka = mapper.readValue(new File("data/jackson/kafka.json"), Com.class);
        String jsonStr = mapper.writeValueAsString(kafka);
        System.out.println(jsonStr);

        Com hadoop = mapper.readValue(new File("data/jackson/hadoop.json"), Com.class);
        String jsonStr2 = mapper.writeValueAsString(hadoop);
        System.out.println(jsonStr2);
    }

    public static void Company() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); //禁止未知属性打断反序列化
        // mapper.registerSubtypes(new NamedType(company.class,"sub1"));

        KafkaCom company = mapper.readValue(new File("data/jackson/kafka.json"), KafkaCom.class);
        String jsonStr = mapper.writeValueAsString(company);
        System.out.println(jsonStr);

        System.out.print("company_name: " + company.getName() + "\t");
        System.out.print("headquarters: " + company.getHeadquarters() + "\t");
        System.out.println("birthDate: " + company.getBirthDate()); //birthDate被标记为@JsonIgnore，所以此处得到的值应该为null

        Department[] departments = company.getDepartments();

        for (Department department : departments) {
            System.out.print("department_name: " + department.getName() + "\t");
            System.out.print("employee_number: " + department.getPm() + "\t");
            //Department中未定义的字段product,employee_number
            System.out.print("product: " + department.get("product") + "\t");
            System.out.println("projectManager: " + department.get("employee_number"));
        }
    }
}