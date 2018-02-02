package frc.team1138.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.Robot;


public class SwitchPlungerMode extends Command{

	public SwitchPlungerMode()
	{
		requires(Robot.COLLECTOR);
	}
	
	protected void initialize()
	{
		
	}
	
	protected void execute()
	{
		Robot.COLLECTOR.switchMode();
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
