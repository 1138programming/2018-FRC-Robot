package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team1138.robot.commands.MoveArmWithJoysticks;
import frc.team1138.robot.commands.MoveArmWithJoysticksPID;
import com.ctre.phoenix.sensors.PigeonIMU;

import java.sql.ResultSet;
import java.util.function.ToDoubleBiFunction;
import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 *
 */
public class Arm extends /*PID*/Subsystem
{
	// Declaring the talons, digital limits, and encoder
	private TalonSRX armMotor;
	private DigitalInput armLowerLimit, armUpperLimit;

	// Making variables for the arm talon
	public static final int KArmMotor = 10;
	// making variables for the limits
	public static final int KArmLowerLimit = 1;
	public static final int KArmUpperLimit = 2;
	// setting dead zone limit
	public static final double KDeadZoneLimit = 0.05;
	public static final double KBigLimit = 0.8;
	public static final double KSlowSpeed = 0.5;
	public static final double KLowSpeed = 0.4; // TODO set variable;
	public static final int KLowValue = 5; // TODO set variable;
	public static final int KZeroSpeed = 0; // TODO set variable;
	public static final int KEncoderValue = 1000;
	public static final int KTicksPerRotation = 4096;

	public Arm()
	{
//		super("Arm PID", 0, 0, 0); // TODO mess with P, I, and D
//		setAbsoluteTolerance(100); // Threshold/error allowed TODO change this value to correct value
//		getPIDController().setContinuous(true); // Change based on need, probably should be continuous
//		getPIDController().setInputRange(-1000000000, 1000000000); // TODO figure out after getting the bot
//		getPIDController().setOutputRange(-1.0, 1.0);

		// Setting up the arm motor talon
		armMotor = new TalonSRX(KArmMotor);

		// setting up the limit digital input
		armLowerLimit = new DigitalInput(KArmLowerLimit);
		armUpperLimit = new DigitalInput(KArmUpperLimit);

		// setting up encoders
		armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

		// Setting up PID controller
//		getPIDController().enable();
		
		//Configuring encoder
		armMotor.configSetParameter(ParamEnum.eClearPositionOnLimitF, 1, 0, 0, 10); // TODO I don't know if this is
																					// quite the right function to clear
																					// the encoder when it touches the
																					// limit, so TODO figure out the
																					// true value
	}

	// Sets the default command
	public void initDefaultCommand()
	{
//		setDefaultCommand(new MoveArmWithJoysticksPID());
		setDefaultCommand(new MoveArmWithJoysticks());
	}

	// TODO set arm to output in pid output
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

	// move to set point with dead zone limit
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

	// move arm to the limit switch with pid
	public void moveArmToLimitSwitch(double encoderValue)
	{
		if (armLowerLimit.get() != true)
		{
//			getPIDController().setSetpoint(encoderValue * KTicksPerRotation);
		}
		else
		{
//			getPIDController().setSetpoint(0);
		}

		// if (armMotor.getSensorCollection().getQuadraturePosition() < encoderValue)
		// {
		// if (armMotor.getSensorCollection().getQuadraturePosition() <
		// KLowValue*KTicksPerRotation)
		// {
		// armMotor.set(ControlMode.PercentOutput, KLowSpeed);
		// }
		// else
		// {
		// armMotor.set(ControlMode.PercentOutput, armSpeed);
		// }
		// }
		// else {
		// armMotor.set(ControlMode.PercentOutput, KZeroSpeed);
		//
		// }
	}

	// move to setpoint with encoders
	public void moveArmWithEncoders(double position)
	{
//		getPIDController().setSetpoint(position * KTicksPerRotation);
	}

	// get encoder value
	public double returnEncoderValue()
	{
		return armMotor.getSensorCollection().getQuadraturePosition();
	}

	// set setpoint
	public void setGoal(double setpoint)
	{
//		getPIDController().setSetpoint(setpoint);
	}

	// getting encodoer value for pid
//	@Override
//	protected double returnPIDInput()
//	{
//		return returnEncoderValue();
//	}

	// move up or down if not on target
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

	// calls for if it is true or false
//	@Override
//	public boolean onTarget()
//	{
//		// TODO Auto-generated method stub
//		return super.onTarget();
//	}
}
