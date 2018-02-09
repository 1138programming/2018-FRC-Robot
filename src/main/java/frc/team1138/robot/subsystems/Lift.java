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
	private PIDController liftController;

	// Making variables for lift slots (talons and sensors) so there aren't magic
	// numbers floating around (there's also other variables to be used later in the code
	public static final int KFrontLiftTalon = 8;
	public static final int KBackLiftTalon = 9;
	public static final int KHangLimit = 3;
	public static final int KLowerLimit = 4; //I don't think we actually have this anymore
	public static final int KHallEffect = 5; //Or this
	private static final double KDeadZoneLimit = 0.1;
	private static final double KTicksPerRotation = 4096;
	
	
	public Lift()
	{
		super("Lift PID", 0, 0, 0); // Sets up as PID loop TODO mess with these values
		setAbsoluteTolerance(50); // Threshold/error TODO mess with this number
		getPIDController().setContinuous(true); // Change based on need, probably should be continuous
		getPIDController().setInputRange(-100000, 100000); // TODO figure out range after getting the bot
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
//		hangLimit2 = new DigitalInput(KLowerLimit); // Limit switch to potentially add in
		frontLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); // Encoder
		backLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); // Encoder
		liftController.enable();
	}

	//The default command when nothing else is running
	public void initDefaultCommand()
	{
		setDefaultCommand(new DriveLift());
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
	
	//Moves the lift using the encoders
	public void liftWithEncoders(double rotations)
	{
		getPIDController().setSetpoint(rotations*KTicksPerRotation);
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
	
	//Returns the value of the encoder
	public double getEncoderValue()
	{
		return frontLift.getSensorCollection().getQuadraturePosition();
	}
	
	//Uses the input to utilize PID
	@Override
	protected void usePIDOutput(double output)
	{
		if (!liftController.onTarget())
		{
			if ((this.returnPIDInput() - this.getSetpoint()) < 0)
			{ // Need to move up
				System.out.println("Move Up");
				moveLift(output);
			}
			else if ((this.returnPIDInput() - this.getSetpoint()) > 0)
			{ // Need to move down
				System.out.println("Move Down");
				moveLift(-output);
			}
			System.out.println("Error: " + (getEncoderValue() - this.getSetpoint()));
			System.out.println("Input: " + this.returnPIDInput());
		}
		else
		{
			moveLift(0);
		}
	}
	
	//Moves the lift
	public void moveLift(double liftAxis)
	{
		frontLift.set(ControlMode.PercentOutput, liftAxis);
	}
	
	//Returns the input for the PID loop
	@Override
	protected double returnPIDInput()
	{
		return getEncoderValue();
	}
	
	//Sets the lift controller to a setpoint
	public void setLift(double target)
	{
		liftController.setSetpoint(target);
	}
	
	//Checks if the PID is on target
	@Override
	public boolean onTarget()
	{
		return super.onTarget();
	}
}