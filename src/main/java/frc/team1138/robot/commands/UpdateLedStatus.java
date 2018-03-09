package frc.team1138.robot.commands;

import frc.team1138.robot.Robot;
import frc.team1138.robot.subsystems.CoprocessorSubsystem.LEDModes;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.DigitalInput;

import java.io.IOException;

/**
 * @author Edward Pedemonte
 * @version 1.0.0
 */

public class UpdateLedStatus extends Command {
	private DigitalInput cubeLimitSwitch;
	private DigitalInput rungLimitSwitch;
	private DigitalInput lastMode; 
	long lastUpdateTime;
	
	public UpdateLedStatus() {
		// TODO Auto0generated constructor stub
		requires(Robot.coprocessorSubsystem);
		cubeLimitSwitch = new DigitalInput(0);
		rungLimitSwitch = new DigitalInput(1);
		lastUpdateTime = System.currentTimeMillis();
	}
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
		if (System.currentTimeMillis() > lastUpdateTime) {
			if (!cubeLimitSwitch.get() && cubeLimitSwitch != lastMode) {
				lastUpdateTime = System.currentTimeMillis() + 650;
				try {
					lastMode = cubeLimitSwitch;
					Robot.coprocessorSubsystem.setMode(LEDModes.Cube);
				} catch (IOException e) {
					System.out.println(e);
				}
			} else if (!rungLimitSwitch.get() && rungLimitSwitch != lastMode) {
				lastUpdateTime = System.currentTimeMillis() + 650;
				try {
					lastMode = rungLimitSwitch;
					Robot.coprocessorSubsystem.setMode(LEDModes.Rung);
				} catch (IOException e) {
					System.out.println(e);
				}
			} else if (lastMode != null && cubeLimitSwitch.get() && rungLimitSwitch.get()) {
				try {
					lastMode = null;
					Robot.coprocessorSubsystem.setMode(LEDModes.Idle);
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		}
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	
	
	@Override
	protected void end() {
	}
	
	@Override
	protected void interrupted() {
	}
}