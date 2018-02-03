package frc.team1138.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1138.robot.OI;
import frc.team1138.robot.Robot;

public class moveArmWithEncoder extends Command {
	private OI oi;
	public moveArmToLimitSwitch
	requires(Robot.ARM);
	oi = new OI();
}
