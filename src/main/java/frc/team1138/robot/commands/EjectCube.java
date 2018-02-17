package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.Robot;

public class EjectCube extends Command
{

	public EjectCube()
	{
		requires(Robot.COLLECTOR);
	}

	protected void initialize()
	{

	}

	protected void execute()
	{
		Robot.COLLECTOR.ejectCubeWithRollers();
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