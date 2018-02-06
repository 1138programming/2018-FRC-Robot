package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.Robot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnWithGyro extends PIDCommand
{
	double targetAngle, speed;
	private static double P = 0.1, I = 0.0, D = 0.0;
	private PIDController turnController;

	public TurnWithGyro(double targetAngle, double speed)
	{
		super("Turn Angle", P, I, D);
		requires(Robot.DRIVE_BASE);
		this.targetAngle = targetAngle;
		this.speed = speed;
		turnController = this.getPIDController();
		turnController.setInputRange(-360, 360);
		turnController.setOutputRange(-1, 1);
		turnController.setAbsoluteTolerance(1.5);
		turnController.setContinuous(true);
		Robot.DRIVE_BASE.resetGyro();
		Robot.DRIVE_BASE.resetEncoders();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Robot.DRIVE_BASE.resetGyro();
		Robot.DRIVE_BASE.resetEncoders();
		setTarget(targetAngle);
	}

	@Override
	protected double returnPIDInput()
	{
		// TODO Auto-generated method stub
		// SmartDashboard.putNumber("Error",
		// (Robot.DRIVE_BASE.getAngle()-initialAngle));
		return (Robot.DRIVE_BASE.getAngle());
	}

	@Override
	protected void usePIDOutput(double output)
	{
		// The side with the GEAR IS THE FRONT!
		if (!turnController.onTarget())
		{
			if (this.returnPIDInput() - this.getSetpoint() > 0)
			{ // need to turn left
				System.out.println("turn left");
				System.out.println("Left Motor: " + (-output) + "Right Motor: " + (output));
				Robot.DRIVE_BASE.tankDrive(-output, output);
			}
			else if (this.returnPIDInput() - this.getSetpoint() < 0)
			{ // need to turn right
				System.out.println("turn right");
				System.out.println("Left Motor: " + (-output) + "Right Motor: " + (output));
				Robot.DRIVE_BASE.tankDrive(-output, output);
			}
			System.out.println("set point: " + this.getSetpoint());
			System.out.println("Current Angle: " + Robot.DRIVE_BASE.getAngle());
			System.out.println("Error: " + (Robot.DRIVE_BASE.getAngle() - this.getSetpoint()));
			System.out.println("Input: " + this.returnPIDInput());
			System.out.println("On Target: " + turnController.onTarget());
		}
		else
		{
			Robot.DRIVE_BASE.tankDrive(0, 0);
			System.out.println("On Target: " + turnController.onTarget());
		}
	}

	// Called repeatedly when this Command is scheduled to run

	@Override
	protected void execute()
	{
		setTarget(targetAngle);
		//double setAngle = SmartDashboard.getNumber("setAngle", 0);
		//double kP = SmartDashboard.getNumber("kP", 1.0);
		//
		//setTarget(setAngle);
		turnController.setPID(0.1,0,0);
		// turnController.setPID(kP,0,0);
		// SmartDashboard.putBoolean("tracking",true);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		System.out.println("On Target: " + turnController.onTarget());
		// return false;
		return turnController.onTarget();
	}

	public void setTarget(double angle)
	{
		this.turnController.setSetpoint(angle);
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Robot.DRIVE_BASE.tankDrive(0, 0);
		Robot.DRIVE_BASE.resetGyro();
		SmartDashboard.putNumber("setAngle", 0);
		SmartDashboard.putBoolean("tracking", false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
}