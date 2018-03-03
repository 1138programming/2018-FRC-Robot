package frc.team1138.robot.AutoCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1138.robot.Robot;
import frc.team1138.robot.MotionProfile.SwitchOnRight2Right;
import openrio.powerup.MatchData;

public class RightCommand extends CommandGroup
{
    public RightCommand()
    {
        requires(Robot.DRIVE_BASE);
        MatchData.OwnedSide switchSide = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        if (switchSide == MatchData.OwnedSide.LEFT)
        {
            // Do something 
        } 
        else if (switchSide == MatchData.OwnedSide.RIGHT)
        {
            addSequential(new TestMotionProfile(SwitchOnRight2Right.left, SwitchOnRight2Right.right));
        }
        else {
            // Do something when you cannot get the Side of the SWITCH
            // I.E. flash the LEDs
        }
    }
}
