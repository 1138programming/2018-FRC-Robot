package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import frc.team1138.robot.RobotMap;
import frc.team1138.robot.commands.DriveWithJoysticks;
import frc.team1138.robot.commands.MoveArmWithJoysticks;

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
public class Arm extends PIDSubsystem
{
	//Declaring the talons, digital limits, and encoder
	private TalonSRX armMotor;
	private DigitalInput armLowerLimit, armUpperLimit;
	private PIDController armController;

	//Making variables for the arm talon 
	public static final int KArmMotor = 7;
	//making variables for the limits
	public static final int KArmLowerLimit = 1;
	public static final int KArmUpperLimit = 2;
	//setting dead zone limit
	public static final double KDeadZoneLimit = 0.2;
	public static final double KLowSpeed = 0.4; //TODO set variable;
	public static final int KLowValue = 5; //TODO set variable;
	public static final int KZeroSpeed = 0; //TODO set variable;
	public static final int KEncoderValue = 1000;
	public static final int KTicksPerRotation = 4096;
	
	public Arm()
	{
		super("Arm PID", 0, 0, 0); // TODO mess with P, I, and D
		setAbsoluteTolerance(100); // Threshold/error allowed TODO change this value to correct value
		getPIDController().setContinuous(true); // Change based on need, probably should be continuous
//		getPIDController().setInputRange(minimumInput, maximumInput); // TODO figure out after getting the bot
		getPIDController().setOutputRange(-1.0, 1.0);
		
		//Setting up the arm motor talon
		armMotor = new TalonSRX(KArmMotor);
		
		//setting up the limit digital input
		armLowerLimit = new DigitalInput(KArmLowerLimit);
		armUpperLimit = new DigitalInput(KArmUpperLimit);
		
		//setting up encoders
		armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);
		
		//Setting up PID controller
		armController.enable();
		armMotor.configSetParameter(ParamEnum.eClearPositionOnLimitF, 1, 0, 0, 10); //TODO I don't know if this is quite the right function to clear the encoder when it touches the limit, so TODO figure out the true value
	}
	
	//Sets the default command
	public void initDefaultCommand()
	{
		setDefaultCommand(new MoveArmWithJoysticks());
	}
	
	public void MoveArm(double armAxis)
	{
		if(armAxis > KDeadZoneLimit || armAxis < -KDeadZoneLimit)
		{
			getPIDController().setSetpoint(getPosition() + armAxis*1000); //TODO experiment with this last constant value
		}
		else
		{
			getPIDController().setSetpoint(getPosition());
		}
	}
	
	public void moveArmToLimitSwitch(double encoderValue, float armSpeed)
	{
		if(armLowerLimit.get() == true)
		{
			resetEncoders(); //TODO SOLVE THIS!!!!!
		}
		
//		if (armMotor.getSensorCollection().getQuadraturePosition() < encoderValue)
//		{
//			if (armMotor.getSensorCollection().getQuadraturePosition() < KLowValue*KTicksPerRotation)
//			{
//				armMotor.set(ControlMode.PercentOutput, KLowSpeed);
//			}
//			else
//			{
//				armMotor.set(ControlMode.PercentOutput, armSpeed);
//			}
//		}
//		else {
//			armMotor.set(ControlMode.PercentOutput, KZeroSpeed);
//
//		}
	}
	
	public void moveArmWithEncoders(double position)
	{
		getPIDController().setSetpoint(position*KTicksPerRotation);
	}

	@Override
	protected double returnPIDInput()
	{
		return returnEncoderValue();
	}

	@Override
	protected void usePIDOutput(double output)
	{
		armMotor.set(ControlMode.PercentOutput, output);
	}
	
	public double returnEncoderValue()
	{
		return armMotor.getSensorCollection().getQuadraturePosition();
	}
	
	@Override
	public boolean onTarget() {
		// TODO Auto-generated method stub
		return super.onTarget();
	}
}