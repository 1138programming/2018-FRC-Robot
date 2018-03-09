package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.Robot;
import frc.team1138.robot.subsystems.Lift.LatchPos;

/**
 *
 */
public class ToggleRatchet extends Command
{
	public ToggleRatchet()
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
	@Override
	protected void execute()
	{
		Robot.LIFT.ratchetIt();
		// Robot.LIFT.moveLatch(LatchPos.AUTON_POS);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return true;
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
