package frc.team1138.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

//import frc.team1138.robot.commands.ExampleCommand;
import frc.team1138.robot.commands.ShiftBase;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	//Limit for Xbox joysticks to get out of the "dead zone"
	public static final double KXboxDeadZoneLimit = 0.2;
	
	//Joystick Definitions
	public static final int KLeftJoystick = 0 ;
	public static final int KRightJoystick = 1 ; 
	public static final int KXBoxController = 2;

	//XBox button definitions
	public static final int KButtonA = 1 ;	//Add description of function here
	public static final int KButtonB = 2 ;	//Add description of function here
	public static final int KButtonX = 3 ;	//Add description of function here
	public static final int KButtonY = 4 ;	//Add description of function here
	public static final int KLeftBumper = 5 ;	//Add description of function here
	public static final int KRightBumper = 6 ; //Add description of function here
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;
	
	//Define joysticks and joystick buttons
	private Joystick leftController, rightController, xBoxController;
	private JoystickButton shiftBtn; // Logitech Button 
	private JoystickButton btnA, btnB, btnX, btnY, btnLB, btnRB; 
	
	public OI() {
		leftController = new Joystick(KLeftJoystick);
		rightController = new Joystick(KRightJoystick);
		xBoxController = new Joystick(KXBoxController);
		
		//Logitech Buttons
		shiftBtn = new JoystickButton(leftController, 1); //Shifts the Base from Low Gear to High Gear and vice versa
		
		//XBox Definitions (the functions of the buttons will change with time)
		btnA	= new JoystickButton(xBoxController, KButtonA) ;	//Toggle Vision
		btnB = new JoystickButton(xBoxController, KButtonB) ;	//Toggle Esophagus
		btnX = new JoystickButton(xBoxController, KButtonX) ;	//Turn on shooter
		btnY = new JoystickButton(xBoxController, KButtonY) ;	//Turn off shooter
		btnLB = new JoystickButton(xBoxController, KLeftBumper) ;	//Decrease Flywheel Speed
		btnRB = new JoystickButton(xBoxController, KRightBumper) ;	//Increase Flywheel Speed
		
		shiftBtn.whenPressed(new ShiftBase());
//		The following are example buttons from Momentum:
//		buttonX->WhenPressed(new EngageShooter());
//		buttonY->WhenPressed(new DisengageShooter());
//		buttonB->WhenPressed(new ToggleEsophagus());
//		buttonA->WhenPressed(new VisionTracking());
//		buttonLB->WhenPressed(new OpenEsophagus());		//buttonLB->WhenPressed(new FlywheelDecreaseSpeed());
//		buttonRB->WhenPressed(new CloseEsophagus());	//buttonRB->WhenPressed(new FlywheelIncreaseSpeed());
	}
	public double getRightController() {			//Right controller is right side drive
		return rightController.getY();
	}

	public double getLeftController() {			//Left controller is left side drive
		return leftController.getY();
	}

	public boolean getLeftTrigger() {				//left controller's trigger is currently unused
		return true;
		//Add function here, currently this doesn't do much.
	}

	public boolean getRightTrigger() {			//right controller's trigger engages the shift on the base
		return shiftBtn.get(); 
	}

	public double getLeftXBoxAxis() {			//left xbox axis controls the collector
		return (-xBoxController.getRawAxis(1));
	}

	//Fine control from collector, signal from joystick is reversed
	public double getRightXBoxAxis() {
		return (-xBoxController.getRawAxis(5));
	}
}
