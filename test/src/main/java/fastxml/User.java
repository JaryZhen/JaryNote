package fastxml;

import lombok.Data;

import java.util.Date;

/**
 * Created by Jary on 2018/1/18 0018.
 */
@Data
public class User {
    private String name;
    private String email;
    private int age;
    private Date birthday;
}
