package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.commands.DriveWithJoysticks;

import com.ctre.phoenix.sensors.PigeonIMU;

import java.util.concurrent.TimeUnit;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 *This is the base subsystem for the robot called Keystone.
 */
public class DriveBase extends Subsystem
{
	// Declaring the talons, sensors, and solenoids
	private TalonSRX baseLeftFront, baseLeftBack, baseRightFront, baseRightBack;
	// private PigeonIMU pigeonIMU;
	private DoubleSolenoid shifterSolenoid;

	// Making variables for base talon slots so there aren't magic numbers floating
	// around
	public static final int KBaseLeftFrontTalon = 1;
	public static final int KBaseLeftBackTalon = 2;
//	public static final int KBaseLeftTopTalon = 3;
	public static final int KBaseRightFrontTalon = 4;
	public static final int KBaseRightBackTalon = 5;
//	public static final int KBaseRightTopTalon = 6;
	public static final double KDeadZoneLimit = 0.2; //Limit for driving
	
	// All of the solenoids are doubles, so they need 2 numbers each. If you change
	// one, be sure to change the other one of the pair also.
	public static final int KShifterSolenoid1 = 1;
	public static final int KShifterSolenoid2 = 2;

	// Variable for base ultrasonic
	// TODO figure out what these numbers will be based on where they're gonna be
	// plugged in
	public static final int KBaseUltrasonic = 1;

	
	public DriveBase()
	{
		// Setting up base talons
		baseLeftFront = new TalonSRX(KBaseLeftFrontTalon);
		baseLeftBack = new TalonSRX(KBaseLeftBackTalon);
		baseRightFront = new TalonSRX(KBaseRightFrontTalon);
		baseRightBack = new TalonSRX(KBaseRightBackTalon);

		// If you need to reverse some motors compared to the joysticks, here's how...
//		baseLeftFront.setSensorPhase(true);
//		baseLeftBack.setSensorPhase(true);
		
		// Configuring the masters
		baseLeftFront.setInverted(true);
		baseLeftBack.setInverted(true);

		// Configuring the slaves
		baseLeftBack.set(ControlMode.Follower, baseLeftFront.getDeviceID());
		baseRightBack.set(ControlMode.Follower, baseRightFront.getDeviceID());

		// Configuring the solenoids and sensors
		shifterSolenoid = new DoubleSolenoid(KShifterSolenoid1, KShifterSolenoid2); // Solenoid
		// pigeonIMU = new PigeonIMU(baseLeftBack); // Gyro
		// pigeonIMU.setYaw(0, 0); // Basically, this resets the gyro
		baseLeftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); // Left Encoder
		baseRightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); // Right Encoder
		
		// This code is for ramping the base, but we don't want it right now
