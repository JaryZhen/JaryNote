package jackson.fasterxml.inter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Jary on 2017/5/10 0010.
 */
// property(可选):制定识别码的属性名称
//@JsonTypeName(value = "kafka")
public class HadoopCom implements Com {
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
    public HadoopCom(@JsonProperty("name") String name) {
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