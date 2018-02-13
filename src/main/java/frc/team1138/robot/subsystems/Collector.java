
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1138.robot.commands.DriveWithJoysticks;

import java.util.concurrent.Delayed;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;


/**
 *
 */
public class Collector extends Subsystem
{
	// Declaring the talons, limit switch and solenoid
	private TalonSRX rightCollector, leftCollector;
	private Solenoid plunger;
	private DigitalInput cubePossessionLimit;

	public static final int KLeftCollectorMotor = 10;
	public static final int KRightCollectorMotor = 11;
	public static final int KCubPossLimit = 0;
	public static final int KPlunger = 3;
	public static final double KCollectorSpeed = .7;
	public static final boolean KForward = true;
	public static final boolean KBackward = false;

	public Collector()
	{
		// setting the limit switch
		cubePossessionLimit = new DigitalInput(KCubPossLimit);

		// Setting up base talons
		rightCollector = new TalonSRX(KRightCollectorMotor);
		leftCollector = new TalonSRX(KLeftCollectorMotor);

		// Configuring the masters
		rightCollector.setInverted(true);

		// Configuring the slaves
		leftCollector.set(ControlMode.Follower, rightCollector.getDeviceID());

		// Solenoid
		plunger = new Solenoid(KPlunger);
	}

	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new DriveWithJoysticks());
	}

	//This command causes the rollers to go at the KCollectorSpeed if the motors aren't 
	//moving, and if they are, then it doesn't move (to prevent them from doing both at
	//the same time

	public void collectCubeWithRollers()
	{
		if(rightCollector.getMotorOutputPercent() == 0)
		{
			rightCollector.set(ControlMode.PercentOutput, KCollectorSpeed);
		}
		else
		{
			rightCollector.set(ControlMode.PercentOutput, 0);
		}
	}

	//This command causes the rollers to go at the -KCollectorSpeed if the motors aren't 
	//moving, and if they are, then it doesn't move (to prevent them from doing both at
	//the same time
	public void ejectCubeWithRollers()
	{
		if(rightCollector.getMotorOutputPercent() == 0)
		{
			rightCollector.set(ControlMode.PercentOutput, -KCollectorSpeed);
		}
		else
		{
			rightCollector.set(ControlMode.PercentOutput, 0);
		}
	}
	
	//The two methods cause the plunger to move forward or backward
	public void plungerForward() {
		plunger.set(KForward);
	}
	
	public void plungerBackward() {
		plunger.set(KBackward);
	}
	
	
	//The method causes the plunger to kick
	public void kickCubeWithPlunger(){
		plungerForward();
		Timer.delay(0.5);
		plungerBackward();
		Timer.delay(0.5);
	}

	

}