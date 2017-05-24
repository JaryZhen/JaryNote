package jackson.fasterxml.inter;

import java.util.Date;

import com.fasterxml.jackson.annotation.*;

/**
 * Created by Jary on 2017/5/10 0010.
 */
// property(可选):制定识别码的属性名称
//@JsonTypeName(value = "kafka")
public class KafkaCom implements Com {
    private String name;
    @JsonProperty("HQ")   //java属性headquarters序列化到json字段的名称为HQ
    private String headquarters;
    private Department[] departments;
    @JsonIgnore         //在序列化与反序列化时，忽略birthDate属性
    private Date birthDate;

    public Date getBirthDate() {
        return birthDate;
    }

    @JsonCreator
    public KafkaCom(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public Department[] getDepartments() {
        return departments;
    }

    public void setDepartments(Department[] departments) {
        this.departments = departments;
    }

}