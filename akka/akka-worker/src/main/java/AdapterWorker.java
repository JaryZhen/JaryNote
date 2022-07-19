import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import com.typesafe.config.ConfigFactory;


/**
 * @description:
 * @author: jaryzhen
 * @date: 2022/3/8
 */
public class AdapterWorker extends AbstractActor {

    String path = ConfigFactory.defaultApplication().getString("remote.actor.name.test");

    private ActorSelection adapterMaster = getContext().actorSelection(path);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, r -> {
                    System.out.println(r);
                    adapterMaster.tell("hi ...", getSelf());
                })
                .build();
    }

}
