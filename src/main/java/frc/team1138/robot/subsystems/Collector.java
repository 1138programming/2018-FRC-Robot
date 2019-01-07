package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.team1138.robot.commands.DriveCollector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class Collector extends Subsystem
{
	// Declaring the talons, limit switch and solenoid
	private TalonSRX XYTable, Appendage1;
	private Solenoid plunger;
	private DigitalInput cubePossessionLimit;

	public static final int KAppendage1Motor = 13;
	public static final int KXYTableMotor = 11;
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
		XYTable = new TalonSRX(KXYTableMotor);
		Appendage1 = new TalonSRX(KAppendage1Motor);

		// Configuring the masters
		Appendage1.setInverted(true);

		// Solenoid
		plunger = new Solenoid(KPlunger1);
	}

	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		//setDefaultCommand(new DriveCollector());
	}

	// This command causes the rollers to go at the KCollectorSpeed if the motors
	// aren't
	// moving, and if they are, then it doesn't move (to prevent them from doing
	// both at
	// the same time

	public void collectCubeWithRollersRight(boolean forward)
	{
		if (forward) 
		{
			XYTable.set(ControlMode.PercentOutput, 1);
		} 
		else 
		{
			XYTable.set(ControlMode.PercentOutput, -1);
		}
	}
	
	public void collectCubeWithRollersLeft(boolean forward)
	{
		if (forward) 
		{
			Appendage1.set(ControlMode.PercentOutput, 1);
		} 
		else 
		{
			Appendage1.set(ControlMode.PercentOutput, -1);
		}
	}
	
//	public void driveCollector(double rightAxis, double leftAxis)
//	{
//		XYTable.set(ControlMode.PercentOutput, rightAxis);
//		Appendage1.set(ControlMode.PercentOutput, leftAxis);
//	}
	
//	public void collectCubeWithRollers()
//	{
//		XYTable.set(ControlMode.PercentOutput, -KCollectorSpeed);
//	}

	// This command causes the rollers to go at the -KCollectorSpeed if the motors
	// aren't
	// moving, and if they are, then it doesn't move (to prevent them from doing
	// both at
	// the same time
//	public void ejectCubeWithRollers()
//	{
//		if (XYTable.getMotorOutputPercent() == 0 && Appendage1.getMotorOutputPercent() == 0)
//		{
//			XYTable.set(ControlMode.PercentOutput, 1);
//			Appendage1.set(ControlMode.PercentOutput, 1);
//			SmartDashboard.putNumber("Right Collector Motor", XYTable.getMotorOutputPercent());
//			SmartDashboard.putNumber("Left Collector Motor", Appendage1.getMotorOutputPercent());
//			kickCubeWithPlunger(); // TODO check which way
//		}
//		else
//		{
//			stopCollector();
//		}
//	}

//	public void ejectCubeWithRollers()
//	{
//		XYTable.set(ControlMode.PercentOutput, 1);
//		Appendage1.set(ControlMode.PercentOutput, 1);
//	}
	
//	public void stopCollector()
//	{
//		XYTable.set(ControlMode.PercentOutput, 0);
//		Appendage1.set(ControlMode.PercentOutput, 0);
//	}
	
	public void stopCollectorRight()
	{
		XYTable.set(ControlMode.PercentOutput, 0);
	}
	
	public void stopCollectorLeft()
	{
		Appendage1.set(ControlMode.PercentOutput, 0);
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
