package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1138.robot.Robot;
import frc.team1138.robot.subsystems.Lift;

public class CycleLatch extends Command
{

	public CycleLatch()
	{
		requires(Robot.LIFT);
	}

	protected void initialize()
	{

	}

	protected void execute()
	{
		Robot.LIFT.moveLatch();
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return true;
	}

	protected void end()
	{
		if(Lift.servoWaiting == 0)
			SmartDashboard.putString("Where is latch?", "Fully engaged");
		
		if(Lift.servoWaiting == 1)
			SmartDashboard.putString("Where is latch?", "Engaged at angle");
		
		if(Lift.servoWaiting == 2)
			SmartDashboard.putString("Where is latch?", "Fully disengaged");
	}

	protected void interrupted()
	{
		
	}
}
