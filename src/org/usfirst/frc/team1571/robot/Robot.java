package org.usfirst.frc.team1571.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	Joystick stick;
	DoubleSolenoid solenoid;
	CANTalon talon;
	Encoder encoder;
	PowerDistributionPanel pdp;
	
	//some variables
	int autoLoopCounter;
	boolean extended;
	
	//axes
	double leftStickX;
	double rightTrigger;
	double leftTrigger;
	
	//buttons
	boolean buttonA;
	boolean buttonY;
	
	int encoderCount;
	
    public void robotInit() {
    	stick = new Joystick(0);
    	solenoid = new DoubleSolenoid(0,1);
    	talon = new CANTalon(0);
    	encoder = new Encoder(0,1);
    	pdp = new PowerDistributionPanel();
    }
    
    public void autonomousInit() {
    	autoLoopCounter = 0;
    	extended = false;
    }


    public void autonomousPeriodic() {
    	SmartDashboard.putNumber("Auto Loop Counter", autoLoopCounter);
    	SmartDashboard.putBoolean("Solenoid Extended", extended);
    	
    	if(autoLoopCounter % 100 == 0) {
    		
    		extended = !extended;
    		
    		if(extended) {
        		solenoid.set(DoubleSolenoid.Value.kForward);
    		} else {
        		solenoid.set(DoubleSolenoid.Value.kReverse);
    		}
    	}
    	
    	autoLoopCounter++;
    }
    
    public void teleopInit(){
    	extended = false;
    }

    
    public void teleopPeriodic() {
    	SmartDashboard.putNumber("Left Stick X", leftStickX = stick.getRawAxis(0));
    	SmartDashboard.putNumber("Right Trigger", rightTrigger = stick.getRawAxis(3));
    	SmartDashboard.putNumber("Left Trigger", leftTrigger = stick.getRawAxis(2));
    	SmartDashboard.putBoolean("A Button", buttonA = stick.getRawButton(1));
    	SmartDashboard.putBoolean("Y Button", buttonY = stick.getRawButton(4));
    	SmartDashboard.putNumber("Encoder Count", encoderCount = encoder.get());
    	
    	if(buttonA) {
    		SmartDashboard.putBoolean("Solenoid Extended", extended = false);
    		solenoid.set(DoubleSolenoid.Value.kForward);
    	} else if(buttonY) {
    		SmartDashboard.putBoolean("Solenoid Extended", extended = true);
    		solenoid.set(DoubleSolenoid.Value.kReverse);
    	}
    	if(leftStickX > .06 || leftStickX < -.15) {
    		talon.set(leftStickX);
    	} else {
    		talon.set(0);
    	}
    	
    	if(rightTrigger > .2) {
    		stick.setRumble(RumbleType.kRightRumble, (float)rightTrigger);
    	}
    	if(leftTrigger > .2) {
    		stick.setRumble(RumbleType.kLeftRumble, (float)leftTrigger);
    	}
    }

    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
