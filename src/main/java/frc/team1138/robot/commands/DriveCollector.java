package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.OI;
import frc.team1138.robot.Robot;

/**
 *
 */
public class DriveCollector extends Command
{
	private OI oi;

	public DriveCollector()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.COLLECTOR);
		oi = new OI();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		//Experimental Stuff Goes Here
		Robot.COLLECTOR.driveCollector(oi.getRightTrigger()*0.75, oi.getLeftTrigger()*0.75);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
}
