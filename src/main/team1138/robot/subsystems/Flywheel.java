package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1138.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 *
 */
public class Flywheel extends Subsystem {

	//Setup the flywheel configuration
	public static final int KFlywheelIndexTalon = 9;
	public static final int KFlywheelAngleAdjusterTalon = 10;
	public static final int KFlywheelBottomTalon = 11;
	public static final int KFlywheelTopTalon = 12;
	
	//For now, these speeds are unified for all the motors in the subsystem until they need to be separate speeds.
	public static final double fullSpeed = 0.8;
	public static final double mediumSpeed = 0.5;
	public static final double lowSpeed = 0.3;
	public static final int stop = 0;
	
    private TalonSRX topFlywheelMotor,
    				 bottomFlywheelMotor,
    				 angleAdjusterFlywheelMotor,
    				 indexerFlywheelMotor;
    public Flywheel()
    {
    	//Setup the motors
    	topFlywheelMotor = new TalonSRX(KFlywheelTopTalon);
    	bottomFlywheelMotor = new TalonSRX(KFlywheelBottomTalon);
    	angleAdjusterFlywheelMotor = new TalonSRX(KFlywheelAngleAdjusterTalon);
    	indexerFlywheelMotor = new TalonSRX(KFlywheelIndexTalon);
    	
    	//Configure masters and enable
    	//topFlywheelMotor.setSafetyEnabled(true);
		//bottomFlywheelMotor.setSafetyEnabled(true);
		//angleAdjusterFlywheelMotor.setSafetyEnabled(true);
		//indexerFlywheelMotor.setSafetyEnabled(true);
    	//topFlywheelMotor.enableControl();
    	//angleAdjusterFlywheelMotor.enableControl();
    	//indexerFlywheelMotor.enableControl();
    	
    	//Configure the bottom motor as a slave
    	bottomFlywheelMotor.set(
    			ControlMode.Follower,
    			topFlywheelMotor.getDeviceID());
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void runShooter()
    {
    	topFlywheelMotor.set(ControlMode.PercentOutput, fullSpeed);
    	bottomFlywheelMotor.set(ControlMode.PercentOutput, fullSpeed);
    }
    
    public void runShooter(double speed)
    {
    	topFlywheelMotor.set(ControlMode.PercentOutput, speed);
    	bottomFlywheelMotor.set(ControlMode.PercentOutput, speed);
    }
    
    public void stopShooter()
    {
    	topFlywheelMotor.set(ControlMode.PercentOutput, stop);
    	bottomFlywheelMotor.set(ControlMode.PercentOutput, stop);
    }
    
    public void runAngleAdjuster()
    {
    	angleAdjusterFlywheelMotor.set(ControlMode.PercentOutput, fullSpeed);
    }
    
    public void runAngleAdjuster(double speed)
    {
    	angleAdjusterFlywheelMotor.set(ControlMode.PercentOutput, speed);
    }
    
    public void stopAngleAdjuster()
    {
    	angleAdjusterFlywheelMotor.set(ControlMode.PercentOutput, stop);
    }
    
    public void runIndexer()
    {
    	indexerFlywheelMotor.set(ControlMode.PercentOutput, fullSpeed);
    }
    
    public void runIndexer(double speed)
    {
    	indexerFlywheelMotor.set(ControlMode.PercentOutput, speed);
    }
    
    public void stopIndexer()
    {
    	indexerFlywheelMotor.set(ControlMode.PercentOutput, stop);
    }
}

