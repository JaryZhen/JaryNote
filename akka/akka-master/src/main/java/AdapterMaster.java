import akka.actor.AbstractActor;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: jaryzhen
 * @date: 2022/3/7
 */
@Slf4j
public class AdapterMaster extends AbstractActor {
    @Override
    public void preStart() throws Exception {
        log.info("prestart");
    }

    @Override
    public Receive createReceive() {

        return receiveBuilder()
                .match(String.class, d -> {
                    System.out.println("yes ,,, gotit ...");
                })
                .build();
    }

}
