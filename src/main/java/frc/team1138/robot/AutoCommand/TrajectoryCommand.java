package frc.team1138.robot.AutoCommand;

import frc.team1138.robot.MotionProfile.Constants;
import frc.team1138.robot.MotionProfile.TrajectoryExecutor;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.Robot;


/**
 * @author Zheyuan Hu
 * @version 1.0.0 This Command requires Robot.SUB_DRIVE_BASE
 */
public class TrajectoryCommand extends Command
{
	private TrajectoryExecutor trajectoryExecutor;
	private Trajectory leftTrajectory, rightTrajectory;
	private double kP = 0.05, kD = 0.025, kI = 0;
	private double kP_HIGH = kP/4.0, kD_HIGH = kD/4.0, kI_HIGH = 0;
	private GearRatio gear;
	public TrajectoryCommand(Waypoint[] points, double maxVel, double maxAccel, double maxJerk, double dt, double width, GearRatio gearRatio)
	{
		requires(Robot.DRIVE_BASE);
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, dt, maxVel, maxAccel, maxJerk);
		Trajectory trajectory = Pathfinder.generate(points, config);
		TankModifier modifier = new TankModifier(trajectory);
		modifier.modify(width);
		leftTrajectory = modifier.getLeftTrajectory();
		rightTrajectory = modifier.getRightTrajectory();
		this.gear = gearRatio;
	}

	public enum GearRatio {
		HIGH_GEAR,
		LOW_GEAR;
	}
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Robot.DRIVE_BASE.resetEncoders();
		trajectoryExecutor = new TrajectoryExecutor(Robot.DRIVE_BASE.getBaseLeftFront(), Robot.DRIVE_BASE.getBaseRightFront(), leftTrajectory, rightTrajectory);

		switch (this.gear)
		{
			case HIGH_GEAR:
				Robot.DRIVE_BASE.getBaseLeftFront().config_kP(0, kP_HIGH, Constants.kTimeoutMs);
				Robot.DRIVE_BASE.getBaseLeftFront().config_kI(0, kI_HIGH, Constants.kTimeoutMs);
				Robot.DRIVE_BASE.getBaseLeftFront().config_kD(0, kD_HIGH, Constants.kTimeoutMs);
				Robot.DRIVE_BASE.getBaseLeftFront().config_kF(0, 0.02798140043763676148796498905908, Constants.kTimeoutMs);
		
				Robot.DRIVE_BASE.getBaseRightFront().config_kP(0, kP_HIGH, Constants.kTimeoutMs);
				Robot.DRIVE_BASE.getBaseRightFront().config_kI(0, kI_HIGH, Constants.kTimeoutMs);
				Robot.DRIVE_BASE.getBaseRightFront().config_kD(0, kD_HIGH, Constants.kTimeoutMs);
				Robot.DRIVE_BASE.getBaseRightFront().config_kF(0, 0.02873595505617977528089887640449, Constants.kTimeoutMs);
				break;
			case LOW_GEAR:
				Robot.DRIVE_BASE.getBaseLeftFront().config_kP(0, kP, Constants.kTimeoutMs);
				Robot.DRIVE_BASE.getBaseLeftFront().config_kI(0, kI, Constants.kTimeoutMs);
				Robot.DRIVE_BASE.getBaseLeftFront().config_kD(0, kD, Constants.kTimeoutMs);
				Robot.DRIVE_BASE.getBaseLeftFront().config_kF(0, 0.1003039514, Constants.kTimeoutMs);
		
				Robot.DRIVE_BASE.getBaseRightFront().config_kP(0, kP, Constants.kTimeoutMs);
				Robot.DRIVE_BASE.getBaseRightFront().config_kI(0, kI, Constants.kTimeoutMs);
				Robot.DRIVE_BASE.getBaseRightFront().config_kD(0, kD, Constants.kTimeoutMs);
				Robot.DRIVE_BASE.getBaseRightFront().config_kF(0, 0.104398408, Constants.kTimeoutMs);
				break;
		}
		trajectoryExecutor.startMotionProfile();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		trajectoryExecutor.control();
		
		SetValueMotionProfile leftOutput = trajectoryExecutor.getLeftValue();
		SetValueMotionProfile rightOutput = trajectoryExecutor.getRightValue();

		// double heading_now = 
		Robot.DRIVE_BASE.setRightMotionControl(ControlMode.MotionProfile, rightOutput.value);
		Robot.DRIVE_BASE.setLeftMotionControl(ControlMode.MotionProfile, leftOutput.value);
		
		SmartDashboard.putNumber("MP Left Motor Output", Robot.DRIVE_BASE.getBaseLeftFront().getMotorOutputPercent());
		SmartDashboard.putNumber("MP Right Motor Output", Robot.DRIVE_BASE.getBaseRightFront().getMotorOutputPercent());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		SmartDashboard.putBoolean("is Ended", trajectoryExecutor.isEnd());
		return trajectoryExecutor.isEnd();
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		System.out.println("End !!!!!!!");
		trajectoryExecutor.reset();
		Robot.DRIVE_BASE.setLeftMotionControl(ControlMode.PercentOutput, 0);
		Robot.DRIVE_BASE.setRightMotionControl(ControlMode.PercentOutput, 0);
		Robot.DRIVE_BASE.resetEncoders();
		Robot.DRIVE_BASE.getBaseLeftFront().config_kP(0, 0, Constants.kTimeoutMs);
		Robot.DRIVE_BASE.getBaseLeftFront().config_kI(0, 0, Constants.kTimeoutMs);
		Robot.DRIVE_BASE.getBaseLeftFront().config_kD(0, 0, Constants.kTimeoutMs);
		Robot.DRIVE_BASE.getBaseLeftFront().config_kF(0, 0, Constants.kTimeoutMs);

		Robot.DRIVE_BASE.getBaseRightFront().config_kP(0, 0, Constants.kTimeoutMs);
		Robot.DRIVE_BASE.getBaseRightFront().config_kI(0, 0, Constants.kTimeoutMs);
		Robot.DRIVE_BASE.getBaseRightFront().config_kD(0, 0, Constants.kTimeoutMs);
		Robot.DRIVE_BASE.getBaseRightFront().config_kF(0, 0, Constants.kTimeoutMs);
		Robot.DRIVE_BASE.setLeftMotionControl(ControlMode.PercentOutput, 0);
		Robot.DRIVE_BASE.setRightMotionControl(ControlMode.PercentOutput, 0);
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