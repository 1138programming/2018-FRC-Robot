package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.commands.DriveWithJoysticks;

import com.ctre.phoenix.sensors.PigeonIMU;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 *
 */
public class DriveBase extends Subsystem
{
	// Declaring the talons and sensors
	private TalonSRX baseLeftFront, baseLeftBack, baseRightFront, baseRightBack;

	private PigeonIMU pigeonIMU;
	private DoubleSolenoid shifterSolenoid;

	// Making variables for base talon slots so there aren't magic numbers floating
	// around
	public static final int KBaseLeftFrontTalon = 1;
	public static final int KBaseLeftBackTalon = 2;
	public static final int KBaseLeftTopTalon = 3;
	public static final int KBaseRightFrontTalon = 4;
	public static final int KBaseRightBackTalon = 5;
	public static final int KBaseRightTopTalon = 6;
	public static final double KDeadZoneLimit = 0.2;
	// All of the solenoids are doubles, so they need 2 numbers each. If you change
	// one,
	// be sure to change the other one of the pair also.
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
//		baseLeftTop = new TalonSRX(KBaseLeftTopTalon);
		baseRightFront = new TalonSRX(KBaseRightFrontTalon);
		baseRightBack = new TalonSRX(KBaseRightBackTalon);
//		baseRightTop = new TalonSRX(KBaseRightTopTalon);

//		baseLeftBack.setSensorPhase(true);
//		baseLeftTop.setSensorPhase(true);
//		baseLeftFront.setSensorPhase(true);
		// Configuring the masters
		baseLeftFront.setInverted(true);
		baseLeftBack.setInverted(true);
//		baseLeftTop.setInverted(true);

		// Configuring the slaves
		baseLeftBack.set(ControlMode.Follower, baseLeftFront.getDeviceID());
//		baseLeftTop.set(ControlMode.Follower, baseLeftFront.getDeviceID());
		baseRightBack.set(ControlMode.Follower, baseRightFront.getDeviceID());
//		baseRightTop.set(ControlMode.Follower, baseRightFront.getDeviceID());

		// Configuring the sensors
		shifterSolenoid = new DoubleSolenoid(KShifterSolenoid1, KShifterSolenoid2);
		pigeonIMU = new PigeonIMU(baseLeftBack); // TODO find out which talon it's actually on
		pigeonIMU.setYaw(0, 0);

		baseLeftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		baseRightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		

		
		//We don't want ramping rn
//		baseRightFront.configOpenloopRamp(1, 0);
//		baseRightBack.configOpenloopRamp(1, 0);
		//baseRightTop.configOpenloopRamp(1, 0);
//		getBaseLeftFront().configOpenloopRamp(1, 0);
//		baseLeftBack.configOpenloopRamp(1, 0);
		//baseLeftTop.configOpenloopRamp(1, 0);
		
	}
	
	//Note: If there is a problem, use Control + /. It will solve all the errors.
	//Also: If it's not working, it's all Leo's fault - Connor Nicholls, 2018
	
	public void clearTalonStickyFaults()
	{
		getBaseLeftFront().clearStickyFaults(5000);
		baseRightFront.clearStickyFaults(5000);
		baseLeftBack.clearStickyFaults(5000);
		baseRightBack.clearStickyFaults(5000);
//		baseLeftTop.clearStickyFaults(5000);
//		baseRightTop.clearStickyFaults(5000);
	}
	
	public boolean getTalonStickyFaults(TalonSRX talon)
	{
		StickyFaults f = new StickyFaults();
		talon.getStickyFaults(f);
		return f.hasAnyFault();
	}

	public TalonSRX getBaseLeftFront()
	{
		return baseLeftFront;
	}
	
	public TalonSRX getBaseRightFront()
	{
		return baseLeftFront;
	}

	public TalonSRX getBaseLeftBack()
	{
		return baseLeftBack;
	}
	
	public TalonSRX getBaseRightBack()
	{
		return baseRightBack;
	}
	
//	public TalonSRX getBaseLeftTop()
//	{
//		return baseLeftTop;
//	}
//	
//	public TalonSRX getBaseRightTop()
//	{
//		return baseRightTop;
//	}
	
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		setDefaultCommand(new DriveWithJoysticks());
	}

	// Used for reseting the gyro in-match
	public void resetGyro()
	{
		pigeonIMU.setYaw(0, 0);
	}

	// @return current gyro value in degrees from 180.0 to -180.0
	public double getAngle()
	{
		double[] ypr = new double[3];
		pigeonIMU.getYawPitchRoll(ypr);
		return (ypr[0]);
	}

	// Resets both encoders
	public void resetEncoders()
	{
		baseLeftFront.getSensorCollection().setQuadraturePosition(0, 0);
		baseRightFront.getSensorCollection().setQuadraturePosition(0, 0);
	}

	// Returns value of the left encoder
	public double getLeftEncoderValue()
	{
		return baseLeftFront.getSelectedSensorPosition(0);
	}

	// Returns value of the right encoder
	public double getRightEncoderValue()
	{
		return baseRightFront.getSelectedSensorPosition(0);
	}

// 	public void cureCancer() // We have found the cure to cancer!
// 	{
// 		baseRightFront.clearStickyFaults(10);
// 		baseLeftFront.clearStickyFaults(10);
// 	}

	// Used to drive the base in a "tank drive" format, this is the standard
	public void tankDrive(double left, double right)
	{
		if (left > KDeadZoneLimit || left < -KDeadZoneLimit)
		{
			getBaseLeftFront().set(ControlMode.PercentOutput, left);
		}
		else
		{
			getBaseLeftFront().set(ControlMode.PercentOutput, 0);
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

	// This function shifts the speed of the base to the reverse position
	public void highShiftBase()
	{
		shifterSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	// This function shifts the speed of the base to the forward position
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

	public void setLeftMotionControl(ControlMode mode, double value) {
		this.baseLeftFront.set(mode, value);
	}
	
	public void setRightMotionControl(ControlMode mode, double value) {
		this.baseRightFront.set(mode, value);
	}
	
	public TalonSRX getRightMotor() {
		return this.baseRightFront; 
	}
	
	public TalonSRX getLeftMotor() {
		return this.baseLeftFront; 
	}
}
