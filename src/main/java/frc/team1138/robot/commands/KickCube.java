package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.Robot;

public class KickCube extends Command
{
	public KickCube()
	{
		requires(Robot.COLLECTOR);
	}

	protected void initialize()
	{

	}

	protected void execute()
	{
		Robot.COLLECTOR.togglePlunger();
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return true;
	}

	protected void end()
	{
	}

	protected void interrupted()
	{
	}
}
