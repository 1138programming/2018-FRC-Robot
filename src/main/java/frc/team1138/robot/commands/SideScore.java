package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1138.robot.OI;
import frc.team1138.robot.Robot;
import frc.team1138.robot.commands.DriveWithEncoders;
import frc.team1138.robot.subsystems.*;

public class SideScore extends CommandGroup
{
	boolean right;
	private OI oi;
	private int direction;
	public static final double KRobotLength = 55; //inches
	public static final double KDistanceToSwitchMiddle = 168; //inches
	public static final double KWheelRadius = 2; //inches
	public static final double KWheelCircumference = 2 * Math.PI * KWheelRadius; //inches
	private final double KRotationsToSwitch = (KDistanceToSwitchMiddle - (KRobotLength / 2)) / KWheelCircumference; //rotations
	
	public SideScore(boolean right)
	{
		this.right = right;
		direction = right ? -1 : 1;
		
		requires(Robot.DRIVE_BASE);
		oi = new OI();
		//addSequential(new TurnWithGyro());
		addSequential(new DriveWithEncoders(KRotationsToSwitch, 1));
		addSequential(new TurnWithGyro(90 * direction, 1));
		addSequential(new DriveWithEncoders(1, 1));
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
