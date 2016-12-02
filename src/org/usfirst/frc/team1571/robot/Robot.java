package org.usfirst.frc.team1571.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
	Joystick stick;
	int autoLoopCounter;
	
    public void robotInit() {
    	stick = new Joystick(0);
    }
    
   public void autonomousInit() {
    	autoLoopCounter = 0;
    }

    public void autonomousPeriodic() {
    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			autoLoopCounter++;
			} else {
		}
    }
    
    public void teleopInit(){
    }

    public void teleopPeriodic() {
    }
    
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
