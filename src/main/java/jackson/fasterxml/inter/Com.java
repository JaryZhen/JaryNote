package jackson.fasterxml.inter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by Jary on 2017/5/17 0017.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type") //作用于类/接口，被用来开启多态类型处理，对基类/接口和子类/实现类都有效
@JsonSubTypes({@JsonSubTypes.Type(value = KafkaCom.class, name = "kafka"),@JsonSubTypes.Type(value = HadoopCom.class, name = "hadoop")})
public interface Com {
}
