package uuid;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Jary on 2017/10/25 0025.
 */
public class UuidTest {
    public static void main(String[] args) throws Exception {
        //version=4
        System.out.println(UUID.randomUUID().version());
        System.out.println(UUID.randomUUID().toString());

        TimeBasedGenerator gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        UUID uuid = gen.generate();
//version=1
        System.out.println(uuid.version());
//32位33304096c01311e58c16089e01cd1de9
        System.out.println(uuid.toString());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(uuid.timestamp()/1000000*100);
        cal.add(Calendar.YEAR, 	1582-1970);
        cal.add(Calendar.MONTH, 10);
        cal.add(Calendar.DAY_OF_MONTH, 15);
//TODO:输出2016-02-24 17:06:14，其实当前系统时间是2016-01-21 17:06:14，有几天的误差
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
    }
}
