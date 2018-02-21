package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1138.robot.Robot;

public class MiddleCommand extends CommandGroup
{
//	String gameData = DriverStation.getInstance().getGameSpecificMessage();
	public MiddleCommand()
	{
		requires(Robot.DRIVE_BASE);
		addSequential(new TestMotionProfile());
//		requires(Robot.ARM);
//		if(gameData!=null && gameData.charAt(0) == 'L')
//		{
//			addSequential(new TurnWithGyro(6, 5)); //TODO revise this when motion profiling's done
//		}
//		else
//		{
//			addSequential(new DriveWithEncoders(5, 6)); //TODO also revise this
//		}
	}
}
