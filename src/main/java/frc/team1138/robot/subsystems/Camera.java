package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1138.robot.subsystems.DriveBase;

public class Camera extends Subsystem
{
	private CameraServer cam;
	
	@Override
	protected void initDefaultCommand() 
	{
		
	}

	public Camera() {
		cam = CameraServer.getInstance();
		cam.startAutomaticCapture();
	}
}
