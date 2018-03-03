package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team1138.robot.commands.MoveArmWithJoysticks;
import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 *This is the arm subsystem for the robot called Keystone.
 *Currently, all PID functionality is commented out until
 *we get to mess with the encoder. TODO check all of the
 *goods with the arm encoder.
 */

public class Arm extends /*PID*/Subsystem
{
	// Declaring the talons and digital input (touch sensor)
	private TalonSRX armMotor;
	private DigitalInput armLowerLimit;

	// Making variable for the arm talon
	public static final int KArmMotor = 7;
	
	// Making variables for the limit (and a deadzone/reverse deadzone for driving)
	public static final int KArmLowerLimit = 1;
	public static final double KDeadZoneLimit = 0.1; // Deadzone
	public static final double KBigLimit = 0.8; // Reverse deadzone (if value's too large)
	
	//Setting speeds and encoders
	public static final double KSlowSpeed = 0.4; //Slow speed of the arm
	public static final int KZeroSpeed = 0; // Speed for not moving
	public static final int KTicksPerRotation = 4096; // The ticks per rotation on the arm encoder
	
//	public static final int KEncoderValue = 1000;
//	public static final int KLowValue = 5;

	public Arm()
	{
//		super("Arm PID", 0, 0, 0); // TODO mess with P, I, and D
//		setAbsoluteTolerance(100); // Threshold/error allowed
//		getPIDController().setContinuous(true); // Change based on need, probably should be continuous
//		getPIDController().setInputRange(-1000000000, 1000000000); // TODO figure out after getting the bot
//		getPIDController().setOutputRange(-1.0, 1.0); // Max and min speed for the arm

		// Setting up the arm motor talon
		armMotor = new TalonSRX(KArmMotor);

		// Setting up the limits as digital inputs
		armLowerLimit = new DigitalInput(KArmLowerLimit);

		// Setting up encoder
		armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

		// Setting up PID controller
//		getPIDController().enable();
		
		// Configuring encoder to clear when arm hits the limit switch
		// TODO test if this is correct way to clear it
		armMotor.configSetParameter(ParamEnum.eClearPositionOnLimitF, 1, 0, 0, 10);
	}
	
	
	/*
	 * The order for methods as of 3 March 2018 (edit if needed):
	 * Default Command
	 * Basic Arm Functionality (without and with PID, in that order)
	 * Moving arm to lower limit switch
	 * PID Functionality and Methods
	*/
	
	// Sets the default command
	public void initDefaultCommand()
	{
//		setDefaultCommand(new MoveArmWithJoysticksPID());
		setDefaultCommand(new MoveArmWithJoysticks());
	}

	// Sets arm to armAxis output, currently used to manually drive the robot
	public void moveArm(double armAxis)
	{
		if (armAxis > -KBigLimit || armAxis < KBigLimit)
		{
			armMotor.set(ControlMode.PercentOutput, armAxis*KSlowSpeed);
		}
		else if (armAxis < -KBigLimit)
		{
			armMotor.set(ControlMode.PercentOutput, -KBigLimit*KSlowSpeed);
		}
		else if (armAxis > KBigLimit) {
			armMotor.set(ControlMode.PercentOutput, KBigLimit*KSlowSpeed);
		}
		SmartDashboard.putNumber("Arm Motor", this.armMotor.getMotorOutputPercent()); 
	}

	// Moves arm to set point with dead zone limit, will be for PID functionality
	public void driveArm(double armAxis)
	{
		if (armAxis > KDeadZoneLimit || armAxis < -KDeadZoneLimit)
		{
//			getPIDController().setSetpoint(getPosition() + armAxis * 1000); // TODO experiment with this last constant
																			// value
		}
		else
		{
//			getPIDController().setSetpoint(getPosition());
		}
	}

	// Moves arm to the limit switch with PID
	public void moveArmToLimitSwitch(double encoderValue)
	{
		if (armLowerLimit.get() != true)
		{
//			setGoal(encoderValue);
		}
		else
		{
//			getPIDController().setSetpoint(0);
		}

		// Currently this is here as a backup once we test the actual functionality
		// TODO get rid of this if we know it is not needed (once bot is tested)
//		 if (armMotor.getSensorCollection().getQuadraturePosition() < encoderValue)
//		 {
//		 if (armMotor.getSensorCollection().getQuadraturePosition() <
//		 KLowValue*KTicksPerRotation)
//		 {
//		 armMotor.set(ControlMode.PercentOutput, KSlowSpeed);
//		 }
//		 else
//		 {
//		 armMotor.set(ControlMode.PercentOutput, armSpeed);
//		 }
//		 }
//		 else {
//		 armMotor.set(ControlMode.PercentOutput, KZeroSpeed);
//		
//		 }
	}
	
	// Returning encodoer value for pid
//	@Override
//	protected double returnPIDInput()
//	{
//		return returnEncoderValue();
//	}

	// Move up or down if not on target
//	@Override
//	protected void usePIDOutput(double output)
//	{
//		if (!getPIDController().onTarget())
//		{
//			if ((this.returnPIDInput() - this.getSetpoint()) < 0)
//			{ // Need to move up
//				System.out.println("Move Up");
//				moveArm(output);
//			}
//			else if ((this.returnPIDInput() - this.getSetpoint()) > 0)
//			{ // Need to move down
//				System.out.println("Move Down");
//				moveArm(-output);
//			}
//			System.out.println("Error: " + (returnEncoderValue() - this.getSetpoint()));
//			System.out.println("Input: " + this.returnPIDInput());
//		}
//		else
//		{
//			moveArm(0);
//		}
//	}

	// Move arm to setpoint with encoders by setting the setpoint to another value
	public void setGoal(double position)
	{
//		getPIDController().setSetpoint(position * KTicksPerRotation);
	}

	// Returns the encoder value
	public double returnEncoderValue()
	{
		return armMotor.getSensorCollection().getQuadraturePosition();
	}

	// Returns whether or not the robot is on target
//	@Override
//	public boolean onTarget()
//	{
//		// TODO Auto-generated method stub
//		return super.onTarget();
//	}
}
