package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.Robot;

public class CollectCubeRight extends Command
{

	private boolean toggle = true; 
	private boolean forward = false;
	
	public CollectCubeRight(boolean forward)
	{
		this.forward = forward;
		requires(Robot.COLLECTOR);
	}

	protected void initialize()
	{

	}

	protected void execute()
	{
		if(toggle)
			Robot.COLLECTOR.collectCubeWithRollersRight(forward);
		else
			Robot.COLLECTOR.stopCollectorRight();
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
		if(toggle)
			toggle = false;
		else
			toggle = true;
	}

	protected void interrupted()
	{
		end();
	}
}
