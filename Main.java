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
		EV3ColorSensor fargesensor = new EV3ColorSensor(s1); // ev3-fargesensor
		NXTSoundSensor lydsensor = new NXTSoundSensor(s3); // NXT-lydsensor
		
		
		SampleProvider fargeLeser = fargesensor.getMode("RGB");  // svart = 0.01..
		float[] fargeSample = new float[fargeLeser.sampleSize()];  // tabell som innholder avlest verdi
		
		/* Definerer en trykksensor */
		SampleProvider trykksensor = new EV3TouchSensor(s2);
		float[] trykkSample = new float[trykksensor.sampleSize()]; // tabell som inneholder avlest verdi
		
		SampleProvider lydLeser = lydsensor.getDBAMode();
		float[] lydSample = new float[lydLeser.sampleSize()]; // tabell som inneholder avlest verdi
		
		for (int i = 0; i < 1000; i++) {
			lydLeser.fetchSample(lydSample, 0);
			System.out.println(lydSample[0]);
		}
	}
	
	
	
  
}