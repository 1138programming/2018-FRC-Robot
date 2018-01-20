package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1138.robot.RobotMap;
import com.ctre.phoenix. motorcontrol.can.TalonSRX;
/**
 *
 */
public class Turret extends Subsystem {

	//Setup the turret configuration
	public static final int KTurretTalon = 7;
	
	private TalonSRX turretMotor;
	
	public Turret() {
		//Setup the motor
		turretMotor = new TalonSRX(KTurretTalon);
		//Configure and enable
//		turretMotor.setSafetyEnabled(true);
//		turretMotor.enableControl();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

