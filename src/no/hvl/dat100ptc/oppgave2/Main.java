package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class Main {

	
	public static void main(String[] args) {
		GPSPoint gps1 = new GPSPoint(10, 60.5, 5.7, 8.0);
		GPSPoint gps2 = new GPSPoint(15, 60.51, 5.71, 8.1);
		
		GPSData gpsData = new GPSData(2);
		
		gpsData.insertGPS(gps1);
		gpsData.insertGPS(gps2);
		
		gpsData.print();
		// TODO
	}
}
