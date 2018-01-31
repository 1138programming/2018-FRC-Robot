package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.Robot;
import frc.team1138.robot.subsystems.Collector;



public class CollectCube extends Command
{
	
	public CollectCube()
	{
		requires(Robot.COLLECTOR);
	}
	
	protected void initialize()
	{
		
	}
	
	protected void execute()
	{
		Robot.COLLECTOR.collectCubeWithRollers();
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void end()
	{}
	
	protected void interrupted()
	{}
}
