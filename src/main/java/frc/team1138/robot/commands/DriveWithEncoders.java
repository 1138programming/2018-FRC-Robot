package frc.team1138.robot.commands;

import java.util.concurrent.RecursiveTask;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.Robot;

public class DriveWithEncoders extends PIDCommand
{
	double distance, speed; // parameter to set in the command group for the PID distance travel by
							// rotations * tick per rotation and the speed of the output range
	private static double P = 0.0002, I = 0.0, D = 0.0001; // sets the PID
	private PIDController driveController;
	double DistanceToTarget = 0; // resets the PID
	public static final double KTicksPerRotation = 4096; // ticks

	public DriveWithEncoders(double distance, double speed)
	{
		super("Drive Distance", P, I, D);
		SmartDashboard.putNumber("setEncoder", 0);
		requires(Robot.DRIVE_BASE);
		this.distance = distance; // defines parameter
		this.speed = speed; // defines parameter
		driveController = this.getPIDController();
		driveController.setInputRange(-20000000, 20000000); // sets the max input range
		driveController.setOutputRange(-speed, speed); // sets the max output range
		driveController.setAbsoluteTolerance(100); // Error allowed
		driveController.setContinuous(true); // sets the value to be continuous throughout the command
		Robot.DRIVE_BASE.resetEncoders(); // resets the encoders
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Robot.DRIVE_BASE.resetEncoders();// resets the encoder
		setTarget(distance * KTicksPerRotation); // set the rotation needed for the distance * the ticksperRotation to
													// go that distance
	}

	@Override
	protected double returnPIDInput()
	{
		return (Robot.DRIVE_BASE.getLeftEncoderValue()); // returns left encoder value to the code
	}

	public void setTarget(double ticks)
	{
		this.driveController.setSetpoint(ticks); // sets the target of the PID
	}

	@Override
	protected void usePIDOutput(double output)
	{
		// The side with the GEAR IS THE FRONT!
		if (!driveController.onTarget())
		{
			if (this.returnPIDInput() - this.getSetpoint() < 0)
			{ // need to move forward
				System.out.println("Move Forward");
				System.out.println("Left Motor: " + (output) + "Right Motor: " + (output));
				SmartDashboard.putNumber("Distance From Target", this.returnPIDInput() - this.getSetpoint());
				Robot.DRIVE_BASE.tankDrive(-output, -output);
			}
			else if (this.returnPIDInput() - this.getSetpoint() > 0)
			{ // need to move backward
				System.out.println("Move Backward");
				System.out.println("Left Motor: " + (-output) + "Right Motor: " + (-output));
				Robot.DRIVE_BASE.tankDrive(-output, -output);
			}
			System.out.println("set point: " + this.getSetpoint());
			System.out.println("Current Encoder Value: " + Robot.DRIVE_BASE.getLeftEncoderValue());
			System.out.println("Error: " + (Robot.DRIVE_BASE.getLeftEncoderValue() - this.getSetpoint()));
			System.out.println("Input: " + this.returnPIDInput());
			System.out.println("On Target: " + driveController.onTarget());
		}
		else
		{
			Robot.DRIVE_BASE.tankDrive(0, 0);
			System.out.println("On Target: " + driveController.onTarget());
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// final double setEncoder = SmartDashboard.getNumber("setEncoder", 0);

		// setTarget(setEncoder);

		setTarget(distance * KTicksPerRotation); // set the rotation needed for the distance * the ticksperRotation to
													// go that distance
		SmartDashboard.putBoolean("tracking", true);
		SmartDashboard.putNumber("Left", Robot.DRIVE_BASE.getLeftEncoderValue());
		SmartDashboard.putNumber("Right", Robot.DRIVE_BASE.getRightEncoderValue());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		System.out.println("On Target: " + driveController.onTarget());
		SmartDashboard.putNumber("Error", this.returnPIDInput() - this.getSetpoint());
		return driveController.onTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Robot.DRIVE_BASE.tankDrive(0, 0);
		Robot.DRIVE_BASE.resetEncoders();
		SmartDashboard.putNumber("setEncoder", 0);
		SmartDashboard.putBoolean("tracking", false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{

	}
}
