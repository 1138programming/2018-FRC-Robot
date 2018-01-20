package frc.team1138.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * NOTE: We are localizing all variables this year. Therefore, to find their
 * values look in the local files that they are used in. They are in this code
 * as well just in case you can't find something, but are all commented out.
 */
public class RobotMap {	

	/*
	This first comment box is all of the mapping we are using for MechaP. The second is from JavaMomentum.
	
	//Setup the base configuration by assigning talons
	public static final int KLeftRearBaseTalon =  1;
	public static final int KLeftFrontBaseTalon = 2 ;
	public static final int KLeftTopBaseTalon = 3;
	public static final int KRightRearBaseTalon = 4 ;
	public static final int KRightFrontBaseTalon = 5 ;
	public static final int KRightTopBaseTalon = 6;
	
	public static final int KLeftBaseMaster = 1; //KLeftMaster = Master Talon for left side
	public static final int KRightBaseMaster = 2; //KRightMaster = Master Talon for right side
	
	//all of the solenoids are doubles, so they need 2 numbers each.  If you change one, be sure to change
	//the other one of the pair.
	public static final int KShifterSolenoid1 = 0;
	public static final int KShifterSolenoid2 = 1;
	//public static final int KLiftSolenoid1 = 2;
	//public static final int KLiftSolenoid2 = 3;
	
	//This is a limit to make sure that the joystick isn't potentially stuck for the function tankDrive
	public static final double KDeadZoneLimit = 0.1;
	
	public static final double KXboxDeadZoneLimit = 0.2;
	
	//Setup the flywheel configuration
	public static final int KFlywheelIndexTalon = 9;
	public static final int KFlywheelAngleAdjusterTalon = 10;
	public static final int KFlywheelBottomTalon = 11;
	public static final int KFlywheelTopTalon = 12;
	
	//Setup the hopper configuration
	public static final int KHopperTalon = 8;
	
	//Setup the turret configuration
	public static final int KTurretTalon = 7;
	
	//Joystick Definitions
	public static final int KLeftJoystick = 0 ;
	public static final int KRightJoystick = 1 ;
	public static final int KXBoxController = 2;

	//XBox button definitions
	public static final int KButtonA = 1 ;	//Toggle Vision
	public static final int KButtonB = 2 ;	//Toggle Esophagus
	public static final int KButtonX = 3 ;	//Turn on shooter
	public static final int KButtonY = 4 ;	//Turn off shooter
	public static final int KLeftBumper = 5 ;	//Decrease Flywheel Speed
	public static final int KRightBumper = 6 ;
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;
	
	private Joystick leftController, rightController, xBoxController;
	private JoystickButton shiftBtn liftBtn; // Logitech Button 
	private JoystickButton btnA, btnB, btnX, btnY, btnLB, btnRB; 
	
	*/
	
	/*
	This is JavaMomentum code to take as examples for things we may need later.
	
	//static finalants for Autonomous routines
	//Circumference of wheel - 330.2 millimeters. Divide by this number to get number of rotations for distances
	public static final double KWheelRadius = 5.255; //In centimeters
	public static final double KWheelCircumference = 33.02; //In centimeters
	public static final double KDistanceToBaseLine = 191.64; //In centimeters
	public static final double KDistanceToPilotTower = 86.86; //In centimeters
	public static final double KRevsToBaseLine = KDistanceToBaseLine / KWheelCircumference;	//rotations from the diamond plate to the baseline from Field CAD (191.64cm)
	public static final double KRevsToPilotTower = KDistanceToPilotTower / KWheelCircumference; //rotations from the baseline to the pilot tower from Field CAD (86.86cm)
	public static final double KTurnToPilotTower = 55;	//degrees to turn from the baseline to face the pilot tower.
	public static final double KAutonStraightSpeed = .5;	//TODO lets go slowly and backwards
	public static final double KAutonTurnSpeed = .5; //TODO turn slowly towards pilot tower
	public static final double KRevsToCrossTheLine = 10; //Unofficial distance to cross the line in autonomous
	public static final double KRevsToVisionTracking = 3; //Unofficial distance  until we turn on vision tracking
	public static final double KEncoderTicksPerRev = 4096; //The amount of ticks it takes to do one full rotation with the encoder

	//Okay, don't get clever and decide this isn't actually the way we are turning.  I don't care which way we are turning.
	//Left turn means the turn we make when our starting position is on the left side of the field.  Right Turn is when
	//our starting position is on the right side of the field.  We are also doing the turn going backwards and that
	//changes the direction too.  Don't think to much about this or your brain will explode.
	public static final boolean KLeftTurn = true;
	public static final boolean KRightTurn = false;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	*/
}
