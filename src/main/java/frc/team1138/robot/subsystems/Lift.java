package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1138.robot.commands.DriveLift;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IFollower;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 *This is the lift subsystem for the robot called Keystone.
 *Currently, all PID functionality is commented out until
 *we get to mess with the encoder. TODO check all of the
 *goods with the lift encoder.
 */

public class Lift extends /*PID*/Subsystem
{
	// Declaring the talons, solenoids, and sensors for the lift branch
	private TalonSRX frontLift, backLift;
	private Victor rightLatch, leftLatch;
	private DoubleSolenoid speedShiftSolenoid, ratchetSolenoid, lockingSolenoid;
	private DigitalInput hangLimit1, hangLimit2;
	private I2C leftIME, rightIME;

	// Making variables for lift things (talons and sensors)
	public static final int KFrontLiftTalon = 8;
	public static final int KBackLiftTalon = 9;
	public static final int KRightLatchVictor = 0;
	public static final int KLeftLatchVictor = 1;
//	public static final int KLeftIME = 6;
//	public static final int KRightIME = 7; 
	public static final int KHangLimit = 3;
	public static final int KLowerLimit = 4; // I don't think we actually have this anymore
	public static final int KHallEffect = 5; // Or this
	
	//Solenoid stuff
	private static final int KShiftSol1 = 4;
	private static final int KShiftSol2 = 5;
	private static final int KRatchet1 = 6;
	private static final int KRatchet2 = 7;
	private static final int KLockingSolenoid1 = 0;
	private static final int KLockingSolenoid2 = 9;
	private static final boolean KForward = true;
	private static final boolean KReverse = false;
	
	//Other variables for things like driving and I2C
	private static final double KDeadZoneLimit = 0.1;
	private static final double KTicksPerRotation = 4096;
	private static final double KMotorRevDividedByOutputRotation = 39.2;
	private static final double KOneOverMotorRevOutputRotation = .02551020408;
	private static final int I2CENCODER_DEFAULT_ADDRESS = 0X30;
	private static final int I2CENCODER_ADDRESS_REGISTER = 0X40;
	private static final int I2CENDCODER_POSITION_REGISTER = 0X40;
	private static final int I2CENCODER_STARTING_ADDRESS = 0X10;
	private static final int TICKS = 8;
	public static final int KLeftIME = I2CENCODER_STARTING_ADDRESS;
	public static final int KRightIME = I2CENCODER_STARTING_ADDRESS + 1; 

	public Lift()
	{
//		super("Lift PID", 0, 0, 0); // Sets up as PID loop TODO mess with these values
//		setAbsoluteTolerance(50); // Threshold/error allowed
//		getPIDController().setContinuous(true); // Change based on need, probably should be continuous
//		getPIDController().setInputRange(-100000, 100000); // TODO figure out range after getting the bot
//		getPIDController().setOutputRange(-1.0, 1.0);

		// Setting up lift and latch talons
		frontLift = new TalonSRX(KFrontLiftTalon);
		backLift = new TalonSRX(KBackLiftTalon);
		rightLatch = new Victor(KRightLatchVictor);
		leftLatch = new Victor(KLeftLatchVictor);
		
		// Configuring the talons
		backLift.setInverted(true);
		backLift.set(ControlMode.Follower, frontLift.getDeviceID());
		rightLatch.setInverted(true);
		
		// Configuring the solenoids
		speedShiftSolenoid = new DoubleSolenoid(KShiftSol1, KShiftSol2);
		ratchetSolenoid = new DoubleSolenoid(KRatchet1, KRatchet2);
		lockingSolenoid = new DoubleSolenoid(KLockingSolenoid1, KLockingSolenoid2);

		// Configuring the sensors
		hangLimit1 = new DigitalInput(KHangLimit); // Limit switch
		// hangLimit2 = new DigitalInput(KLowerLimit); // Limit switch to potentially
		// add in
		frontLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); // Encoder
		backLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); // Encoder

