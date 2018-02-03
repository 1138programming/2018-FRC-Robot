package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1138.robot.OI;
import frc.team1138.robot.Robot;
import frc.team1138.robot.commands.DriveWithEncoders;
import frc.team1138.robot.subsystems.*;

public class CrossLine extends CommandGroup
{
	private OI oi;

	public CrossLine()
	{
		requires(Robot.DRIVE_BASE);
		oi = new OI();
		//addSequential(new TurnWithGyro());
		addSequential(new DriveWithEncoders(11, 1));
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
