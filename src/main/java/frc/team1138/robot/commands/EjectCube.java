package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.Robot;


public class EjectCube extends Command
{
//	private boolean toggle = true;

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
		Robot.COLLECTOR.kickCubeWithPlunger();
//		if(toggle)
//		{
//			
//		}
//		else
//		{
//			
//		}
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
		Robot.COLLECTOR.stopCollectorLeft();
		Robot.COLLECTOR.stopCollectorRight();
	}

	protected void interrupted()
	{
		end();
	}
}