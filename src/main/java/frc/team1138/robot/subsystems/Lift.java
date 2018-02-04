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
	// numbers floating around (there's also other variables to be used later in the code
	public static final int KFrontLiftTalon = 8;
	public static final int KBackLiftTalon = 9;
	public static final int KHangLimit = 3;
	public static final int KLowerLimit = 4;
	public static final int KHallEffect = 5;
	private static final double KDeadZoneLimit = 0.1;
	private static final double KTicksPerRotation = 4096;
	
	
	public Lift()
	{
		super("Lift PID", 0, 0, 0); // Sets up as PID loop
		setAbsoluteTolerance(0.05); // Threshold
		getPIDController().setContinuous(true); // Change based on need, probably should be continuous
//		getPIDController().setInputRange(minimumInput, maximumInput); // TODO figure out after getting the bot
		getPIDController().setOutputRange(-1.0, 1.0);
		
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
		controller.enable();
	}

	//The default command when nothing else is running
	public void initDefaultCommand()
	{
		setDefaultCommand(new DriveLift());
	}

	//Returns the input for the PID loop
	@Override
	protected double returnPIDInput()
	{
		return getEncoderValue();
	}
	
	//Returns the value of the encoder
	public double getEncoderValue()
	{
		return frontLift.getSensorCollection().getQuadraturePosition();
	}

	//Uses the input to utilize PID
	@Override
	protected void usePIDOutput(double output)
	{
		// TODO use the calculated PID output
		frontLift.set(ControlMode.PercentOutput, output);
	}

	//Lifts (or lowers) the robot using the joysticks
	public void liftWithJoysticks(double liftAxis)
	{
		if (liftAxis > KDeadZoneLimit || liftAxis < -KDeadZoneLimit)
		{
			getPIDController().setSetpoint(getPosition() + liftAxis * 1000);
		}
		else
		{
			getPIDController().setSetpoint(getPosition());
		}
	}

	//Shifts the lift to the high speed position
	private void highShiftLift()
	{
		speedShiftSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	//Shifts the lift the low speed position
	private void lowShiftLift()
	{
		speedShiftSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	//Toggles the lift speed
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
	
	//Moves the lift using the encoders
	public void liftWithEncoders(double rotations)
	{
		getPIDController().setSetpoint(rotations*KTicksPerRotation);
	}
}