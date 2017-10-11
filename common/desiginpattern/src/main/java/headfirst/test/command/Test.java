package headfirst.test.command;

import headfirst.designpatterns.command.party.*;
import headfirst.designpatterns.command.remote.GarageDoor;

/**
 * Created by Jary on 2017/7/24 0024.
 */
public class Test {
    public static void main(String[] args) {
        SimpleRemoteControl simpleRemoteControl = new SimpleRemoteControl();
        Light light = new Light("asdf");
        LightOnCommand lightOnCommand = new LightOnCommand(light);
        simpleRemoteControl.setCommand(lightOnCommand);
        simpleRemoteControl.buttonWasPressed();

        GarageDoor doo = new GarageDoor("d");
        GarageDorrOpenCommand g = new GarageDorrOpenCommand(doo);
        simpleRemoteControl.setCommand(g);
        simpleRemoteControl.buttonWasPressed();
    }
}
