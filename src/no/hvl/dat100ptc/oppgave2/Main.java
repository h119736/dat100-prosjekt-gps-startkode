package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class Main {

	public static void main(String[] args) {

	
		
		GPSPoint p1 = new GPSPoint(4353, 54.44, 23.54, 23.33);
		GPSPoint p2 = new GPSPoint (4325, 53.44, 65.34, 24.54);
		
		GPSData gpsData = new GPSData(2);
		
		
		gpsData.insertGPS(p1);
		gpsData.insertGPS(p2);
		
		gpsData.print();

	}
}
