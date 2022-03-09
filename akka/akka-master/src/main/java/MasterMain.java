import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @description:
 * @author: jaryzhen
 * @date: 2022/3/9
 */
public class MasterMain {

    private static final String ACTOR_SYSTEM_NAME = "adapterfunction";
    private ActorSystem actorSystem;
    private ActorRef adapterMaster;

    MasterMain() {
        actorSystem = ActorSystem.create(ACTOR_SYSTEM_NAME);
        adapterMaster = actorSystem.actorOf(Props.create(AdapterMaster.class), "adapterMaster");
    }

    public static void main(String[] args) {

        MasterMain process = new MasterMain();
        process.exc();
    }

    private void exc() {

        adapterMaster.tell("helll ", adapterMaster);
    }



}
