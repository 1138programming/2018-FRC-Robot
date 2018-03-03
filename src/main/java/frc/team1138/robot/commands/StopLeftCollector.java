package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.Robot;

public class StopLeftCollector extends Command
{

	public StopLeftCollector()
	{
		requires(Robot.COLLECTOR);
	}

	protected void initialize()
	{

	}

	protected void execute()
	{
		Robot.COLLECTOR.stopCollectorLeft();
		//SmartDashboard.putBoolean("", value);
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
//		return false;
		return true;
	}

	protected void end()
	{
//		Robot.COLLECTOR.stopCollector();
	}

	protected void interrupted()
	{
//		end();
	}
}
