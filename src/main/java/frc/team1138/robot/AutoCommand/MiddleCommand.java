package frc.team1138.robot.AutoCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1138.robot.Robot;
import frc.team1138.robot.MotionProfile.CrossLine;
import frc.team1138.robot.MotionProfile.Middle2Right;
// import frc.team1138.robot.MotionProfile.Middle2Right;
import openrio.powerup.MatchData;

public class MiddleCommand extends CommandGroup
{
	public MiddleCommand()
	{
		requires(Robot.DRIVE_BASE);
		MatchData.OwnedSide switchSide = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
		// if (switchSide == MatchData.OwnedSide.LEFT)
		// {
		// 	// Do something 
		// } 
		// else if (switchSide == MatchData.OwnedSide.RIGHT)
		// {
		// 	addSequential(new TestMotionProfile(Middle2Right.left_Part1, Middle2Right.right_Part1));
		// 	// addSequential(new TestMotionProfile(Middle2Right.left_Part2, Middle2Right.right_Part2));
		// }
		// else {
		// 	// Do something when you cannot get the Side of the SWITCH
		// 	// I.E. flash the LEDs
		// }
//		requires(Robot.ARM);
	}
}
