package frc.team1138.robot.AutoCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1138.robot.Robot;
import frc.team1138.robot.MotionProfile.CrossLine;
import openrio.powerup.MatchData;

public class LeftCommand extends CommandGroup
{
    public LeftCommand()
    {
        requires(Robot.DRIVE_BASE);
        MatchData.OwnedSide switchSide = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        addSequential(new TestMotionProfile(CrossLine.left, CrossLine.right));
        // if (switchSide == MatchData.OwnedSide.LEFT)
        // {
        //     // Do something 
        // } 
        // else if (switchSide == MatchData.OwnedSide.RIGHT)
        // {
        //     // addSequential(new TestMotionProfile(Middle2Right.left_Part1, Middle2Right.right_Part1));
        //     // addSequential(new TestMotionProfile(Middle2Right.left_Part2, Middle2Right.right_Part2));
        // }
        // else {
        //     // Do something when you cannot get the Side of the SWITCH
        //     // I.E. flash the LEDs
        // }
    }
}
