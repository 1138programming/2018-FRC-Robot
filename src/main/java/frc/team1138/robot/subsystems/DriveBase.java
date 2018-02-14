package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1138.robot.RobotMap;
import frc.team1138.robot.commands.DriveWithJoysticks;

import com.ctre.phoenix.sensors.PigeonIMU;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 *
 */
public class DriveBase extends Subsystem
{
	// Declaring the talons and sensors
	private TalonSRX baseLeftFront, baseLeftBack, baseLeftTop, baseRightFront, baseRightBack, baseRightTop;

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
	public static final int KShifterSolenoid1 = 0;
	public static final int KShifterSolenoid2 = 1;

	// Variable for base ultrasonic
	// TODO figure out what these numbers will be based on where they're gonna be
	// plugged in
	public static final int KBaseUltrasonic = 1;

	public DriveBase()
	{
		// Setting up base talons
		baseLeftFront = new TalonSRX(KBaseLeftFrontTalon);
		baseLeftBack = new TalonSRX(KBaseLeftBackTalon);
		baseLeftTop = new TalonSRX(KBaseLeftTopTalon);
		baseRightFront = new TalonSRX(KBaseRightFrontTalon);
		baseRightBack = new TalonSRX(KBaseRightBackTalon);
		baseRightTop = new TalonSRX(KBaseRightTopTalon);

		// Configuring the masters
		baseLeftFront.setInverted(true);

		// Configuring the slaves
		baseLeftBack.set(ControlMode.Follower, baseLeftFront.getDeviceID());
		baseLeftTop.set(ControlMode.Follower, baseLeftFront.getDeviceID());
		baseRightBack.set(ControlMode.Follower, baseRightFront.getDeviceID());
		baseRightTop.set(ControlMode.Follower, baseRightFront.getDeviceID());

		// Configuring the sensors
		pigeonIMU = new PigeonIMU(baseLeftFront); // TODO find out which talon it's actually on
		pigeonIMU.setYaw(0, 0);
		baseLeftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		baseRightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	}

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
		return (-ypr[0]);
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
		return baseLeftFront.getSensorCollection().getQuadraturePosition(); // May need to be reversed
	}

	// Returns value of the right encoder
	public double getRightEncoderValue()
	{
		return baseRightFront.getSensorCollection().getQuadraturePosition();
	}

	// Used to drive the base in a "tank drive" format, this is the standard
	public void tankDrive(double left, double right)
	{
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
	}
}
