package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1138.robot.Robot;
import frc.team1138.robot.commands.DriveWithEncoders;

public class SideScore extends CommandGroup
{
	// Deciding which way to turn. If the boolean right = true, then the robot will
	// turn right. Otherwise, the robot will turn left
	boolean right;
	private int direction;

	// Setting some constants in order to determine the distance traveled in the
	// first DriveWithEncoders
	public static final double KRobotLength = 55; // inches
	public static final double KDistanceToSwitchMiddle = 168; // inches
	public static final double KWheelRadius = 2; // inches
	public static final double KWheelCircumference = 2 * Math.PI * KWheelRadius; // inches
	private final double KRotationsToSwitch = (KDistanceToSwitchMiddle - (KRobotLength / 2)) / KWheelCircumference; // rotations

	public SideScore(boolean right)
	{
		this.right = right;
		direction = right ? -1 : 1;

		requires(Robot.DRIVE_BASE);
		// addSequential(new TurnWithGyro());
		addSequential(new DriveWithEncoders(KRotationsToSwitch, 1)); // Drive until parallel to the switch
		addSequential(new TurnWithGyro(90 * direction, 1)); // Turn to face the switch
		addSequential(new DriveWithEncoders(1, 1)); // Drive towards the switch
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
