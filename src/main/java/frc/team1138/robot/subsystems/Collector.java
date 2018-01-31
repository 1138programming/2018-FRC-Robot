package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1138.robot.commands.DriveWithJoysticks;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.SafePWM;

/**
 *
 */
public class Collector extends Subsystem
{
	// Declaring the talons, limit switch and solenoid
	private TalonSRX rightCollector, leftCollector;
	private Solenoid plunger;
	private DigitalInput cubPossLimit;

	public static final int KLeftCollectorMotor = 10;
	public static final int KRightCollectorMotor = 11;
	public static final int KCubPossLimit = 0;
	public static final int KPlunger = 3;
	public static final double KCollectorSpeed = .7;

	public Collector()
	{
		// setting the limit switch
		cubPossLimit = new DigitalInput(KCubPossLimit);

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
	public void ejectCubeWithRollers()
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
}