//		getPIDController().enable();
		
		//I2C Configuration
		// Robot
		//  |
		// Left
		//  |
		// Right
		
		I2C defaultIME = new I2C(I2C.Port.kOnboard, I2CENCODER_DEFAULT_ADDRESS);
		byte[] buffer = new byte[2];
		buffer[0] = I2CENCODER_ADDRESS_REGISTER;
		buffer[1] = I2CENCODER_STARTING_ADDRESS << 1;
		defaultIME.writeBulk(buffer, 2);
		buffer[1] = (I2CENCODER_STARTING_ADDRESS + 1) << 1;
		defaultIME.writeBulk(buffer, 2);

		leftIME = new I2C(I2C.Port.kOnboard, KLeftIME);
		rightIME = new I2C(I2C.Port.kOnboard, KRightIME);
	}
	
	
	/*
	 * The order for methods as of 3 March 2018 (edit if needed):
	 * Default Command
	 * Basic Lift Functionality (without and with PID, in that order)
	*/

	// The default command when nothing else is running
	public void initDefaultCommand()
	{
//		setDefaultCommand(new DriveLiftPID());
		setDefaultCommand(new DriveLift());
	}
	
	// Moves the lift without PID
	public void moveLift(double liftAxis)
	{
		if(liftAxis > KDeadZoneLimit || liftAxis < -KDeadZoneLimit)
		{
			frontLift.set(ControlMode.PercentOutput, liftAxis);
		}
		else
		{
			frontLift.set(ControlMode.PercentOutput, 0);
		}
	}

	// Lifts (or lowers) the robot using the joysticks and PID
	public void liftWithJoysticks(double liftAxis)
	{
		if (liftAxis > KDeadZoneLimit || liftAxis < -KDeadZoneLimit)
		{
//			getPIDController().setSetpoint(getPosition() + liftAxis * 1000);
		}
		else
		{
//			getPIDController().setSetpoint(getPosition());
		}
	}

	// Moves the lift using the encoders
	public void liftWithEncoders(double rotations)
	{
//		getPIDController().setSetpoint(rotations * KTicksPerRotation);
	}

	// Toggles the lift speed
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

	// Shifts the lift to the high speed position
	private void highShiftLift()
	{
		speedShiftSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	// Shifts the lift the low speed position
	private void lowShiftLift()
	{
		speedShiftSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	// Returns the value of the encoder
	public double getEncoderValue()
	{
		return frontLift.getSensorCollection().getQuadraturePosition();
	}

	// Uses the input to utilize PID
//	@Override
//	protected void usePIDOutput(double output)
//	{
//		if (!getPIDController().onTarget())
//		{
//			if ((this.returnPIDInput() - this.getSetpoint()) < 0)
//			{ // Need to move up
//				System.out.println("Move Up");
//				moveLift(output);
//			}
//			else if ((this.returnPIDInput() - this.getSetpoint()) > 0)
//			{ // Need to move down
//				System.out.println("Move Down");
//				moveLift(-output);
//			}
//			System.out.println("Error: " + (getEncoderValue() - this.getSetpoint()));
//			System.out.println("Input: " + this.returnPIDInput());
//		}
//		else
//		{
//			moveLift(0);
//		}
//	}

	// Returns the input for the PID loop
//	@Override
//	protected double returnPIDInput()
//	{
//		return getEncoderValue();
//	}

	// Sets the lift controller to a setpoint
//	public void setLift(double target)
//	{
//		getPIDController().setSetpoint(target);
//	}

	// Checks if the PID is on target
//	@Override
//	public boolean onTarget()
//	{
//		return super.onTarget();
//	}
	
//	public long getLeftIME()
//	{
//		byte[] buffer = new byte[1];
//		buffer[0] = I2CENDCODER_POSITION_REGISTER;
//		leftIME.writeBulk(buffer, 1);
//		long pos = 0;
//		leftIME.readOnly(buffer, 1);
//		pos |= buffer[0] << 8;
//		leftIME.readOnly(buffer, 1);
//		pos |= buffer[0] << 0;
//		leftIME.readOnly(buffer, 1);
//		pos |= buffer[0] << 24;
//		leftIME.readOnly(buffer, 1);
//		pos |= buffer[0] << 16;
//		return pos;
//	}
//	
//	public long getRightIME()
//	{
//		byte[] buffer = new byte[1];
//		buffer[0] = I2CENDCODER_POSITION_REGISTER;
//		rightIME.writeBulk(buffer, 1);
//		long pos = 0;
//		rightIME.readOnly(buffer, 1);
//		pos |= buffer[0] << 8;
//		rightIME.readOnly(buffer, 1);
//		pos |= buffer[0] << 0;
//		rightIME.readOnly(buffer, 1);
//		pos |= buffer[0] << 24;
//		rightIME.readOnly(buffer, 1);
//		pos |= buffer[0] << 16;
//		return pos;
//	}
//	
//	//TODO WE MUST FINISH THIS FUNCTIONALITY
//	public void moveLatch(double targetValue)
//	{
//		long rightIME = getRightIME();
//		long leftIME = getLeftIME();
//		if(leftIME < targetValue && rightIME < targetValue)
//		{
//			
//		}
//
	
	// Shifts the lift to the high speed position
	private void engageRatchet()
	{
		ratchetSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	// Shifts the lift the low speed position
	private void disengageRatchet()
	{
		ratchetSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void ratchetIt()
	{
		if (ratchetSolenoid.get() == DoubleSolenoid.Value.kForward)
		{
			engageRatchet();
		}
		else
		{
			disengageRatchet();
		}
	}
	
	public void toggleLocker()
	{
		if(lockingSolenoid.get() == DoubleSolenoid.Value.kForward)
		{
			lockingSolenoid.set(DoubleSolenoid.Value.kOff);
		}
		else
		{
			lockingSolenoid.set(DoubleSolenoid.Value.kForward);
		}
	}
}
