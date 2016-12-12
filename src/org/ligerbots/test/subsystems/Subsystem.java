/**
 * 
 */
package org.ligerbots.test.subsystems;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Erik
 *
 */
public abstract class Subsystem {
	public abstract void sendDataToSmartDashboard();
	
	private static Set<Subsystem> allSubsystems = new HashSet<>();
	protected Subsystem() {
		allSubsystems.add(this);
	}
	
	public static void sendAllDataToSmartDashboard() {
		allSubsystems.forEach((Subsystem s) -> s.sendDataToSmartDashboard());
	}
}
