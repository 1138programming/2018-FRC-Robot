package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.OI;
import frc.team1138.robot.Robot;

/**
 *
 */
public class DriveWithJoysticks extends Command {
	private OI oi;
    public DriveWithJoysticks() {
    	// TODO Auto-generated constructor stub
    	requires(Robot.DRIVE_BASE);
    	oi = new OI(); 
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.DRIVE_BASE.tankDrive(oi.getLeftController(), oi.getRightController());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
