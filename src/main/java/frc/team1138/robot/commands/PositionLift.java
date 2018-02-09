package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.Robot;

/**
 *
 */
public class PositionLift extends Command
{
	double encoderValue;
	public PositionLift(double encoderValue)
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.LIFT);
		this.encoderValue = encoderValue;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		
	}

	// Called repeatedly when this Command is scheduled to run
	// Lifts (or lowers) the lift using the encoders and PID
	protected void execute()
	{
		Robot.LIFT.liftWithEncoders(encoderValue);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return Robot.LIFT.onTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Robot.LIFT.setLift(Robot.LIFT.getPosition());
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		end();
	}
}