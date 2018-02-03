package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1138.robot.RobotMap;
import frc.team1138.robot.commands.DriveLift;
import frc.team1138.robot.commands.DriveWithJoysticks;

import com.ctre.phoenix.sensors.PigeonIMU;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 *
 */
public class Lift extends PIDSubsystem
{
	// Declaring the talons and sensors for the lift branch
	private TalonSRX frontLift, backLift;
	private DoubleSolenoid speedShiftSolenoid;
	private DigitalInput hangLimit1, hangLimit2;
	private PIDController controller;

	// Making variables for lift slots (talons and sensors) so there aren't magic
	// numbers floating around
	public static final int KFrontLiftTalon = 8;
	public static final int KBackLiftTalon = 9;
	public static final int KHangLimit = 3;
	public static final int KLowerLimit = 4;
	public static final int KHallEffect = 5;
	private static final double KDeadZoneLimit = 0.1;
	private static final double KTicksPerRotation = 4096;
	
	
	public Lift()
	{
		super(0, 0, 0); // Sets up as PID loop
		// Setting up base talons
		frontLift = new TalonSRX(KFrontLiftTalon);
		backLift = new TalonSRX(KBackLiftTalon);

		// Configuring the talons
		frontLift.setInverted(true);
		backLift.set(ControlMode.Follower, frontLift.getDeviceID());

		// Configuring the solenoid
		speedShiftSolenoid = new DoubleSolenoid(4, 5);

		// Configuring the sensors
		hangLimit1 = new DigitalInput(KHangLimit); // Limit switch
		hangLimit2 = new DigitalInput(KLowerLimit); // Limit switch
		frontLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); // Encoder
		backLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); // Encoder
//		controller.enable();
	}

	public void initDefaultCommand()
	{
		setDefaultCommand(new DriveLift());
	}

	@Override
	protected double returnPIDInput()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void usePIDOutput(double output)
	{
		// TODO Auto-generated method stub

	}

	public void liftWithJoysticks(double liftAxis)
	{
		if (liftAxis > KDeadZoneLimit || liftAxis < -KDeadZoneLimit)
		{
			frontLift.set(ControlMode.PercentOutput, liftAxis);
		}
		else
		{
			frontLift.set(ControlMode.PercentOutput, 0);
		}
	}

	private void highShiftLift()
	{
		speedShiftSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	/**
	 * Shift the base to low position
	 */
	private void lowShiftLift()
	{
		speedShiftSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	/**
	 * public method to switch shifts base
	 */
	public void toggleLiftSpeed()
	{
		if (speedShiftSolenoid.get() == DoubleSolenoid.Value.kForward)
		{
			highShiftLift();
		}
		else
		{
			lowShiftLift();
		}
	}
	
	public void liftWithEncoders(double rotations, double liftSpeed)
	{
		if (frontLift.getSensorCollection().getQuadraturePosition() != rotations*KTicksPerRotation)
		{
			if (frontLift.getSensorCollection().getQuadraturePosition() < rotations*KTicksPerRotation)
			{
				frontLift.set(ControlMode.PercentOutput, liftSpeed);
			}
			else
			{
				frontLift.set(ControlMode.PercentOutput, -liftSpeed);
			}
		}
		frontLift.set(ControlMode.PercentOutput, 0);
	}
}