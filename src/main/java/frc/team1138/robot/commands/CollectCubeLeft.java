package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.OI;
import frc.team1138.robot.Robot;

public class CollectCubeLeft extends Command
{

//	private boolean toggle = true;
	
	public CollectCubeLeft()
	{
		requires(Robot.COLLECTOR);
	}

	protected void initialize()
	{

	}

	protected void execute()
	{
		Robot.COLLECTOR.collectCubeWithRollersLeft(Robot.oi.getLeftTrigger());
		Robot.COLLECTOR.collectCubeWithRollersRight(Robot.oi.getRightTrigger());
//		if(toggle)
//			Robot.COLLECTOR.collectCubeWithRollersLeft();
//		else
//			Robot.COLLECTOR.stopCollectorLeft();
		//SmartDashboard.putBoolean("", value);
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return true;
	}

	protected void end()
	{
//		if(toggle)
//			toggle = false;
//		else
//			toggle = true;
	}

	protected void interrupted()
	{
		end();
	}
}
