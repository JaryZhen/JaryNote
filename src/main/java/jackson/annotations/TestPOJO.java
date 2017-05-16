package jackson.annotations;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Jary on 2017/5/10 0010.
 */
public  class TestPOJO{
    private int id;
    @JsonIgnore
    private String name;
    private int count;

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

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
