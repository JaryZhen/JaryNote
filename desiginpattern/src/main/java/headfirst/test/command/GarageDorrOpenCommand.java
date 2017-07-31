package headfirst.test.command;

import headfirst.designpatterns.command.party.Light;
import headfirst.designpatterns.command.remote.GarageDoor;

/**
 * Created by Jary on 2017/7/24 0024.
 */
public class GarageDorrOpenCommand implements Command {

    GarageDoor light ;
    GarageDorrOpenCommand (){

    }
    public GarageDorrOpenCommand(GarageDoor light){
        this.light = light;
    }
    @Override
    public void execute() {
        light.lightOn();
    }
}
