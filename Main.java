import lejos.hardware.motor.*;
import lejos.hardware.lcd.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.NXTTouchSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.sensor.NXTColorSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.Keys;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.*;


class Main {
	
	// Hovedfunksjon
	static void main(String[] args) throws Exception {
		// sjekkLyd()
		// sjekkEnde()
		// sjekkVeikant()
		// kjørFrem()
		// snu()
		// stoppOgVent()
		try {
			Brick brick = BrickFinder.getDefault();
    		Port s1 = brick.getPort("S1"); // soundSensor
    		Port s2 = brick.getPort("S2"); // fargeSensor
    		Port s3 = brick.getPort("S3"); 
		
			EV3 ev3 = (EV3) BrickFinder.getLocal();
			TextLCD lcd = ev3.getTextLCD();
			Keys keys = ev3.getKeys();
		
			sjekkLyd();
		} catch(Exception e) {
			System.out.println("Feil: " + e);
		}
		
	}
	
	// sjekkLyd
	static void sjekkLyd() { // Skal være en boolean
		NXTSoundSensor soundSensor = new NXTSoundSensor(s1);
		float[] volume = new float[soundSensor.sampleSize()];
		System.out.println(volume);
	}
	
	// sjekkEnde
	static boolean sjekkEnde() throws Exception{
		EV3ColorSensor fargesensor = new EV3ColorSensor(s2); // ev3-fargesensor
		SampleProvider fargeLeser = fargesensor.getMode("RGB");  // svart = 0.01..
		float[] fargeSample = new float[fargeLeser.sampleSize()];  // tabell som innholder avlest verdi

		int svart = 0;
		for (int i = 0; i<100; i++){
			fargeLeser.fetchSample(fargeSample, 0);
			svart += fargeSample[0]* 100;
		}
		svart = svart / 100 + 5;
		System.out.println("Svart: " + svart);
		boolean fortsett = true;

		while (fortsett){ 	// Fortsett så lenge roboten ikke treffer noe
		   fargeLeser.fetchSample(fargeSample, 0);
		   
		   if (fargeSample[0]*100 > svart) {   // sjekk sort linje
		   	  return false;
			  System.out.println("hvit");
		   } else {
			   // Kjør framover
			  	fortsett = false;
			  	return true;
			   	System.out.println("svart");
	  	   }
	   }


	}
	
	// sekkVeikant
	static boolean sjekkVeikant() throws Exception {
		return true;
	}
	
	// kjorFrem
	static void kjorFrem()throws Exception{
		LCD.clear();
		System.out.println("Kjører fremover");
		Motor.A.setSpeed(400);
   		Motor.C.setSpeed(400);
		Motor.A.forward();  // Start motor A - kjør framover
		Motor.C.forward();  // Start motor C - kjør framover
		Thread.sleep(2000);
	}
	
	// stoppOgVent
	static void stoppOgVent() throws Exception{
		LCD.clear();
		Motor.A.stop();
        Motor.C.stop();
        Thread.sleep(4000);
	}
	
	// stoppOgSnu
	static void stoppOgSnu()throws Exception{
		LCD.clear();
		System.out.println("Svinger 180 grader");
		Motor.A.forward();  // Start motor A - kjør framover
		Motor.C.backward(); // Start motor C - kjør bakover
		Thread.sleep(2000);
	}
	
	
}