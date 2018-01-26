package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1138.robot.RobotMap;
import frc.team1138.robot.commands.DriveWithJoysticks;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SendableBase;


public class Pneumatics extends Subsystem
{

	@Override
	protected void initDefaultCommand() 
	{
		
	}
	
	//Declaring the compressor
	private Compressor pCompressor;
	
	
	public Pneumatics()
	{
		//Compressor
		pCompressor = new Compressor(0);
	}
	
}
