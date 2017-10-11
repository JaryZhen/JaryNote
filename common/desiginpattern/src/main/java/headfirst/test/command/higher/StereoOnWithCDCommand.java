package headfirst.test.command.higher;

import headfirst.designpatterns.command.party.Stereo;
import headfirst.test.command.Command;

/**
 * Created by Jary on 2017/7/24 0024.
 */
public class StereoOnWithCDCommand implements Command{

    Stereo stereo;
    StereoOnWithCDCommand(Stereo stereo){
        this.stereo = stereo;
    }
    @Override
    public void execute() {
        stereo.on();
        stereo.setCD();
        stereo.setVolume(1);

    }
}
