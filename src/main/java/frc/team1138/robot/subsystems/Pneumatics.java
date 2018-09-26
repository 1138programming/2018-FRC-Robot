package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem
{	
	private Compressor Compressor = new Compressor(0);
	
	@Override
	protected void initDefaultCommand() 
	{
	Compressor.setClosedLoopControl(true);
	}
}