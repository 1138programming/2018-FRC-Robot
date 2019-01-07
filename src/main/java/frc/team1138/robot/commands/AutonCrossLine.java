//package frc.team1138.robot.commands;
//
//import frc.team1138.robot.MotionProfile.Constants;
//import frc.team1138.robot.MotionProfile.LeftProfiles;
//import frc.team1138.robot.MotionProfile.ProfileExecutor;
//import frc.team1138.robot.MotionProfile.RightProfiles;
//
//import com.ctre.phoenix.motion.SetValueMotionProfile;
//import com.ctre.phoenix.motorcontrol.ControlMode;
//import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.team1138.robot.Robot;
//
//
//public class AutonCrossLine extends Command
//{
//    private ProfileExecutor leftMP, rightMP;
//    private double kP, kD, kI;
//	public AutonCrossLine()
//	{
//		requires(Robot.DRIVE_BASE);
//	}
//
//	protected void initialize()
//	{
//        kP = 0;
//        kI = 0;
//        kD = 0;
//
//        Robot.DRIVE_BASE.getLeftMotor().config_kP(0, kP, Constants.kTimeoutMs);
//        Robot.DRIVE_BASE.getLeftMotor().config_kI(0, kI, Constants.kTimeoutMs);
//        Robot.DRIVE_BASE.getLeftMotor().config_kD(0, kD, Constants.kTimeoutMs);
//        Robot.DRIVE_BASE.getLeftMotor().config_kF(0, 0.45, Constants.kTimeoutMs);
//
//		Robot.DRIVE_BASE.getRightMotor().config_kP(0, kP, Constants.kTimeoutMs);
//        Robot.DRIVE_BASE.getRightMotor().config_kI(0, kI, Constants.kTimeoutMs);
//        Robot.DRIVE_BASE.getRightMotor().config_kD(0, kD, Constants.kTimeoutMs);
//        Robot.DRIVE_BASE.getRightMotor().config_kF(0, 0.4, Constants.kTimeoutMs);
//        Robot.DRIVE_BASE.resetEncoders();
//
//		leftMP = new ProfileExecutor(Robot.DRIVE_BASE.getLeftMotor(), LeftProfiles.Cross_Line);
//		rightMP = new ProfileExecutor(Robot.DRIVE_BASE.getRightMotor(), RightProfiles.Cross_Line);
//	}
//
//	protected void execute()
//	{
//		leftMP.control();
//		rightMP.control();
//		leftMP.startMotionProfile();
//		rightMP.startMotionProfile();
//		SetValueMotionProfile leftOutput = leftMP.getValue();
//		SetValueMotionProfile rightOutput = rightMP.getValue();
//		Robot.DRIVE_BASE.setLeftMotionControl(ControlMode.MotionProfile, leftOutput.value);
//		Robot.DRIVE_BASE.setRightMotionControl(ControlMode.MotionProfile, rightOutput.value);
//		SmartDashboard.putNumber("MP Left Motor Output", Robot.DRIVE_BASE.getLeftMotor().getMotorOutputPercent());
//		SmartDashboard.putNumber("MP Right Motor Output", Robot.DRIVE_BASE.getRightMotor().getMotorOutputPercent());
//	}
//
//	@Override
//	protected boolean isFinished()
//	{
//		return leftMP.getValue() == SetValueMotionProfile.Hold ||
//		 rightMP.getValue() == SetValueMotionProfile.Hold;
//	}
//
//	protected void end()
//	{
//        Robot.DRIVE_BASE.setLeftMotionControl(ControlMode.PercentOutput, 0);
//		leftMP.reset();
//		Robot.DRIVE_BASE.setRightMotionControl(ControlMode.PercentOutput, 0);
//		rightMP.reset();
//	}
//
//	protected void interrupted()
//	{
//        super.interrupted();
//        end();
//	}
//}