//		baseRightFront.configOpenloopRamp(1, 0);
//		baseRightBack.configOpenloopRamp(1, 0);
//		baseLeftFront().configOpenloopRamp(1, 0);
//		baseLeftBack.configOpenloopRamp(1, 0);
	}
	
	
	/* 
	 * The order for methods as of 3 March 2018 (edit if needed):
	 * Default Command
	 * Basic Base Functionality (calling talons, driving the base, etc.)
	 * Sticky Faults
	 * Solenoids
	 * Gyro
	 * Encoders
	 * Motion Profiling
	*/
	
	//This is the default command that runs when no other command is currently running
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		setDefaultCommand(new DriveWithJoysticks());
	}
	
	// These four methods just return the base talons if we need to access them somewhere else
	public TalonSRX getBaseLeftFront()
	{
		return this.baseLeftFront;
	}
	public TalonSRX getBaseRightFront()
	{
		return this.baseRightFront;
	}
	public TalonSRX getBaseLeftBack()
	{
		return this.baseLeftBack;
	}
	public TalonSRX getBaseRightBack()  
	{
		return this.baseRightBack;
	}
	
	// Used to drive the base in a "tank drive" format, this is the standard
	public void tankDrive(double left, double right)
	{
		SmartDashboard.putNumber("Left Base Input", left);
		SmartDashboard.putNumber("Right Base Input", right);
		if (left > KDeadZoneLimit || left < -KDeadZoneLimit)
		{
			baseLeftFront.set(ControlMode.PercentOutput, left);
		}
		else
		{
			baseLeftFront.set(ControlMode.PercentOutput, 0);
		}

		if (right > KDeadZoneLimit || right < -KDeadZoneLimit)
		{
			baseRightFront.set(ControlMode.PercentOutput, right);
		}
		else
		{
			baseRightFront.set(ControlMode.PercentOutput, 0);
		}
	}
	
	public double t = 0;
	
	public void testDrive(double driveAmount)
	{	
		
		if(t < driveAmount)
		{
			SmartDashboard.putNumber("t", t);
			baseLeftFront.set(ControlMode.PercentOutput, 0.5);
			baseRightFront.set(ControlMode.PercentOutput, 0.5);
			t++;
		}
	}
	
	public void arcadeDrive(double left, double rightArcade)
	{
		if (Math.abs(left) > KDeadZoneLimit && Math.abs(rightArcade) > KDeadZoneLimit)
		{
			getBaseLeftFront().set(ControlMode.PercentOutput, left + rightArcade);
			getBaseRightFront().set(ControlMode.PercentOutput, left - rightArcade);
		}
		else
		{
			getBaseLeftFront().set(ControlMode.PercentOutput, 0);
			getBaseRightFront().set(ControlMode.PercentOutput, 0);
		}

		if (rightArcade > KDeadZoneLimit || rightArcade < -KDeadZoneLimit)
		{
			getBaseLeftFront().set(ControlMode.PercentOutput, left + rightArcade);
			getBaseRightFront().set(ControlMode.PercentOutput, left + rightArcade);
		}
		else
		{
			getBaseLeftFront().set(ControlMode.PercentOutput, 0);
			getBaseRightFront().set(ControlMode.PercentOutput, 0);
		}
	}
	
	//This method returns whether or not the talon specified has a sticky fault
	public boolean getTalonStickyFaults(TalonSRX talon)
	{
		StickyFaults f = new StickyFaults();
		talon.getStickyFaults(f);
		return f.hasAnyFault();
	}
	
	//This method clears the sticky faults on the base talons when called
	public void clearTalonStickyFaults()
	{
		getBaseLeftFront().clearStickyFaults(5000);
		getBaseRightFront().clearStickyFaults(5000);
		getBaseLeftBack().clearStickyFaults(5000);
		getBaseRightBack().clearStickyFaults(5000);
	}
	
	// This function shifts the speed of the base to the high position
	public void highShiftBase()
	{
		shifterSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	// This function shifts the speed of the base to the low position
	public void lowShiftBase()
	{
		shifterSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	// This function toggles the shift speed of the base
	public void toggleShift()
	{
		if (shifterSolenoid.get() == DoubleSolenoid.Value.kForward)
		{
			highShiftBase();
		}
		else
		{
			lowShiftBase();
		}
		SmartDashboard.putString("Base Solenoid:", shifterSolenoid.get().toString());
	}

	// Used for reseting the gyro
	public void resetGyro()
	{
		// pigeonIMU.setYaw(0, 0);
	}

	// @return current gyro value in degrees from 180.0 to -180.0
	public double getAngle()
	{
		double[] ypr = new double[3];
		// pigeonIMU.getYawPitchRoll(ypr);
		return (ypr[0]);
	}

	// Resets both of the base encoders
	public void resetEncoders()
	{
		baseLeftFront.getSensorCollection().setQuadraturePosition(0, 0);
		baseRightFront.getSensorCollection().setQuadraturePosition(0, 0);
	}

	// Returns value of the left encoder when we need access
	public double getLeftEncoderValue()
	{
		return baseLeftFront.getSelectedSensorPosition(0);
	}

	// Returns value of the right encoder when we need access
	public double getRightEncoderValue()
	{
		return baseRightFront.getSelectedSensorPosition(0);
	}

	// This sets the motion control mode for the left side of the base
	public void setLeftMotionControl(ControlMode mode, double value)
	{
		this.baseLeftFront.set(mode, value);
	}
	
	// This sets the motion control mode for the right side of the base
	public void setRightMotionControl(ControlMode mode, double value)
	{
		this.baseRightFront.set(mode, value);
	}
}
