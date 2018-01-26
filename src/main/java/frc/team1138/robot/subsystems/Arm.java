package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;



import frc.team1138.robot.RobotMap;
import frc.team1138.robot.commands.DriveWithJoysticks;

import com.ctre.phoenix.sensors.PigeonIMU;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix. motorcontrol.can.TalonSRX;

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
		
	}
}

