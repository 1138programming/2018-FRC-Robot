package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.Timer;
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
	double delay;
	double start = 0; 
	double diff = 0; 
	public PositionLift(LiftPos pos, double delaySec)
	{
		super("Lift PID", 0.0008, 0, 0.0004); // Sets up as PID loop TODO mess with these values
		getPIDController().setAbsoluteTolerance(200); // error allowed
		getPIDController().setContinuous(false); // Change based on need, probably should be continuous
		getPIDController().setInputRange(-30000, 30000); // TODO figure out range after getting the bot
		getPIDController().setOutputRange(-0.75, 0.75);
		// Use requires() here to declare subsystem dependencies
		requires(Robot.LIFT);
		this.pos = pos;
		this.delay = delaySec;
		getPIDController().setName("Lift subsystem", "Position PIDCommand");
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		getPIDController().enable();
		start = Timer.getFPGATimestamp();
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
		diff = Timer.getFPGATimestamp()-start;
		if (diff >= delay) 
		{
			switch (this.pos)
			{
				case BUTTOM:
					this.setSetpoint(0);
					break;
				case MIDDLE:
					this.setSetpoint(21750.0/2.0);
					break;
				case TOP:
					this.setSetpoint(21750.0);
					break;
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return (diff >= delay && getPIDController().onTarget());
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Robot.LIFT.setLift(0);
		getPIDController().disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		end();
	}
}