package frc.team1138.robot.AutoCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.AutoCommand.TrajectoryCommand.GearRatio;
import frc.team1138.robot.MotionProfile.Ways;
import frc.team1138.robot.commands.EjectCube;
import frc.team1138.robot.commands.PositionLift;
import frc.team1138.robot.subsystems.Lift.LiftPos;
// import frc.team1138.robot.MotionProfile.Middle2Right;
import openrio.powerup.MatchData;

public class MiddleCommand extends CommandGroup
{
	public MiddleCommand()
	{
		MatchData.OwnedSide switchSide = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
		addSequential(new TrajectoryCommand(Ways.CROSS_LINE, 8, 5, 70, 0.05, 2.25, GearRatio.LOW_GEAR));
		// if (switchSide == MatchData.OwnedSide.LEFT)
		// {
		// 	SmartDashboard.putString("Mid", "Left");
		// 	// addSequential(new TrajectoryCommand(Ways.MID_2_LEFT_SWITCH, 8, 5, 70, 0.05, 2.25, GearRatio.LOW_GEAR));
		// } 
		// else if (switchSide == MatchData.OwnedSide.RIGHT)
		// {
		// 	SmartDashboard.putString("Mid", "Right");
		// 	// addSequential(new TrajectoryCommand(Ways.MID_2_RIGHT_SWITCH, 8, 5, 70, 0.05, 2.25, GearRatio.LOW_GEAR));
		// 	// addSequential(new PositionLift(LiftPos.MIDDLE, 1.0));
		// 	// addSequential(new EjectCube());
		// }
		// else 
		// {
		// 	SmartDashboard.putString("Mid", "Crap");
		// 	// addSequential(new TrajectoryCommand(Ways.TEST_45, 8, 5, 70, 0.05, 2.25, GearRatio.LOW_GEAR));
		// 	// addSequential(new PositionLift(LiftPos.MIDDLE, 0));
		// }
	}
}
