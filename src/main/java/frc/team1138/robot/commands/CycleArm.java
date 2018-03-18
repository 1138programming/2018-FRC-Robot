package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.Robot;
import frc.team1138.robot.subsystems.Arm.ArmPos;

/**
 *
 */
public class CycleArm extends PIDCommand
{
	ArmPos armPos;
	double delay;
	double start = 0; 
	double diff = 0; 
	public CycleArm(ArmPos pos, double delaySec)
	{
		super(0.0008, 0, 0.0004);
		requires(Robot.ARM);
		getPIDController().setAbsoluteTolerance(100); // error allowed
		getPIDController().setContinuous(true); // Change based on need, probably should be continuous
		getPIDController().setInputRange(-15000, 15000); // TODO figure out range after getting the bot
		getPIDController().setOutputRange(-0.75, 0.75);
		this.armPos = pos;
		this.delay = delaySec;
		getPIDController().setName("Arm", "Arm Pos PIDCommand");
		setName("Arm", "Arm PID");
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
		return Robot.ARM.returnEncoderValue();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (!getPIDController().onTarget())
		{
			SmartDashboard.putNumber("Arm PID Output", output);
			Robot.ARM.driveArm(output);
		}
		else
		{
			Robot.ARM.driveArm(0);
		}	
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		diff = Timer.getFPGATimestamp()-start;
		if (diff >= delay) {
			switch (this.armPos)
			{
				case PERPENDICULAR:
					this.setSetpoint(0);
					break; 
				case FLAT:
					this.setSetpoint(14000.0/2.0);
					break;
				case HALF:
					this.setSetpoint(14000.0/4.0);
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
		Robot.ARM.driveArm(0);
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