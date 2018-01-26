package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1138.robot.RobotMap;
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
	//Declaring the talons and sensors
	private TalonSRX frontLift, backLift;
	private DoubleSolenoid speedShift;
	private DigitalInput hangLimit, lowerLimit;
	private DigitalInput hallEffect;
	
	//Making variables for lift slots (talons and sensors) so there aren't magic numbers floating around
	public static final int KFrontLiftTalon = 8;
	public static final int KBackLiftTalon = 9;
	public static final int KHangLimit = 3;
	public static final int KLowerLimit = 4;
	public static final int KHallEffect = 5;

	
	public Lift()
	{
		super(0, 0, 0); //Sets up as PID loop
		//Setting up base talons
		frontLift = new TalonSRX(KFrontLiftTalon);
		backLift = new TalonSRX(KBackLiftTalon);
		
		//Configuring the talons
		frontLift.setInverted(true);
		backLift.set(ControlMode.Follower, frontLift.getDeviceID());
		
		//Configuring the solenoid
		speedShift = new DoubleSolenoid(4, 5);
		
		//Configuring the sensors
		hangLimit = new DigitalInput(KHangLimit); //Limit switch
		lowerLimit = new DigitalInput(KLowerLimit); //Limit switch
		hallEffect = new DigitalInput(KHallEffect); //Hall effect sensor, TODO make sure it's a digital input and not a counter
		frontLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0); //Encoder
		backLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0); //Encoder
	}

	public void initDefaultCommand()
	{
		//Set the default command for a subsystem here.
		
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		
	}
}