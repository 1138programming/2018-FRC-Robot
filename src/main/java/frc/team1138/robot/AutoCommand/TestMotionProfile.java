package frc.team1138.robot.AutoCommand;

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
	private double[][] leftProfile, rightProfile; 
	private double kP = 0.1, kD = 0.5, kI = 0;
	public TestMotionProfile(double[][] leftProfile, double[][] rightProfile)
	{
		requires(Robot.DRIVE_BASE);
		this.leftProfile = leftProfile;
		this.rightProfile = rightProfile;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Robot.DRIVE_BASE.resetEncoders();
		leftMP = new ProfileExecutor(Robot.DRIVE_BASE.getBaseLeftFront(), this.leftProfile);
		rightMP = new ProfileExecutor(Robot.DRIVE_BASE.getBaseRightFront(), this.rightProfile);

		Robot.DRIVE_BASE.getBaseLeftFront().config_kP(0, kP, Constants.kTimeoutMs);
        Robot.DRIVE_BASE.getBaseLeftFront().config_kI(0, kI, Constants.kTimeoutMs);
		Robot.DRIVE_BASE.getBaseLeftFront().config_kD(0, kD, Constants.kTimeoutMs);
        Robot.DRIVE_BASE.getBaseLeftFront().config_kF(0, 0.029593844, Constants.kTimeoutMs);

		Robot.DRIVE_BASE.getBaseRightFront().config_kP(0, kP, Constants.kTimeoutMs);
        Robot.DRIVE_BASE.getBaseRightFront().config_kI(0, kI, Constants.kTimeoutMs);
        Robot.DRIVE_BASE.getBaseRightFront().config_kD(0, kD, Constants.kTimeoutMs);
        Robot.DRIVE_BASE.getBaseRightFront().config_kF(0, 0.0305994257, Constants.kTimeoutMs);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		leftMP.control();
		rightMP.control();
		leftMP.startMotionProfile();
		rightMP.startMotionProfile();
		SetValueMotionProfile leftOutput = leftMP.getValue();
		SetValueMotionProfile rightOutput = rightMP.getValue();
		Robot.DRIVE_BASE.setLeftMotionControl(ControlMode.MotionProfile, leftOutput.value);
		Robot.DRIVE_BASE.setRightMotionControl(ControlMode.MotionProfile, rightOutput.value);
		SmartDashboard.putNumber("MP Left Motor Output", Robot.DRIVE_BASE.getBaseLeftFront().getMotorOutputPercent());
		SmartDashboard.putNumber("MP Right Motor Output", Robot.DRIVE_BASE.getBaseRightFront().getMotorOutputPercent());
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