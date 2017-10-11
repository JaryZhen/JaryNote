package headfirst.test.command;

import headfirst.designpatterns.command.party.Light;

/**
 * Created by Jary on 2017/7/24 0024.
 */
public class LightOnCommand implements Command {
    Light light;
    LightOnCommand(Light light){
        this.light = light;
    }
    @Override
    public void execute() {
        light.on();
    }
}
