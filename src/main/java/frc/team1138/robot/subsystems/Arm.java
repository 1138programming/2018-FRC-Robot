package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;



import frc.team1138.robot.RobotMap;
import frc.team1138.robot.commands.DriveWithJoysticks;
import frc.team1138.robot.commands.MoveArmWithJoysticks;

import com.ctre.phoenix.sensors.PigeonIMU;

import java.sql.ResultSet;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 *
 */
public class Arm extends Subsystem
{
	//Declaring the talons, digital limits, and encoder
	private TalonSRX armMotor;
	private DigitalInput armLowerLimit, armUpperLimit;

	//Making variables for the arm talon 
	public static final int KArmMotor = 7;
	//making variables for the limits
	public static final int KArmLowerLimit = 1;
	public static final int KArmUpperLimit = 2;
	//setting dead zone limit
	public static final double KDeadZoneLimit = 0.2;
	public static final int KLowSpeed = //TODO set variable;
	public static final int KLowValue = //TODO set variable;
	public static final int KZeroSpeed = //TODO set variable;
	public static final int KEncoderValue = 1000;
	public static final int KTargetEncoderValue =//TODO set variable;
	


	
	public Arm()
	{
		//Setting up the arm motor talon
		armMotor = new TalonSRX(KArmMotor);
		
		//setting up the limit digital input
		armLowerLimit = new DigitalInput(KArmLowerLimit); 
		armUpperLimit = new DigitalInput(KArmUpperLimit);
		
		//setting up encoders
		armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);
	

	
	}
	
	public void initDefaultCommand()
	{
		setDefaultCommand(new MoveArmWithJoysticks());
	}
	
	public void MoveArm (double armAxis)
	{
		if(armAxis > KDeadZoneLimit || armAxis < -KDeadZoneLimit)
		{
			armMotor.set(ControlMode.PercentOutput, armAxis);
		}
		else
		{
			armMotor.set(ControlMode.PercentOutput, 0);
			
		}
		
	}
	
	
	public void moveArmToLimitSwitch(double encoderValue, float armSpeed)
	{
		if (armMotor.getSensorCollection().getQuadraturePosition() < KTargetEncoderValue)
		{
			if (armMotor.getSensorCollection().getQuadraturePosition() < KLowValue) {
				armMotor.set(ControlMode.PercentOutput, KLowSpeed);
			}
			else {
				armMotor.set(ControlMode.PercentOutput, armSpeed);
			}
		}
		else {
			armMotor.set(ControlMode.PercentOutput, KZeroSpeed); 

		}
		}
	
}
