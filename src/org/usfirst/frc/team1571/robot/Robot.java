package org.usfirst.frc.team1571.robot;


import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
	Joystick stick;
	
	int autoLoopCounter;
	
	CANTalon masterFlywheel, slaveFlywheel;
	DoubleSolenoid solenoid;
	
	boolean firing, retracting;
	
	Timer shootTimer;
	
        public void robotInit() {
    	stick = new Joystick(0);
    	
    	masterFlywheel = new CANTalon(0);
    		masterFlywheel.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    		masterFlywheel.reverseOutput(false);
    		masterFlywheel.enableBrakeMode(false);
    	slaveFlywheel = new CANTalon(1);
    		slaveFlywheel.changeControlMode(CANTalon.TalonControlMode.Follower);
    		slaveFlywheel.reverseOutput(true);
    		slaveFlywheel.enableBrakeMode(false);
    		slaveFlywheel.set(masterFlywheel.getDeviceID());
    	
    	solenoid = new DoubleSolenoid(0,1);
    	
    	shootTimer = new Timer();
    }
    
    public void autonomousInit() {
    }

    public void autonomousPeriodic() {
    }
    
    public void teleopInit(){
    	
    	firing = false;
    	retracting = false;
    	
    }

    public void teleopPeriodic() {
    	
    	double leftTrigger = stick.getRawAxis(3);
    	boolean aButton = stick.getRawButton(1);
    	boolean xButton = stick.getRawButton(2);
    	
    	if(leftTrigger > .15) {
        	masterFlywheel.set(leftTrigger);
    	}
    	
    	if((aButton || xButton) && !firing && !retracting) {
    		firing = true;
    		solenoid.set(DoubleSolenoid.Value.kReverse);
    		shootTimer.reset();
    		shootTimer.start();
    	}
    	
    	if(firing && shootTimer.get() >= .05) {
    		firing = false;
    		solenoid.set(DoubleSolenoid.Value.kForward);
    		shootTimer.reset();
    		retracting = true;
    	}
    	
    	if(retracting && shootTimer.get() >= .05 && !aButton) {
    		retracting = false;
    		shootTimer.stop();
    	}
    }
    
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
