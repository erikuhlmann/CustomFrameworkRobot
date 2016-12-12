/**
 * 
 */
package org.ligerbots.test;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * @author Erik
 *
 */
public class Controls {
	private static final Controls INSTANCE = new Controls();

	public static Controls getInstance() {
		return INSTANCE;
	}
	
	private boolean[] xboxButtonStates = new boolean[Constants.XBOX_CONTROLLER_BUTTON_COUNT + 1];
	private boolean[] xboxButtonPressed = new boolean[Constants.XBOX_CONTROLLER_BUTTON_COUNT + 1];

	public void feed() {
		for(int i = 1; i <= Constants.XBOX_CONTROLLER_BUTTON_COUNT; i++) {
			xboxButtonPressed[i] = false;
			boolean val = DriverStation.getInstance().getStickButton(Constants.XBOX_CONTROLLER, (byte) i);
			if(!xboxButtonStates[i] && val) {
				xboxButtonPressed[i] = true;
			}
			xboxButtonStates[i] = val;
		}
	}

	public double getThrottle() {
		return DriverStation.getInstance().getStickAxis(
				Constants.XBOX_CONTROLLER,
				Constants.XBOX_CONTROLLER_THROTTLE_AXIS);
	}

	public double getTurn() {
		return DriverStation.getInstance().getStickAxis(
				Constants.XBOX_CONTROLLER,
				Constants.XBOX_CONTROLLER_TURN_AXIS);
	}

	public boolean getShiftButton() {
		return xboxButtonPressed[Constants.XBOX_CONTROLLER_LEFT_BUMPER];
	}

	public boolean getCompressorButton() {
		return xboxButtonPressed[Constants.XBOX_CONTROLLER_START_BUTTON];
	}

}
