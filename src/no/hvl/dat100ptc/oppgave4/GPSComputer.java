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
		return this.gpspoints;
	}

	public double totalDistance() {

		double distance = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
		}

		return distance;

	}

	public double totalElevation() {

		double elevation = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {

			if (gpspoints[i + 1].getElevation() > gpspoints[i].getElevation()) {
				elevation += gpspoints[i + 1].getElevation() - gpspoints[i].getElevation();
			}

		}
		return elevation;

	}

	public int totalTime() {

		int time = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			if (gpspoints[i + 1].getTime() > gpspoints[i].getTime()) {
				time += gpspoints[i + 1].getTime() - gpspoints[i].getTime();
			}

		}
		return time;
	}

	public double[] speeds() {

		double[] speeds = new double[gpspoints.length - 1];

		for (int i = 0; i < gpspoints.length - 1; i++) {
			speeds[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}
		return speeds;

	}

	public double maxSpeed() {

		double maxspeed = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			double speed = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
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

		double time = secs / 3600.0;

		if (speedmph < 10) {
			met = 4.0;
		}
		if (speedmph >= 10.0 && speedmph <= 12.0) {
			met = 6.0;
		}
		if (speedmph >= 12.0 && speedmph <= 14.0) {
			met = 8.0;
		}
		if (speedmph >= 14.0 && speedmph <= 16.0) {
			met = 10.0;
		}
		if (speedmph >= 16.0 && speedmph <= 20.0) {
			met = 12.0;
		} else {
			met = 16.0;
		}

		return kcal = met * weight * time;

	}

	public double totalKcal(double weight) {

		double totalkcal = 0;

		int sec = totalTime();
		double speed = totalDistance() / sec;

		totalkcal = kcal(weight, sec, speed);

		return totalkcal;

	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {

		String tidTxt = GPSUtils.formatTime(totalTime());

		String totalDistanse = GPSUtils.formatDouble(totalDistance());
		String totalElevation = GPSUtils.formatDouble(totalElevation());
		String maxSpeed = GPSUtils.formatDouble(maxSpeed() * 3.6);
		String averageSpeed = GPSUtils.formatDouble(averageSpeed() * 3.6);
		String energy = GPSUtils.formatDouble(totalKcal(WEIGHT));

		System.out.println("==============================================");
		System.out.println("Total time      :  " + tidTxt);
		System.out.println("Total distance  :  " + totalDistanse + " km");
		System.out.println("Total elevation :  " + totalElevation + " m ");
		System.out.println("Max speed       :  " + maxSpeed + " km/t ");
		System.out.println("Average speed   :  " + averageSpeed + " km/t");
		System.out.println("Energy          :  " + energy + " kcal ");

		System.out.println("==============================================");

	}

}
