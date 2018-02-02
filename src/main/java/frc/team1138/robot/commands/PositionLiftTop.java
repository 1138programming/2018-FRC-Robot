package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.Robot;

/**
 *
 */
public class PositionLiftTop extends Command
{
	public PositionLiftTop()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.LIFT);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute()
	{
		Robot.LIFT.liftWithEncoders(30000, 0.7); //TODO figure out the actual values of this too too
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