package frc.team1138.robot.commands;

import frc.team1138.robot.MotionProfile.CircleLeft;
import frc.team1138.robot.MotionProfile.CircleRight;
import frc.team1138.robot.MotionProfile.Constants;
import frc.team1138.robot.MotionProfile.ProfileExecutor;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.Robot;


/**
 * @author Zheyuan Hu
 * @version 1.0.0 This Command requires Robot.SUB_DRIVE_BASE
 */
public class TestMotionProfile extends Command
{
	private ProfileExecutor leftMP, rightMP;
    private double kP, kD, kI;
	public TestMotionProfile()
	{
		// TODO Auto-generated constructor stub
        requires(Robot.DRIVE_BASE);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
        Robot.DRIVE_BASE.resetEncoders();
		leftMP = new ProfileExecutor(Robot.DRIVE_BASE.getLeftMotor(), CircleLeft.Points);
		rightMP = new ProfileExecutor(Robot.DRIVE_BASE.getRightMotor(), CircleRight.Points);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
        kP = SmartDashboard.getNumber("Left kP", 0);
        kI = SmartDashboard.getNumber("Left kI", 0);
        kD = SmartDashboard.getNumber("Left kD", 0);

        Robot.DRIVE_BASE.getLeftMotor().config_kP(0, kP, Constants.kTimeoutMs);
        Robot.DRIVE_BASE.getLeftMotor().config_kI(0, kI, Constants.kTimeoutMs);
        Robot.DRIVE_BASE.getLeftMotor().config_kD(0, kD, Constants.kTimeoutMs);
        Robot.DRIVE_BASE.getLeftMotor().config_kF(0, 0.45, Constants.kTimeoutMs);

		Robot.DRIVE_BASE.getRightMotor().config_kP(0, kP, Constants.kTimeoutMs);
        Robot.DRIVE_BASE.getRightMotor().config_kI(0, kI, Constants.kTimeoutMs);
        Robot.DRIVE_BASE.getRightMotor().config_kD(0, kD, Constants.kTimeoutMs);
        Robot.DRIVE_BASE.getRightMotor().config_kF(0, 0.4, Constants.kTimeoutMs);

		leftMP.control();
		rightMP.control();
		SetValueMotionProfile leftOutput = leftMP.getValue();
		SetValueMotionProfile rightOutput = rightMP.getValue();
		Robot.DRIVE_BASE.setLeftMotionControl(ControlMode.MotionProfile, leftOutput.value);
		Robot.DRIVE_BASE.setRightMotionControl(ControlMode.MotionProfile, rightOutput.value);
		leftMP.startMotionProfile();
		rightMP.startMotionProfile();
		SmartDashboard.putNumber("MP Left Motor Output", Robot.DRIVE_BASE.getLeftMotor().getMotorOutputPercent());
		SmartDashboard.putNumber("MP Right Motor Output", Robot.DRIVE_BASE.getRightMotor().getMotorOutputPercent());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return leftMP.getValue() == SetValueMotionProfile.Hold ||
		 rightMP.getValue() == SetValueMotionProfile.Hold;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
        Robot.DRIVE_BASE.setLeftMotionControl(ControlMode.PercentOutput, 0);
		leftMP.reset();
		Robot.DRIVE_BASE.setRightMotionControl(ControlMode.PercentOutput, 0);
		rightMP.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
        super.interrupted();
        end();
	}
}