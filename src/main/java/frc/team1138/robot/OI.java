package frc.team1138.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team1138.robot.commands.EjectCube;
import frc.team1138.robot.commands.PositionLift;
import frc.team1138.robot.commands.ShiftLift;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{

	// Limit for Xbox joystick axes to get out of the "dead zone"
	public static final double KXboxDeadZoneLimit = 0.2;

	// Joystick Definitions
	public static final int KLogitechController = 0; // Base driver
	public static final int KXBoxController = 1; // Arms and lifts driver

	// Logitech button definitions
	public static final int KButton2 = 2; // Dumper Down position (collecting position)
	public static final int KButton3 = 3; // Dumper Exchange position (collecting from exchange)
	public static final int KButton4 = 4; // Dumper Up position (scoring position)
	public static final int KButton6 = 6; // Shifts the base

	// XBox button definitions
	public static final int KButtonA = 1; // Linear Lift preset position 1
	public static final int KButtonB = 2; // Linear Lift preset position 2
	public static final int KButtonX = 3; // Linear Lift preset position 3
	public static final int KButtonY = 4; // Shift the lift
	public static final int KLeftBumper = 5; // Toggle between collect/off
	public static final int KRightBumper = 6; // Toggle between eject/off
	public static final int KStartButton = 8; // Toggle forward/reverse
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.

	// Define joysticks and joystick buttons
	private Joystick logitechController, xBoxController;
	private JoystickButton btn2, btn3, btn4, btn6; // Logitech Button
	private JoystickButton btnA, btnB, btnX, btnY, btnLB, btnRB, btnStrt;

	public OI()
	{
		logitechController = new Joystick(KLogitechController);
		xBoxController = new Joystick(KXBoxController);

		// Logitech Buttons
		btn2 = new JoystickButton(logitechController, KButton2); // Puts the dumper in the down position
		btn3 = new JoystickButton(logitechController, KButton3); // Puts the dumper in the exchange position
		btn4 = new JoystickButton(logitechController, KButton4); // Puts the dumper in the up position
		btn6 = new JoystickButton(logitechController, KButton6); // shifts base between low/high speed

		// XBox Definitions (the functions of the buttons will change with time)
		btnA = new JoystickButton(xBoxController, KButtonA); // Puts the linear lift in position 1
		btnB = new JoystickButton(xBoxController, KButtonB); // Puts the linear lift in position 2
		btnX = new JoystickButton(xBoxController, KButtonX); // Puts the linear lift in posiiton 3
		btnY = new JoystickButton(xBoxController, KButtonY); // Shifts the lift speed
		btnLB = new JoystickButton(xBoxController, KLeftBumper); // Toggles rollers collecting
		btnRB = new JoystickButton(xBoxController, KRightBumper); // Toggles rollers ejecting
		btnStrt = new JoystickButton(logitechController, KStartButton); // Shifts the plunger/EjectCube from forward to reverse
																		// and vice versa

//TODO add in these buttons as they are created
//		btn2.whenPressed(new dumperdown());
//		btn3.whenPressed(new dumperexchange());
//		btn4.whenPressed(new dumperup());

//		btn6.whenPressed(new basespeed());
		//TODO figure out the values of the lift positions and speeds for the next 3 buttons
		btnA.whenPressed(new PositionLift(0)); //Bottom Position
		btnB.whenPressed(new PositionLift(4)); //Middle Position
		btnX.whenPressed(new PositionLift(7)); //Top Position
		btnY.whenPressed(new ShiftLift());
//		btnLB.whenPressed(new rollerscollect());
//		btnRB.whenPressed(new rollerseject());
		btnStrt.whenPressed(new EjectCube());
	}

	public double getRightAxis()
	{ // Right axis is right side drive
		if (logitechController.getThrottle() < -KXboxDeadZoneLimit
		 || logitechController.getThrottle() > KXboxDeadZoneLimit)
		{
			return logitechController.getThrottle(); // TODO check if it's twist for z-rotate axis
		}
		else
		{
			return 0;
		}
	}

	public double getLeftAxis()
	{ // Left controller is left side drive
		if (logitechController.getY() < -KXboxDeadZoneLimit || logitechController.getY() > KXboxDeadZoneLimit)
		{
			return logitechController.getY();
		}
		else
		{
			return 0;
		}
	}

	public boolean getLeftTrigger()
	{ // left controller's trigger is currently unused
		return true;
		// Add function here, currently this doesn't do much.
	}
  
	public boolean getRightTrigger()
	{ // right controller's trigger engages the shift on the base
		return true;
		// Add function here, currently this doesn't do much.
	}

	public double getLeftXBoxAxis()
	{ // left xbox axis controls the linear lift
		return (-xBoxController.getRawAxis(1));
	}

	public double getRightXBoxAxis()
	{ // right xbox axis controls the dumper arm
		return (-xBoxController.getRawAxis(5));
	}

	public double getXBoxPOV()
	{ // POV left and right is dumper conveyor
		return xBoxController.getRawAxis(6);
	}
}
