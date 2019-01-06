package frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
<<<<<<< HEAD
=======
import frc.team1138.robot.commands.UpdateLedStatus;
>>>>>>> f25d8c58c44387c10b8282830cb421cec396ed68

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

import java.io.IOException;


/**
* The LEDSubsystem class
*/
public class LEDSubsystem extends Subsystem
{
	public enum LEDModes {
		Off			((byte) 0),
		Idle		((byte) 1),
		Cube		((byte) 2),
		Rung		((byte) 3);
		
		private final byte value;
		private LEDModes(byte value) {
			this.value = value;
		}
		
		public byte getValue() {
			return value;
		}
	}
	
	public enum LEDResults {
		Success			((byte) 0),
		Error			((byte) 1);
		
		private final byte value;
		private LEDResults(byte value) {
			this.value = value;
		}
		
		public byte getValue() {
			return value;
		}
	}
	
	// Initialize the I2C port
	private final I2C Wire;
	private byte[] received;
	
	public LEDSubsystem()
	{
		System.out.println("LED Subsystem Initializing...");
		received = new byte[1];
		Wire = new I2C(Port.kMXP, 4);
		
		try {
			setMode(LEDModes.Off);
			System.out.println("LED Subsystem Initialized!");
		} catch (IOException e) {
			System.out.println("LED Subsystem Failed!");
			System.out.println(e);
		}
	}
	
	public void initDefaultCommand()
	{
		// We don't actually have a default command; leave this blank
	}
	
	public void setMode(LEDModes mode) throws IOException {
		// Turn the the mode into a byte to send (from the enum declaration)
		byte[] toSend = new byte[1];
		toSend[0] = mode.getValue();
		
		if (Wire != null && toSend != null) {
			// Check that we have a proper I2C connection to avoid
			// NullPointerExceptions
			Wire.writeBulk(toSend, 1);
		}
		
		// Receive a response to check for an error
		Wire.readOnly(received,  1);
		
		// Do we have an error?
		if (received[0] == LEDResults.Error.getValue()) {
			throw new IOException("Error from rioDuino");
		}
	}
}