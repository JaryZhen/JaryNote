package jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

/**
 * Created by Jary on 2017/9/21 0021.
 */
public class MemoryPool {
    public static void main(String[] args) {
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        int poolFound = 0;
        int poolWithStats = 0;

        for (int i = 0; i < pools.size(); i++) {
            MemoryPoolMXBean pool = pools.get(i);
            String name = pool.getName();
            System.out.println("found pool :"+name);
            //if(name.contains(poolname))
        }
    }
}
