package jackson.fasterxml.inter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by Jary on 2017/5/17 0017.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type") //��������/�ӿڣ�������������̬���ʹ����Ի���/�ӿں�����/ʵ���඼��Ч
@JsonSubTypes({@JsonSubTypes.Type(value = KafkaCom.class, name = "kafka"),@JsonSubTypes.Type(value = HadoopCom.class, name = "hadoop")})
public interface Com {
}
