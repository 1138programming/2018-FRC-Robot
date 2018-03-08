package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.commands.DriveLift;
import frc.team1138.robot.commands.DriveLiftPID;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 *This is the lift subsystem for the robot called Keystone.
 */

public class Lift extends PIDSubsystem
{
	// Declaring the talons, solenoids, and sensors for the lift branch
	private WPI_TalonSRX frontLift, backLift;
	// private Victor rightLatch, leftLatch; //TODO ask!
	private DoubleSolenoid speedShiftSolenoid, ratchetSolenoid, lockingSolenoid; //TODO verify

	// Making variables for lift things (talons and sensors)
	public static final int KFrontLiftTalon = 8;
	public static final int KBackLiftTalon = 9;
	private final static double KDeadZoneLimit = 0.1;
	//TODO reverify Solenoid stuff
	// private static final int KShiftSol1 = 4;
	// private static final int KShiftSol2 = 5;
	// private static final int KRatchet1 = 6;
	// private static final int KRatchet2 = 7;
	// private static final int KLockingSolenoid1 = 0;
	// private static final int KLockingSolenoid2 = 9;
	// private static final boolean KForward = true;
	// private static final boolean KReverse = false;
	
	public Lift()
	{
		super("Lift PID", 0.2, 0, 0.6); // Sets up as PID loop TODO mess with these values
		setAbsoluteTolerance(50); // error allowed
		getPIDController().setContinuous(true); // Change based on need, probably should be continuous
		getPIDController().setInputRange(-30000, 30000); // TODO figure out range after getting the bot
		getPIDController().setOutputRange(-1.0, 1.0);

		// Setting up lift and latch talons
		frontLift = new WPI_TalonSRX(KFrontLiftTalon);
		backLift = new WPI_TalonSRX(KBackLiftTalon);
		// rightLatch = new Victor(KRightLatchVictor);
		// leftLatch = new Victor(KLeftLatchVictor);
		
		// Configuring the talons
		// backLift.setInverted(true);
		frontLift.setInverted(true);
		backLift.set(ControlMode.Follower, frontLift.getDeviceID());

		// rightLatch.setInverted(true);
		
		// TODO Configuring the solenoids
		// speedShiftSolenoid = new DoubleSolenoid(KShiftSol1, KShiftSol2);
		// ratchetSolenoid = new DoubleSolenoid(KRatchet1, KRatchet2);
		// lockingSolenoid = new DoubleSolenoid(KLockingSolenoid1, KLockingSolenoid2);
		backLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); // Encoder
		frontLift.setName("Lift", "FrontLift");
		getPIDController().enable();
	}
	
	
	/*
	 * The order for methods as of 3 March 2018 (edit if needed):
	 * Default Command
	 * Basic Lift Functionality (without and with PID, in that order)
	*/

	// The default command when nothing else is running
	public void initDefaultCommand()
	{
		setDefaultCommand(new DriveLiftPID());
		// setDefaultCommand(new DriveLift());
	}

	// Uses the input to utilize PID
	@Override
	protected void usePIDOutput(double output)
	{
		if (!getPIDController().onTarget())
		{
			SmartDashboard.putNumber("output", output);
			if ((this.returnPIDInput() - this.getSetpoint()) < 0)
			{ // Need to move up
				System.out.println("Move Up");
				moveLift(output);
			}
			else if ((this.returnPIDInput() - this.getSetpoint()) > 0)
			{ // Need to move down
				System.out.println("Move Down");
				moveLift(output);
			}
			System.out.println("Error: " + (getEncoderValue() - this.getSetpoint()));
			System.out.println("Input: " + this.returnPIDInput());
		}
		else
		{
			moveLift(0);
		}
	}

	// Returns the input for the PID loop
	@Override
	protected double returnPIDInput()
	{
		return getEncoderValue();
	}

	// Sets the lift controller to a setpoint
//	public void setLift(double target)
//	{
//		getPIDController().setSetpoint(target);
//	}

	// Checks if the PID is on target
	@Override
	public boolean onTarget()
	{
		return super.onTarget();
	}

	// Returns the value of the encoder
	public double getEncoderValue()
	{
		return backLift.getSensorCollection().getQuadraturePosition();
	}

	public void resetLiftEncoder() 
	{
		backLift.getSensorCollection().setQuadraturePosition(0, 10);
	}

	// Moves the lift without PID
	public void moveLift(double liftAxis)
	{
		SmartDashboard.putBoolean("Lift Limit Switch Rev", frontLift.getSensorCollection().isRevLimitSwitchClosed());
		SmartDashboard.putBoolean("Lift Limit Switch Fwd", frontLift.getSensorCollection().isFwdLimitSwitchClosed());
		if(liftAxis > KDeadZoneLimit || (liftAxis < -KDeadZoneLimit && !frontLift.getSensorCollection().isRevLimitSwitchClosed()))
		{
			// frontLift.set(ControlMode.PercentOutput, liftAxis * 0.3);
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
		SmartDashboard.putNumber("error", getPIDController().getError());
		SmartDashboard.putNumber("setPoint", getPIDController().getSetpoint());
		SmartDashboard.putNumber("result", getPIDController().get());
		if (liftAxis > KDeadZoneLimit || (liftAxis < -KDeadZoneLimit && !frontLift.getSensorCollection().isRevLimitSwitchClosed()))
		{
			getPIDController().setSetpoint(getPosition() + Math.pow(liftAxis * 5, 3));
		}
		else
		{
			getPIDController().setSetpoint(getPosition());
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
