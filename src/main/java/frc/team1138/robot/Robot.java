package frc.team1138.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.subsystems.Collector;
import frc.team1138.robot.AutoCommand.LeftCommand;
import frc.team1138.robot.AutoCommand.MiddleCommand;
import frc.team1138.robot.AutoCommand.RightCommand;
import frc.team1138.robot.subsystems.Arm;
import frc.team1138.robot.subsystems.DriveBase;
import frc.team1138.robot.subsystems.Lift;
import frc.team1138.robot.subsystems.LEDSubsystem;
import frc.team1138.robot.subsystems.LEDSubsystem.LEDModes;

import java.io.IOException;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot
{
	public static final DriveBase DRIVE_BASE = new DriveBase();
	public static final Arm ARM = new Arm();
	public static final Lift LIFT = new Lift();
	public static final Collector COLLECTOR = new Collector();
	public static LEDSubsystem ledSubsystem = new LEDSubsystem();
	public static OI oi;
	public static PowerDistributionPanel pdp;

	Command autonomousCommand;
	SendableChooser chooser;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		chooser = new SendableChooser();
		oi = new OI();
		pdp =  new PowerDistributionPanel();
		chooser.addDefault("Middle", new MiddleCommand());
		chooser.addObject("Right", new RightCommand());
		chooser.addObject("Left", new LeftCommand());
		SmartDashboard.putData("Auto mode", chooser);

	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit()
	{
		try {
			ledSubsystem.setMode(LEDModes.Off);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	@Override
	public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit()
	{
		autonomousCommand = (Command) chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
		{
			autonomousCommand.start();
		}
		
		try {
			ledSubsystem.setMode(LEDModes.Idle);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit()
	{
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		Robot.DRIVE_BASE.resetEncoders();
		Robot.LIFT.resetLiftEncoder();

		try {
			ledSubsystem.setMode(LEDModes.Idle);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
//		SmartDashboard.putNumber("Gyro Value", Robot.DRIVE_BASE.getAngle());
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Right Base Encoder", Robot.DRIVE_BASE.getRightEncoderValue());
		SmartDashboard.putNumber("Left Base Encoder", Robot.DRIVE_BASE.getLeftEncoderValue());
		SmartDashboard.putNumber("Gyro", Robot.DRIVE_BASE.getAngle());
		// Robot.DRIVE_BASE.cureCancer();
	}
	
	@Override
	public void testInit() {
		try {
			ledSubsystem.setMode(LEDModes.Idle);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic()
	{
		Scheduler.getInstance().run();
		if (pdp.getVoltage() < 6.5) {
			pdp.clearStickyFaults();
			SmartDashboard.putNumber("PDP Sticky", pdp.getVoltage());
		}
	}
}
