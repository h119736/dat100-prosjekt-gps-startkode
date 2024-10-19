package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

import no.hvl.dat100ptc.TODO;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return gpspoints;
	}

	public double totalDistance() {

		double distance = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
		}
		return distance;
		// TODO
	}

	public double totalElevation() {

		double elevation = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			if (gpspoints[i + 1].getElevation() > gpspoints[i].getElevation()) {
				elevation += gpspoints[i + 1].getElevation() - gpspoints[i].getElevation();
			}
		}

		return elevation;
		// TODO
	}

	public int totalTime() {

		return gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime();
	}

	public double[] speeds() {

		double[] speeds = new double[gpspoints.length - 1];

		for (int i = 0; i < speeds.length; i++) {
			speeds[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}

		return speeds;
	}

	public double maxSpeed() {

		double maxspeed = 0;

		for (double speed : speeds()) {
			if (speed > maxspeed) {
				maxspeed = speed;
			}
		}

		return maxspeed;
	}

	public double averageSpeed() {
		double average = totalDistance() / totalTime();

		return average;
	}

	// conversion factor m/s to miles per hour (mps)
	public static final double MS = 2.23;

	public double kcal(double weight, int secs, double speed) {

		double kcal;

		double met = 0;
		double speedmph = speed * MS;

		if (speedmph < 10) {
			met = 4;
		} else if (speedmph <= 12) {
			met = 6;
		} else if (speedmph <= 14) {
			met = 8;
		} else if (speedmph <= 16) {
			met = 10;
		} else if (speedmph <= 20) {
			met = 12;
		} else {
			met = 16;
		}

		kcal = met * weight * (secs / (double)GPSUtils.SEKUNDER_PER_TIME);
				
		return kcal;
	}
	
	public double totalKcal(double weight) {
		double total = 0;
		
	    int sec;
	    double distance;
	    double speed;
	    
	    for (int i = 1; i < gpspoints.length; i++) {
	    	sec = gpspoints[i].getTime() - gpspoints[i-1].getTime();
	    	distance = GPSUtils.distance(gpspoints[i-1], gpspoints[i]);
	    	speed = distance / sec;
	    	total += kcal(weight, sec, speed);
	    }
	    
	    return total;
	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {

		System.out.println("==============================================");
		System.out.println("Total Time     :" + GPSUtils.formatTime(totalTime()));
		System.out.println("Total distance :" + GPSUtils.formatDouble(totalDistance() / 1000) + " km");
		System.out.println("Total elevation:" + GPSUtils.formatDouble(totalElevation()) + " m");
		System.out.println("Max speed      :" + GPSUtils.formatDouble(maxSpeed() * 3.6) + " km/t");
		System.out.println("Average speed  :" + GPSUtils.formatDouble(averageSpeed() * 3.6) + " km/t");
		System.out.println("Energy         :" + GPSUtils.formatDouble(totalKcal(WEIGHT)) + " kcal");
		System.out.println("==============================================");

		// TODO
	}

	// Metode til Oppgave 5c)
	public String[] statisticsStringArray() {
		String[] statArray = { "Total Time         :" + GPSUtils.formatTime(totalTime()) + " ",
				"Total distance    :" + GPSUtils.formatDouble(totalDistance() / 1000.0) + " km ",
				"Total elevation   :" + GPSUtils.formatDouble(totalElevation()) + " m ",
				"Max speed         :" + GPSUtils.formatDouble(maxSpeed() * 3.6) + " km/t ",
				"Average speed  :" + GPSUtils.formatDouble(averageSpeed() * 3.6) + " km/t ",
				"Energy               :" + GPSUtils.formatDouble(totalKcal(WEIGHT)) + " kcal  " };

		return statArray;
	}

	// Oppgave 6a)
	public double[] climbs() {
		double[] climbs = new double[gpspoints.length - 1];
		double elevation;
		double distance;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			elevation = gpspoints[i + 1].getElevation() - gpspoints[i].getElevation();
			distance = GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
			climbs[i] = elevation / distance * 100;
		}

		return climbs;
	}

	public double maxClimb() {
		return GPSUtils.findMax(climbs());
	}

	// 6c)
	public double maxElevation() {
		double maxElevation = gpspoints[0].getElevation();

		for (int i = 0; i < gpspoints.length; i++) {
			if (gpspoints[i].getElevation() > maxElevation) {
				maxElevation = gpspoints[i].getElevation();
			}
		}
		return maxElevation;
	}

}
