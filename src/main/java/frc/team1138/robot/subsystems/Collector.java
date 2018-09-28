package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.commands.DriveCollector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class Collector extends Subsystem
{
	// Declaring the talons, limit switch and solenoid
	private TalonSRX rightCollector, leftCollector;
	private Solenoid plunger;
	private DigitalInput cubePossessionLimit;

	public static final int KLeftCollectorMotor = 10;
	public static final int KRightCollectorMotor = 11;
	public static final int KCubPossLimit = 0;
	public static final int KPlunger1 = 3;
//	public static final int KPlunger2 = 7;
	public static final double KCollectorSpeed = .7;
	public static final boolean KForward = true;
	public static final boolean KBackward = false;

	public Collector()
	{
		// setting the limit switch
		cubePossessionLimit = new DigitalInput(KCubPossLimit);

		// Setting up base talons
		rightCollector = new TalonSRX(KRightCollectorMotor);
		leftCollector = new TalonSRX(KLeftCollectorMotor);

		// Configuring the masters
		leftCollector.setInverted(true);

		// Solenoid
		plunger = new Solenoid(KPlunger1);
	}

	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		setDefaultCommand(new DriveCollector());
	}

	// This command causes the rollers to go at the KCollectorSpeed if the motors
	// aren't
	// moving, and if they are, then it doesn't move (to prevent them from doing
	// both at
	// the same time

	public void collectCubeWithRollersRight(double value)
	{
		rightCollector.set(ControlMode.PercentOutput, -value);
		SmartDashboard.putNumber("Right Collector", rightCollector.getMotorOutputPercent());
	}
	
	public void collectCubeWithRollersLeft(double value)
	{
		leftCollector.set(ControlMode.PercentOutput, value);
		SmartDashboard.putNumber("Left Collector", leftCollector.getMotorOutputPercent());
	}
	
	public void driveCollector(boolean collect)
	{
		if (collect) 
		{
			rightCollector.set(ControlMode.PercentOutput, -0.5);
			leftCollector.set(ControlMode.PercentOutput, -1);
		}
		else
		{
			rightCollector.set(ControlMode.PercentOutput, 0);
			leftCollector.set(ControlMode.PercentOutput, 0);
		}
		SmartDashboard.putNumber("Left Collector", leftCollector.getMotorOutputPercent());
		SmartDashboard.putNumber("Right Collector", leftCollector.getMotorOutputPercent());
	}
	
//	public void collectCubeWithRollers()
//	{
//		rightCollector.set(ControlMode.PercentOutput, -KCollectorSpeed);
//	}

	// This command causes the rollers to go at the -KCollectorSpeed if the motors
	// aren't
	// moving, and if they are, then it doesn't move (to prevent them from doing
	// both at
	// the same time
//	public void ejectCubeWithRollers()
//	{
//		if (rightCollector.getMotorOutputPercent() == 0 && leftCollector.getMotorOutputPercent() == 0)
//		{
//			rightCollector.set(ControlMode.PercentOutput, 1);
//			leftCollector.set(ControlMode.PercentOutput, 1);
//			SmartDashboard.putNumber("Right Collector Motor", rightCollector.getMotorOutputPercent());
//			SmartDashboard.putNumber("Left Collector Motor", leftCollector.getMotorOutputPercent());
//			kickCubeWithPlunger(); // TODO check which way
//		}
//		else
//		{
//			stopCollector();
//		}
//	}

	public void ejectCubeWithRollers()
	{
		rightCollector.set(ControlMode.PercentOutput, -1);
		leftCollector.set(ControlMode.PercentOutput, -1);
	}
	
//	public void stopCollector()
//	{
//		rightCollector.set(ControlMode.PercentOutput, 0);
//		leftCollector.set(ControlMode.PercentOutput, 0);
//	}
	
	public void stopCollectorRight()
	{
		rightCollector.set(ControlMode.PercentOutput, 0);
	}
	
	public void stopCollectorLeft()
	{
		leftCollector.set(ControlMode.PercentOutput, 0);
	}
	
	// The two methods cause the plunger to move forward or backward
	public void plungerForward()
	{
		plunger.set(/*DoubleSolenoid.Value.*/KForward);
	}

	public void plungerBackward()
	{
		plunger.set(/*DoubleSolenoid.Value.*/KBackward);
	}

	// The method causes the plunger to kick
	public void kickCubeWithPlunger()
	{
		plungerForward();
		Timer.delay(0.3);
		plungerBackward();
	}
	
	public void togglePlunger()
	{
		if(plunger.get() /*== DoubleSolenoid.Value.kForward*/)
		{
			plungerBackward();
		}
		else 
		{
			plungerForward();
		}
//		SmartDashboard.putString("Plunger", plunger.get().toString());
	}
}
