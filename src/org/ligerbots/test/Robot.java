
package org.ligerbots.test;

import org.ligerbots.test.subsystems.Drive;
import org.ligerbots.test.subsystems.Pneumatics;
import org.ligerbots.test.subsystems.Subsystem;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * 
 * @author Erik
 *
 */
public class Robot extends IterativeRobot {
	Drive drive;
	Pneumatics pneumatics;
	Controls controls;
	
    public void robotInit() {
    	drive = Drive.getInstance();
    	pneumatics = Pneumatics.getInstance();
    	controls = Controls.getInstance();
    }
    
    public void teleopInit() {
    	drive.drive(0, 0);
    	pneumatics.setCompressorOn(true);
    }
    
    public void teleopPeriodic() {
    	controls.feed();
    	
        drive.drive(controls.getThrottle(), controls.getTurn());
        if(controls.getShiftButton()) {
        	drive.shift(Drive.Shift.oppositeOf(drive.getShiftState()));
        }
        
        if(controls.getCompressorButton()) {
        	pneumatics.setCompressorOn(!pneumatics.isCompressorOn());
        }
        
        Subsystem.sendAllDataToSmartDashboard();
    }
        
}
