package note.kafka.v10.low;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;


/**
 * @author Administrator
 *
 */
public class SimplePartitioner implements Partitioner {

	@Override
	public void configure(Map<String, ?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int partition(String topic,
						 Object key,
						 byte[] keyBytes,
						 Object value,
						 byte[] valueBytes,
						 Cluster cluster){
		// TODO Auto-generated method stub
// TODO Auto-generated method stub
        /*
         * Cluster arg5��ӡ�������������
         * Cluster(nodes = [172.17.11.11:9092 (id: 0 rack: null), 172.17.11.13:9092 (id: 1 rack: null), 172.17.11.15:9092 (id: 2 rack: null)], partitions = [Partition(topic = TOPIC-20160504-1200, partition = 1, leader = 2, replicas = [0,1,2,], isr = [2,1,0,], Partition(topic = TOPIC-20160504-1200, partition = 2, leader = 0, replicas = [0,1,2,], isr = [0,2,1,], Partition(topic = TOPIC-20160504-1200, partition = 0, leader = 1, replicas = [0,1,2,], isr = [1,0,2,]])
        */
        /*
         * byte[] arg2���ֽ���ĸ�ʽ�洢key
         * System.out.println(new String(arg2));
         * System.out.println(key.toString());���������ͬ������key
         * byte[] arg4��Object valueͬ��
         */
        /*
         * ����ֵָ���ķ���ֵ
         */
		//�Ӵ����key�зָ�����ڷ�������ֵ
		int partition= Integer.parseInt(key.toString());//�ָ� " . " ��Ҫת��" \\. "
		if(partition%2==0){
			return 0;
		}else{
			return 1;
		}
	}



}
