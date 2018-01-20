package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1138.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix. motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 *
 */
public class Hopper extends Subsystem {

	//Setup the hopper configuration
	public static final int KHopperTalon = 8;
	
	public static final double hopperFullSpeed = 0.8;
	public static final double hopperMediumSpeed = 0.5;
	public static final double hopperLowSpeed = 0.3;
	public static final double hopperStop = 0;
	
	private TalonSRX hopperMotor;
	
	public Hopper() {
		//Setup the motor
		hopperMotor = new TalonSRX(KHopperTalon);
		//Configure and enable
//		hopperMotor.setSafetyEnabled(true);
//		hopperMotor.enableControl();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void runHopper()
    {
    	hopperMotor.set(ControlMode.PercentOutput, hopperFullSpeed);
    }
    
    public void runHopper(double speed)
    {
    	hopperMotor.set(ControlMode.PercentOutput, speed);
    }
    
    public void stopHopper()
    {
    	hopperMotor.set(ControlMode.PercentOutput, hopperStop);
    	
    }
    
}

