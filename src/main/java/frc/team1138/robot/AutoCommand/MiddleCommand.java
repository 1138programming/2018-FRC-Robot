package frc.team1138.robot.AutoCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1138.robot.MotionProfile.Ways;
import frc.team1138.robot.commands.PositionLift;
import frc.team1138.robot.subsystems.Lift.LiftPos;
// import frc.team1138.robot.MotionProfile.Middle2Right;
import openrio.powerup.MatchData;

public class MiddleCommand extends CommandGroup
{
	public MiddleCommand()
	{
		MatchData.OwnedSide switchSide = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
		
		if (switchSide == MatchData.OwnedSide.LEFT)
		{
			addSequential(new TrajectoryCommand(Ways.MID_2_LEFT_SWITCH, 8, 5, 70, 0.05, 2.25));
		} 
		else if (switchSide == MatchData.OwnedSide.RIGHT)
		{
			addParallel(new TrajectoryCommand(Ways.MID_2_RIGHT_SWITCH, 8, 5, 70, 0.05, 2.25));
			addParallel(new PositionLift(LiftPos.MIDDLE, 1.0));
		}
		else {
			addSequential(new TrajectoryCommand(Ways.CROSS_LINE, 8, 5, 70, 0.05, 2.25));
		}
	}
}
