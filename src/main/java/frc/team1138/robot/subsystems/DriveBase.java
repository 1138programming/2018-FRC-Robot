package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1138.robot.RobotMap;
import frc.team1138.robot.commands.DriveWithJoysticks;

import com.ctre.phoenix.sensors.PigeonIMU;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix. motorcontrol.can.TalonSRX;

/**
 *
 */
public class DriveBase extends Subsystem
{
	//Declaring the talons and sensors
	private TalonSRX baseLeftFront, baseLeftBack, baseLeftTop, baseRightFront, baseRightBack, baseRightTop;
	private PigeonIMU PigeonIMU;
	
	//Making variables for base talon slots so there aren't magic numbers floating around
	public static final int KBaseLeftFrontTalon = 1;
	public static final int KBaseLeftBackTalon = 2;
	public static final int KBaseLeftTopTalon = 3;
	public static final int KBaseRightFrontTalon = 4;
	public static final int KBaseRightBackTalon = 5;
	public static final int KBaseRightTopTalon = 6;
	public static final double KDeadZoneLimit = 0.2;
	
	public DriveBase()
	{
		//Setting up base talons
		baseLeftFront = new TalonSRX(KBaseLeftFrontTalon);
		baseLeftBack = new TalonSRX(KBaseLeftBackTalon);
		baseLeftTop = new TalonSRX(KBaseLeftTopTalon);
		baseRightFront = new TalonSRX(KBaseRightFrontTalon);
		baseRightBack = new TalonSRX(KBaseRightBackTalon);
		baseRightTop = new TalonSRX(KBaseRightTopTalon);
		
		//Configuring the masters
		baseLeftFront.setInverted(true);
		
		//Configuring the slaves
		baseLeftBack.set(ControlMode.Follower, baseLeftFront.getDeviceID());
		baseLeftTop.set(ControlMode.Follower, baseLeftFront.getDeviceID());
		baseRightBack.set(ControlMode.Follower, baseRightFront.getDeviceID());
		baseRightTop.set(ControlMode.Follower, baseRightFront.getDeviceID());
		
		//Configuring the sensors
		PigeonIMU = new PigeonIMU(baseLeftFront); //TODO find out which talon it's actually on
		PigeonIMU.setYaw(0,0);
		baseLeftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);
		baseRightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);
	}

	public void initDefaultCommand()
	{
		//Set the default command for a subsystem here.
		setDefaultCommand(new DriveWithJoysticks());
	}
	
	public void tankDrive(double left, double right)
	{
		if(left > KDeadZoneLimit || left < -KDeadZoneLimit)
		{
			baseLeftFront.set(ControlMode.PercentOutput, left);
		}
		else
		{
			baseLeftFront.set(ControlMode.PercentOutput, 0);
		}
		
		if(right > KDeadZoneLimit || right < -KDeadZoneLimit)
		{
			baseRightFront.set(ControlMode.PercentOutput, right);
		}
		else
		{
			baseRightFront.set(ControlMode.PercentOutput, 0);
		}
	}
}
