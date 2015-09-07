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


public class Main{
	public static void main (String[] arg) throws Exception  {
	
	
		// Definerer sensorer:
		Brick brick = BrickFinder.getDefault();
    	Port s1 = brick.getPort("S1"); // fargesensor
 		Port s2 = brick.getPort("S2"); // trykksensor
		Port s3 = brick.getPort("S3"); // lydsensor
		EV3ColorSensor fargesensor = new EV3ColorSensor(s1); // EV3-fargesensor
		EV3TouchSensor trykksensor = new EV3TouchSensor(s2); // EV3-trykksensor
		NXTSoundSensor lydsensor = new NXTSoundSensor(s3); // NXT-lydsensor
		
		/* Definerer en fargesensor */
		SampleProvider fargeLeser = fargesensor.getMode("RGB");  // svart = 0.01..
		float[] fargeSample = new float[fargeLeser.sampleSize()];  // tabell som innholder avlest verdi
		
		/* Definerer en trykksensor */
		SampleProvider trykkLeser = trykksensor;
		float[] trykkSample = new float[trykkLeser.sampleSize()]; // tabell som inneholder avlest verdi
		
		/* Definerer en lydsensor */
		SampleProvider lydLeser = lydsensor.getDBAMode();
		float[] lydSample = new float[lydLeser.sampleSize()]; // tabell som inneholder avlest verdi
		
		// Setter hastighet på roboten
	    Motor.A.setSpeed(200);
	    Motor.C.setSpeed(200);
	    Motor.B.setSpeed(800);  // vifte arm
		
		
		// Beregn verdi for svart
	    int svart = 0;
	    for (int i = 0; i<100; i++){
	    	fargeLeser.fetchSample(fargeSample, 0);
	    	svart += fargeSample[0]* 100;
	    }
	    svart = svart / 100 + 5;
	    System.out.println("Svart: " + svart);

	    // Initierer variabler før løkken
	    boolean fortsett = true;
	    boolean fremover = true;
	    int retning = 0;
		
		while (fortsett){ 	// Fortsett så lenge trykksensoren ikke blir trykket inn
			
			// Fargesensor ting
			fargeLeser.fetchSample(fargeSample, 0);
			if (fargeSample[0]*100 < svart){   // sjekk sort linje
				if (fremover) {
					retning = 1;
					kjor(retning);
					fremover = false;
					Thread.sleep(1000);
				} else {
					retning = -1;
					kjor(retning);
					fremover = true;
					Thread.sleep(1000);
				}
			}

			// Lydsensor ting
			lydLeser.fetchSample(lydSample, 0);
			if (lydSample[0] > 0.5) {
				stoppOgVent(retning);
			}

			// Trykksensor ting
			trykksensor.fetchSample(trykkSample, 0);
			if (trykkSample[0] > 0){
				System.out.println("Avslutter...");
				fortsett = false;
				Motor.A.stop();
				Motor.C.stop();
				Motor.B.stop();
			}
		} // End while
	} // End main

	
	public static void kjor(int retning) throws Exception {
		   // Kjør framover
		if(retning > 0) {
			Motor.A.forward();
			Motor.C.forward();
			Motor.B.forward();
			System.out.println("Hvitt");
			Thread.sleep(100);
		} else {
			Motor.A.backward();
			Motor.C.backward();
			Motor.B.forward();
			System.out.println("Kjorer tilbake");
			Thread.sleep(100);
		}
	} // End kjor
	
	public static void stoppOgVent(int retning) throws Exception {
		Motor.A.stop();
		Motor.C.stop();
		Motor.B.stop();
		System.out.println("Venter pa at bilen skal kjore trygt forbi...");
		Thread.sleep(3000);
		kjor(retning);
	} // End stoppOgVent
	
} // End Main
