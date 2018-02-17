package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1138.robot.Robot;

public class RightCommand extends CommandGroup
{
	String gameData = DriverStation.getInstance().getGameSpecificMessage();
	public RightCommand()
	{
		requires(Robot.DRIVE_BASE);
		requires(Robot.ARM);
		if(gameData.charAt(0) == 'L')
		{
			addSequential(new TurnWithGyro(6, 5)); //TODO change this when motion profiling's done
		}
		else
		{
			addSequential(new DriveWithEncoders(5, 6)); //TODO also edit this
		}
	}
}