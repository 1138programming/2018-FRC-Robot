package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.Robot;
import frc.team1138.robot.subsystems.Lift.LiftPos;

/**
 *
 */
public class PositionLift extends PIDCommand
{
	LiftPos pos;

	public PositionLift(LiftPos pos)
	{
		super("Lift PID", 0.0, 0, 0.0); // Sets up as PID loop TODO mess with these values
		getPIDController().setAbsoluteTolerance(50); // error allowed
		getPIDController().setContinuous(false); // Change based on need, probably should be continuous
		getPIDController().setInputRange(-30000, 30000); // TODO figure out range after getting the bot
		getPIDController().setOutputRange(-1.0, 1.0);
		getPIDController().enable();
		// Use requires() here to declare subsystem dependencies
		requires(Robot.LIFT);
		this.pos = pos;
		setName("Lift subsystem", "Position PIDCommand");
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		switch (this.pos)
		{
			case BUTTOM:
				this.setSetpoint(0);
				break;
			case MIDDLE:
				this.setSetpoint(30000.0/2.0);
				break;
			case TOP:
				this.setSetpoint(30000.0);
				break;
		}
		
	}

	@Override
	protected double returnPIDInput() {
		return Robot.LIFT.getEncoderValue();
	}

	@Override
	protected void usePIDOutput(double output) 
	{
		if (!getPIDController().onTarget())
		{
			SmartDashboard.putNumber("Lift PID Output", output);
			Robot.LIFT.setLift(output);
		}
		else
		{
			Robot.LIFT.setLift(0);
		}	
	}

	// Called repeatedly when this Command is scheduled to run
	// Lifts (or lowers) the lift using the encoders and PID
	protected void execute()
	{
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return getPIDController().onTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Robot.LIFT.setLift(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		end();
	}
}