package frc.team1138.robot.AutoCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1138.robot.MotionProfile.Ways;
import frc.team1138.robot.commands.CycleArm;
import frc.team1138.robot.commands.EjectCube;
import frc.team1138.robot.commands.PositionLift;
import frc.team1138.robot.subsystems.Arm.ArmPos;
import frc.team1138.robot.subsystems.Lift.LiftPos;
import openrio.powerup.MatchData;

public class RightCommand extends CommandGroup
{
    public RightCommand()
    {
        MatchData.OwnedSide switchSide = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        MatchData.OwnedSide scaleSide = MatchData.getOwnedSide(MatchData.GameFeature.SCALE);
        if (scaleSide == MatchData.OwnedSide.LEFT)
        {
            addSequential(new TrajectoryCommand(Ways.RIGHT_FAR_SCALE_Part1, 8, 5, 70, 0.05, 2.25));
            addSequential(new TrajectoryCommand(Ways.RIGHT_FAR_SCALE_Part2, 8, 5, 70, 0.05, 2.25));
            addParallel(new TrajectoryCommand(Ways.RIGHT_FAR_SCALE_PART3, 8, 5, 70, 0.05, 2.25));
            addParallel(new PositionLift(LiftPos.TOP, 4.0));
            // addParallel(new CycleArm(ArmPos.FLAT, 6.0));
            // addSequential(new EjectCube());
        } 
        else if (scaleSide == MatchData.OwnedSide.RIGHT)
        {
            addParallel(new TrajectoryCommand(Ways.RIGHT_NEAR_SCALE, 8, 5, 70, 0.05, 2.25));
            addParallel(new PositionLift(LiftPos.TOP, 4.0));
            // addParallel(new CycleArm(ArmPos.FLAT, 6.0));
            addSequential(new EjectCube());
        }
        else {
            // Do something when you cannot get the Side of the SWITCH
            // I.E. flash the LEDs
        }
    }
}
