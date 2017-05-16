package jackson.json;

import java.util.Date;

import com.fasterxml.jackson.annotation.*;

/**
 * Created by Jary on 2017/5/10 0010.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type") //作用于类/接口，被用来开启多态类型处理，对基类/接口和子类/实现类都有效  property(可选):制定识别码的属性名称
public class Company {

    private String name;

    @JsonCreator
    public Company(String name) {
        this.name = name;
    }
    private String headquarters;

    private Department[] departments;

    @JsonIgnore         //在序列化与反序列化时，忽略birthDate属性
    private Date birthDate;

    public Date getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("HQ")   //java属性headquarters序列化到json字段的名称为HQ
    public String getHeadquarters() {
        return headquarters;
    }

    public Department[] getDepartments() {
        return departments;
    }

    @JsonProperty("departments")
    public void setDepartments(Department[] departments) {
        this.departments = departments;
    }

}