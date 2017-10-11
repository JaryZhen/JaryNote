package headfirst.test.command;

import headfirst.designpatterns.command.party.Light;
import headfirst.test.command.Command;

/**
 * Created by Jary on 2017/7/24 0024.
 */
public class LightOffCommand implements Command {

    Light light;
    LightOffCommand(Light light){
        this.light = light;
    }
    @Override
    public void execute() {
        light.off();
    }
}
