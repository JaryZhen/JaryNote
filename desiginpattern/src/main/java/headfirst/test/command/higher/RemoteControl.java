package headfirst.test.command.higher;

import headfirst.designpatterns.command.party.NoCommand;
import headfirst.test.command.Command;

/**
 * Created by Jary on 2017/7/24 0024.
 */
public class RemoteControl {

    Command[] onCommand;
    Command[] offCommand;
    public RemoteControl(){
    onCommand = new Command[7];
        offCommand = new Command[7];
        Command no = (Command) new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommand[i] = no;
            offCommand[i] = no ;
        }
    }

    public void setCommand (int slot ,Command on,Command off){
        onCommand[slot] = on;
        offCommand[slot] = off;
    }
    public void onButtonWasPushed(int slot){
        onCommand[slot].execute();
    }
    public void offButonWasPushed(int slot){
        offCommand[slot].execute();
    }
}

