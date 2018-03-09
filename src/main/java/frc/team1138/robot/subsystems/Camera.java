package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem
{
	private static CameraServer cam;
	
	@Override
	protected void initDefaultCommand() 
	{
		
	}

	public Camera() {
		cam = CameraServer.getInstance();
		cam.startAutomaticCapture();
	}
}
