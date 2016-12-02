package org.usfirst.frc.team1571.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Joystick stick;
	
	CANTalon masterTalonLeft, slaveTalonLeft, slaveTalonRight1, slaveTalonRight2, steeringTalon;
	int autoLoopCounter;
	
    public void robotInit() {
    	stick = new Joystick(0);
    	
    	masterTalonLeft = new CANTalon(0);
    	LiveWindow.addActuator("Drive Wheels", "Master Talon (Left)", masterTalonLeft);
    		masterTalonLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    		masterTalonLeft.reverseOutput(false);
    	slaveTalonLeft = new CANTalon(1);
    	LiveWindow.addActuator("Drive Wheels", "Slave Talon (Left)", slaveTalonLeft);
    		slaveTalonLeft.changeControlMode(CANTalon.TalonControlMode.Follower);
    		slaveTalonLeft.set(masterTalonLeft.getDeviceID());
    		slaveTalonLeft.reverseOutput(false);
    	slaveTalonRight1 = new CANTalon(6);
    	LiveWindow.addActuator("Drive Wheels", "Slave Talon (Right) 1", slaveTalonRight1);
    		slaveTalonRight1.changeControlMode(CANTalon.TalonControlMode.Follower);
    		slaveTalonRight1.set(masterTalonLeft.getDeviceID());
    		slaveTalonRight1.reverseOutput(true);
    	slaveTalonRight2 = new CANTalon(7);
    	LiveWindow.addActuator("Drive Wheels", "Slave Talon (Right) 2", slaveTalonRight2);
			slaveTalonRight2.changeControlMode(CANTalon.TalonControlMode.Follower);
			slaveTalonRight2.set(masterTalonLeft.getDeviceID());
			slaveTalonRight2.reverseOutput(true);
			
		steeringTalon = new CANTalon(4);
		LiveWindow.addActuator("Steering", "Steering Talon", steeringTalon);
			steeringTalon.changeControlMode(CANTalon.TalonControlMode.Position);
			steeringTalon.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
			steeringTalon.reverseSensor(false);
			steeringTalon.configPotentiometerTurns(10);

    }
    
    public void autonomousInit() {
    	autoLoopCounter = 0;
    }

    public void autonomousPeriodic() {
    }
    
    public void teleopInit(){
    }

    public void teleopPeriodic() {
    	double rightTrigger = stick.getRawAxis(3);
    	SmartDashboard.putNumber("Right Trigger", rightTrigger);
    	double leftTrigger = stick.getRawAxis(2);
    	SmartDashboard.putNumber("Left Trigger", leftTrigger);
    	double leftX = stick.getRawAxis(0);
    	SmartDashboard.putNumber("Left Stick X", leftX);
    	double targetPos = (leftX + 1)/2 * 900;
    	
    	SmartDashboard.putNumber("Potentiometer", steeringTalon.getAnalogInPosition());
    	SmartDashboard.putNumber("Steering Talon Speed", steeringTalon.getSpeed());
    	SmartDashboard.putNumber("Steering Talon Set Value", steeringTalon.get());
    	SmartDashboard.putNumber("Steering Talon Closed Loop Error", steeringTalon.getClosedLoopError());
    	SmartDashboard.putNumber("Steering Talon Closed Loop Ramp Rate", steeringTalon.getCloseLoopRampRate());
    	
    	masterTalonLeft.set(rightTrigger - leftTrigger);
    	steeringTalon.set(targetPos);
    }
    
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
