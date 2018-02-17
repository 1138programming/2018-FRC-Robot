package frc.team1138.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.subsystems.Collector;
import frc.team1138.robot.commands.LeftCommand;
import frc.team1138.robot.commands.MiddleCommand;
import frc.team1138.robot.commands.RightCommand;
import frc.team1138.robot.subsystems.Arm;
import frc.team1138.robot.subsystems.DriveBase;
import frc.team1138.robot.subsystems.Lift;

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
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		oi = new OI();
		 chooser.addDefault("Middle", new MiddleCommand());
		 chooser.addObject("Right", new RightCommand());
		 chooser.addObject("Left", new LeftCommand());
		//SmartDashboard.putData("Auto mode", chooser);
		SmartDashboard.putData(chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit()
	{

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
		autonomousCommand = chooser.getSelected();

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
<<<<<<< HEAD
		// Robot.DRIVE_BASE.cureCancer();
=======
//		Robot.DRIVE_BASE.cureCancer();

>>>>>>> master
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic()
	{
	}
}
