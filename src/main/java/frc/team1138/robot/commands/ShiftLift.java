package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.Robot;

/**
 *
 */
public class ShiftLift extends Command
{

	public ShiftLift()
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
	// Shifts the lift between it's fast and slow speeds
	@Override
	protected void execute()
	{
		Robot.LIFT.toggleLiftSpeed();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
//		return Robot.LIFT.onTarget();
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
//		Robot.LIFT.setLift(Robot.LIFT.getPosition());
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		end();
	}
}
