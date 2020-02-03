package test;

import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {

        Integer productId = 333;
        HashSet<String>  roomId = null;

        Strategy s1 = new Strategy();
        s1.setLine(2323);
        s1.setProductId(333);
        s1.setRoomIds(null);

        Strategy s2 = new Strategy();
        s2.setLine(2323);
        s2.setProductId(333);
        s2.setRoomIds(null);

        List<Strategy> listSg = new ArrayList<>();
        listSg.add(s1);
        listSg.add(s2);

        Header header = new Header();
        header.setVersion(1559545058706L);

        LineStrategy roomStrategy = new LineStrategy();

        roomStrategy.setHeader(header);
        roomStrategy.setDefaultLine(1);
        //roomStrategy.setStrategy(null);


        List<Strategy> elseSg = new ArrayList<>();
        elseSg.add(s2);

/*
*
*
* {"header":{"version":1559545058706},"defaultLine":"1","strategy":[]}
* {"header":{"version":1559545058706},"defaultLine":"2","strategy":[{},{},{}]}*/

        System.out.println(roomStrategy.toString());

        List<Strategy> line2 = Optional.ofNullable(roomStrategy.getStrategy())
                .map(e->e.stream()
                        .filter(s-> s.getProductId().equals(productId))
                        .collect(Collectors.toList()))
                .orElse(roomStrategy.getDefaultStrategy(elseSg))

                ;

        System.out.println(line2);
/*
        Integer line2 = Optional.ofNullable(roomStrategy.getStrategy())
                .map(e->e.stream()
                        .filter(s-> s.getProductId().equals(productId))
                        .collect(Collectors.toList()))
                //ETCDCTL_API=3 etcdctl put ol/signal/strategy/line/room   '{"header":{"version":1559545058706},"defaultLine":"1","strategy":[]}' 时报错
                .map(e -> (
                   *//* Strategy sg = null;
                    Iterator<Strategy> iterSg = e.iterator();
                    while (iterSg.hasNext()){
                         sg = iterSg.next();
                        if (sg.getLine().equals("uou"));
                        return sg;
                    }
                    return sg;*//*
                   e.get(0) == null
                )).map(e -> e.getLine()).orElse(e -> e);*/




    }
}
