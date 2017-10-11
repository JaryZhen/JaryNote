package headfirst.test.command;

/**
 * Created by Jary on 2017/7/24 0024.
 */
public class SimpleRemoteControl {
    Command slot;
    public SimpleRemoteControl(){

    }
    public void setCommand(Command command){
        slot = command;
    }
    public void buttonWasPressed(){
        slot.execute();
    }
}
