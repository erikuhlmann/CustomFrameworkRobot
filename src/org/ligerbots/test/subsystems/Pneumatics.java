/**
 * 
 */
package org.ligerbots.test.subsystems;

import org.ligerbots.test.Constants;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Erik
 *
 */
public class Pneumatics extends Subsystem {
	private static final Pneumatics INSTANCE = new Pneumatics();
	
	public static Pneumatics getInstance() {
		return INSTANCE;
	}

	private Compressor compressor;
	
	private Pneumatics() {
		compressor = new Compressor(Constants.PCM_ID);
	}
	
	public void setCompressorOn(boolean on) {
		compressor.setClosedLoopControl(on);
	}
	
	public boolean isCompressorOn() {
		return compressor.getClosedLoopControl();
	}

	@Override
	public void sendDataToSmartDashboard() {
		SmartDashboard.putBoolean("compressor_on", isCompressorOn());
	}

}
