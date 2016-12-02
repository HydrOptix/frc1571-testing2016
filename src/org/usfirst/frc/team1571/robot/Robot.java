package org.usfirst.frc.team1571.robot;


import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Joystick stick;
	int talons = 8;
	int solenoids = 3;
	LiveWindowSendable component[] = new LiveWindowSendable[talons + solenoids];
	int componentIndex;
	boolean bPressed;
	boolean xPressed;
	int talon, solenoid;
	
    public void robotInit() {
    	stick = new Joystick(0);
    	
	    int i = 0;
	    		
    	for(int j = 0; j < talons; j++) {
    		component[i] = new CANTalon(j);
    		i++;
    	}
    	
    	for(int j = 0; j < solenoids; j++) {
    		component[i] = new Solenoid(j);
    		i++;
    	}
	    	
    }
    
    
    public void autonomousInit() {	
    	componentIndex = 0;
    	talon = 0;
    	solenoid = 0;
    }

    
    public void autonomousPeriodic() {
    	
    	LiveWindowSendable currentComponent = component[componentIndex];
    		
    		
		if(currentComponent instanceof CANTalon) {
			SmartDashboard.putString("Current Component", "Talon " + talon);
			
			CANTalon currentTalon = (CANTalon)currentComponent;
			currentTalon.set(1);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			currentTalon.set(-1);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			currentTalon.set(0);
			
			if(talon < talons - 1) {
				talon++;
			} else {
				talon = 0;
			}
			
		} else if(currentComponent instanceof Solenoid) {
			SmartDashboard.putString("Current Component", "Solenoid " + solenoid);

			Solenoid currentSolenoid = (Solenoid)currentComponent;
			currentSolenoid.set(true);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			currentSolenoid.set(false);
			
			if(solenoid < solenoids - 1) {
				solenoid++;
			} else {
				solenoid = 0;
			}
		}
		
		if(componentIndex < component.length - 1) {
			componentIndex++;
		} else {
			componentIndex = 0;
		}
    }
    
        public void teleopInit(){
        	componentIndex = 0;
        	bPressed = false;
        	xPressed = false;
    }

    public void teleopPeriodic() {
    	
    	double rightTrigger = stick.getRawAxis(3), leftTrigger = stick.getRawAxis(2);
    	boolean xButton = stick.getRawButton(2), bButton = stick.getRawButton(3);
    	
    	if(Math.abs(rightTrigger) > .15) {
    		rightTrigger = 0;
    	}
    	if(Math.abs(leftTrigger) > .15) {
    		leftTrigger = 0;
    	}
    	SmartDashboard.putNumber("Right Trigger", rightTrigger);
    	SmartDashboard.putNumber("Left Trigger", leftTrigger);
    	SmartDashboard.putBoolean("X Button", xButton);
    	SmartDashboard.putBoolean("B Button", bButton);
    	
    	
    	if(bButton) {
    		if(!bPressed) {
    			componentIndex++;
    			bPressed = true;
    		}
    	} else {
    		bPressed = false;
    		
    		if(xButton) {
    			if(!xPressed) {
    				componentIndex--;
    				xPressed = true;
    			}
    		} else {
    			xPressed = false;
    		}
    	}
    	
    	LiveWindowSendable currentComponent = component[componentIndex];
    	
    	if(currentComponent instanceof CANTalon) {
			SmartDashboard.putString("Current Component", "Talon " + componentIndex);
			
			CANTalon currentTalon = (CANTalon)currentComponent;
			currentTalon.set(rightTrigger = leftTrigger);
			
		} else if(currentComponent instanceof Solenoid) {
			SmartDashboard.putString("Current Component", "Solenoid " + (componentIndex - talons));

			Solenoid currentSolenoid = (Solenoid)currentComponent;
			if(rightTrigger > 0) {
				currentSolenoid.set(true);
			} else {
				currentSolenoid.set(false);
			}
		}
    }
    
        public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
