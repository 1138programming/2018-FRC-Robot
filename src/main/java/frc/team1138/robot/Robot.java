package frc.team1138.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.subsystems.Collector;
import frc.team1138.robot.AutoCommand.LeftCommand;
import frc.team1138.robot.AutoCommand.MiddleCommand;
import frc.team1138.robot.AutoCommand.RightCommand;
import frc.team1138.robot.subsystems.Arm;
import frc.team1138.robot.subsystems.Camera;
import frc.team1138.robot.subsystems.DriveBase;
import frc.team1138.robot.subsystems.Lift;
import frc.team1138.robot.subsystems.CoprocessorSubsystem;
import frc.team1138.robot.subsystems.CoprocessorSubsystem.LEDModes;
import frc.team1138.robot.subsystems.Lift.LatchPos;
import edu.wpi.first.wpilibj.hal.PDPJNI;
import java.lang.String;

import java.awt.print.Printable;
import java.io.IOException;

import org.omg.CORBA.PUBLIC_MEMBER;
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
	public static final Camera camera = new Camera();
	public static CoprocessorSubsystem coprocessorSubsystem = new CoprocessorSubsystem();
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
		pdp =  new PowerDistributionPanel(0);
		LiveWindow.disableTelemetry(pdp);
		chooser.addDefault("Middle", new MiddleCommand());
		chooser.addObject("Right", new RightCommand());
		chooser.addObject("Left", new LeftCommand());
		SmartDashboard.putData("Auto mode", chooser);

	}

	public static final int KPDPArmTalon = 0;
	public static final int KPDPFrontLiftTalon = 1;
	public static final int KPDPSpareTalon = 2;
	public static final int KPDPBackLiftTalon = 3;
	public static final int KPDPLeftCollectorTalon = 4;
	public static final int KPDPRightCollectorTalon = 5;
	public static final int KPDPRightBackBaseTalon = 12;
	public static final int KPDPRightFrontBaseTalon = 13;
	public static final int KPDPLeftBackBaseTalon = 14;
	public static final int KPDPLeftFrontBaseTalon = 15; 
	
	
	public void SmartDashboardPutPDPChannel(String label, int channel)
	{
		String presentationString = "PDP Channel " + channel + ": " + label;
		SmartDashboard.putNumber(presentationString, pdp.getCurrent(channel));
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
			coprocessorSubsystem.setMode(LEDModes.Off);
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

		Robot.DRIVE_BASE.resetEncoders();
		Robot.LIFT.resetLiftEncoder();
		Robot.ARM.resetEncoder();
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
		{
			// autonomousCommand.start();
		}
		
		try {
			coprocessorSubsystem.setMode(LEDModes.Idle);
		} catch (IOException e) {
			System.out.println(e);
		}
		

		// Robot.LIFT.moveLatch(LatchPos.AUTON_POS);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
		// Robot.LIFT.testSoul();
	}

	@Override
	public void testInit()
	{
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		try {
			coprocessorSubsystem.setMode(LEDModes.Idle);
		} catch (IOException e) {
			System.out.println(e);
		}
		Robot.ARM.resetEncoder();
		DRIVE_BASE.t = 0;
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
		SmartDashboard.putNumber("Arm Encoder", Robot.ARM.returnEncoderValue());
		// SmartDashboard.putNumber("Gyro", Robot.DRIVE_BASE.getAngle());
		Robot.LIFT.testSoul();
		// Robot.DRIVE_BASE.cureCancer();
	}
	
	@Override
	public void teleopInit() {
		Robot.DRIVE_BASE.resetEncoders();
		Robot.LIFT.resetLiftEncoder();
//		try {
//			coprocessorSubsystem.setMode(LEDModes.Idle);
//		} catch (IOException e) {
//			System.out.println(e);
//		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic()
	{
		if (pdp.getVoltage() < 6.8) {
			pdp.clearStickyFaults();
		}
		SmartDashboard.putNumber("PDP Voltage", pdp.getVoltage());
		SmartDashboard.putNumber("Match Time", Timer.getMatchTime());
		//Display PWM Channel Values
				SmartDashboardPutPDPChannel("Arm Talon", KPDPArmTalon);
				SmartDashboardPutPDPChannel("Spare Talon", KPDPSpareTalon);
				SmartDashboardPutPDPChannel("Front Lift Talon", KPDPFrontLiftTalon);
				SmartDashboardPutPDPChannel("Back Lift Talon", KPDPBackLiftTalon);
				SmartDashboardPutPDPChannel("Left Collector Talon", KPDPLeftCollectorTalon);
				SmartDashboardPutPDPChannel("Right Collector Talon", KPDPRightCollectorTalon);
				SmartDashboardPutPDPChannel("Right Back Base Talon", KPDPRightBackBaseTalon);
				SmartDashboardPutPDPChannel("Right Front Base Talon", KPDPRightFrontBaseTalon);
				SmartDashboardPutPDPChannel("Left Back Base Talon", KPDPLeftBackBaseTalon);
				SmartDashboardPutPDPChannel("Left Front Base Talon", KPDPLeftFrontBaseTalon);
				
		//Display Talon Values
				SmartDashboard.putNumber("Arm Talon Value", ARM.getArmTalon().getMotorOutputPercent());
				SmartDashboard.putNumber("Lift Front Talon Value", LIFT.getFrontLiftTalon().getMotorOutputPercent());
				SmartDashboard.putNumber("Lift Back Talon Value", LIFT.getBackLiftTalon().getMotorOutputPercent());
				SmartDashboard.putNumber("Left Collector Talon Value", COLLECTOR.getLeftCollectorTalon().getMotorOutputPercent());
				SmartDashboard.putNumber("Right Collector Talon Value", COLLECTOR.getRightCollectorTalon().getMotorOutputPercent());
				SmartDashboard.putNumber("Base Left Front Talon Value", DRIVE_BASE.getBaseLeftFront().getMotorOutputPercent());
				SmartDashboard.putNumber("Base Left Back Talon Value", DRIVE_BASE.getBaseLeftBack().getMotorOutputPercent());
				SmartDashboard.putNumber("Base Right Front Talon Value", DRIVE_BASE.getBaseRightFront().getMotorOutputPercent());
				SmartDashboard.putNumber("Base Right Back Talon Value", DRIVE_BASE.getBaseRightBack().getMotorOutputPercent());
				
				DRIVE_BASE.testDrive(2);
	}
	
	@Override
	public void robotPeriodic()
	{
		SmartDashboard.putNumber("Ultrasonic Value", Robot.coprocessorSubsystem.ultrasonicValue());
	}
}
