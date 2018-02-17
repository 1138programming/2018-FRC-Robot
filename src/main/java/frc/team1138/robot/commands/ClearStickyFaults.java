package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.SolenoidBase;
import edu.wpi.first.wpilibj.hal.SolenoidJNI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.hal.PDPJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU_StickyFaults;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team1138.robot.Robot;
import frc.team1138.robot.OITest;
import frc.team1138.robot.subsystems.DriveBase;

/**
 * @author Zheyuan Hu
 * @version 1.0.0
 */
public class ClearStickyFaults extends Command
{
	public ClearStickyFaults()
	{
		// Use requires() here to declare subsystem dependencies
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		SolenoidBase.clearAllPCMStickyFaults(0);
		PDPJNI.clearPDPStickyFaults(0);
		Robot.DRIVE_BASE.clearTalonStickyFaults();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		SmartDashboard.putString("For the Solenoid, ", "false means that there are no sticky faults");
		SmartDashboard.putBoolean("Solenoid Sticky Faults?", SolenoidBase.getPCMSolenoidVoltageStickyFault(0));
		SmartDashboard.putString("For all of the Talons, ", "true means that there are no sticky faults");
		SmartDashboard.putBoolean("BaseLeftFront Talon Sticky Faults?", Robot.DRIVE_BASE.getTalonStickyFaults(Robot.DRIVE_BASE.getBaseLeftFront()));
		SmartDashboard.putBoolean("BaseLeftBack Talon Sticky Faults?", Robot.DRIVE_BASE.getTalonStickyFaults(Robot.DRIVE_BASE.getBaseLeftBack()));
		SmartDashboard.putBoolean("BaseLeftTop Talon Sticky Faults?", Robot.DRIVE_BASE.getTalonStickyFaults(Robot.DRIVE_BASE.getBaseLeftTop()));
		SmartDashboard.putBoolean("BaseRightFront Talon Sticky Faults?", Robot.DRIVE_BASE.getTalonStickyFaults(Robot.DRIVE_BASE.getBaseRightFront()));
		SmartDashboard.putBoolean("BaseRightBack Talon Sticky Faults?", Robot.DRIVE_BASE.getTalonStickyFaults(Robot.DRIVE_BASE.getBaseRightBack()));
		SmartDashboard.putBoolean("BaseRightTop Talon Sticky Faults?", Robot.DRIVE_BASE.getTalonStickyFaults(Robot.DRIVE_BASE.getBaseRightTop()));
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		
	}
}
