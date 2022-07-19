import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @description:
 * @author: jaryzhen
 * @date: 2022/3/9
 */
public class WorkerMain {

    private static final String ACTOR_SYSTEM_NAME = "adapterfunction";
    private ActorSystem actorSystem;
    private ActorRef adapterWorker;

    WorkerMain() {
        actorSystem = ActorSystem.create(ACTOR_SYSTEM_NAME);
        adapterWorker = actorSystem.actorOf(Props.create(AdapterWorker.class), "adapterWorker");
    }

    public static void main(String[] args) {

        WorkerMain process = new WorkerMain();
        process.exc();
    }

    private void exc() {

        adapterWorker.tell("helll ", adapterWorker);
    }


}
