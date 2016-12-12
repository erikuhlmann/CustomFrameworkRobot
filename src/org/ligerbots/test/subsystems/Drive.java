/**
 * 
 */
package org.ligerbots.test.subsystems;

import java.util.HashMap;
import java.util.Map;

import org.ligerbots.test.Constants;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Erik
 *
 */
public class Drive extends Subsystem {
	private static final Drive INSTANCE = new Drive();

	public static Drive getInstance() {
		return INSTANCE;
	}
	
	public enum Shift {
		HIGH_GEAR,
		LOW_GEAR;
		
		public static Shift oppositeOf(Shift type) {
			if(type == Shift.HIGH_GEAR) {
				return Shift.LOW_GEAR;
			} else {
				return Shift.HIGH_GEAR;
			}
		}
	}

	private Map<Integer, CANTalon> driveTalons = new HashMap<>();
	private RobotDrive driveHelper;
	private AHRS navX;
	private DoubleSolenoid shifter;

	private Drive() {
		int masterLeftId = Constants.CAN_TALON_LEFT_IDS[0];
		int masterRightId = Constants.CAN_TALON_RIGHT_IDS[0];
		driveTalons.put(masterLeftId, createMaster(masterLeftId));
		driveTalons.put(masterRightId, createMaster(masterRightId));
		for (int i = 1; i < Constants.CAN_TALON_LEFT_IDS.length; i++) {
			driveTalons.put(Constants.CAN_TALON_LEFT_IDS[i],
					createSlave(Constants.CAN_TALON_LEFT_IDS[i], masterLeftId));
		}
		for (int i = 1; i < Constants.CAN_TALON_RIGHT_IDS.length; i++) {
			driveTalons.put(Constants.CAN_TALON_RIGHT_IDS[i], createSlave(
					Constants.CAN_TALON_RIGHT_IDS[i], masterRightId));
		}

		driveHelper = new RobotDrive(driveTalons.get(masterLeftId),
				driveTalons.get(masterRightId));

		navX = new AHRS(SPI.Port.kMXP);
		shifter = new DoubleSolenoid(Constants.PCM_ID, Constants.PCM_SHIFT_UP, Constants.PCM_SHIFT_DOWN);
	}

	public void drive(double mag, double curve) {
		driveHelper.drive(mag, curve);
	}
	
	public void shift(Shift type) {
		if(type == Shift.HIGH_GEAR) {
			shifter.set(DoubleSolenoid.Value.kForward);
		} else {
			shifter.set(DoubleSolenoid.Value.kReverse);
		}
	}
	
	public Shift getShiftState() {
		if(shifter.get() == DoubleSolenoid.Value.kForward) {
			return Shift.HIGH_GEAR;
		} else {
			return Shift.LOW_GEAR;
		}
	}

	private CANTalon createMaster(int id) {
		CANTalon master = new CANTalon(id);
		master.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		master.enableBrakeMode(true);
		return master;
	}

	private CANTalon createSlave(int id, int masterId) {
		CANTalon slave = new CANTalon(id);
		slave.changeControlMode(CANTalon.TalonControlMode.Follower);
		slave.set(masterId);
		slave.enableBrakeMode(true);
		return slave;
	}

	@Override
	public void sendDataToSmartDashboard() {
		SmartDashboard.putNumber("yaw", navX.getYaw());

		driveTalons.forEach((Integer i, CANTalon talon) -> {
			SmartDashboard.putNumber("talon_" + i + "_watts",
					talon.getOutputVoltage() * talon.getOutputCurrent());
		});
	}

}
