package no.hvl.dat100ptc.oppgave6;

import java.util.Random;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class CycleComputer extends EasyGraphics {

	private static int SPACE = 10;
	private static int MARGIN = 20;

	private static int ROUTEMAPXSIZE = 600;
	private static int ROUTEMAPYSIZE = 300;
	private static int HEIGHTSIZE = 200;

	private GPSComputer gpscomp;
	private GPSPoint[] gpspoints;
	private GPSPoint[] gpspoints2;
	private double[] speeds;
	private int syklist1;
	private int syklist2;// ----
	private double maxSpeed;
	private double maxSpeed2 = 0;
	private double[] climbs;
	private double totalDistance = 0;
	private double maxElevation;

	private int N = 0;

	private double minlon, minlat, maxlon, maxlat;

	private double xstep, ystep;

	public CycleComputer() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");

		gpscomp = new GPSComputer(filename);
		gpspoints = gpscomp.getGPSPoints();
		speeds = gpscomp.speeds();
		maxSpeed = gpscomp.maxSpeed();
		climbs = gpscomp.climbs();
		maxElevation = gpscomp.maxElevation();
		gpspoints2 = createSecondCyclist();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		// throw new UnsupportedOperationException(TODO.method());

		N = gpspoints.length; // number of gps points

		minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));

		xstep = xstep();
		ystep = ystep();

		makeWindow("Cycle Computer", 2 * MARGIN + ROUTEMAPXSIZE, 2 * MARGIN + ROUTEMAPYSIZE + HEIGHTSIZE + SPACE);

		bikeRoute();
		
		int j = 0;

		for (int i = 0; i < gpspoints.length; i++) {
			syklist1 = showPosition(i, syklist1, gpspoints, maxSpeed);
			if (i == 0) {
				syklist2 = showPosition(j, syklist2, gpspoints2, maxSpeed2);
				j++;
			}else {
				while (j < gpspoints2.length - 1 && (gpspoints2[j].getTime() <= gpspoints[i].getTime() || i == gpspoints.length - 1)) {
					syklist2 = showPosition(j, syklist2, gpspoints2, maxSpeed2);
					j++;
				}
			}
			showCurrent(i);
			//showHeight(ROUTEMAPYSIZE, i);
		}

	}

	// main method to visualise route, position, and current speed/time
	public void bikeRoute() {
		int x, y, x2, y2;
		int circleRadius = 2;
		int ybase = MARGIN + ROUTEMAPYSIZE * 2;

		for (int i = 0; i < gpspoints.length; i++) {
			showHeight(ROUTEMAPYSIZE, i);
			x = ROUTEMAPXSIZE + MARGIN - (int) ((maxlon - gpspoints[i].getLongitude()) * xstep);
			y = ROUTEMAPYSIZE - (int) ((maxlat - gpspoints[i].getLatitude()) * ystep);
			if (i != gpspoints.length - 1) {
				if (climbs[i] < 0) {
					setColor(0, 255, 0);
				} else {
					setColor(255, 0, 0);
				}
				x2 = ROUTEMAPXSIZE + MARGIN - (int) ((maxlon - gpspoints[i + 1].getLongitude()) * xstep);
				y2 = ROUTEMAPYSIZE - (int) ((maxlat - gpspoints[i + 1].getLatitude()) * ystep);
				drawLine(x, ybase - y, x2, ybase - y2);
			}
			fillCircle(x, ybase - y, circleRadius);
		}
	}

	public double xstep() {
		return ROUTEMAPXSIZE / (Math.abs(maxlon - minlon));
	}

	public double ystep() {
		return ROUTEMAPYSIZE / 2 / (Math.abs(maxlat - minlat));
	}

	// show current speed and time (i'th GPS point)
	public void showCurrent(int i) {
		int currentTime = gpspoints[i].getTime() - gpspoints[0].getTime();
		double currentSpeed = 0;
		int x = MARGIN, y = MARGIN;

		if (i != 0) {
			currentSpeed = speeds[i - 1];
			totalDistance += GPSUtils.distance(gpspoints[i - 1], gpspoints[i]);
		}

		String[][] statisticsArray = { { "Total time:          ", GPSUtils.formatTime(currentTime), " " },
				{ "Total distance:      ", GPSUtils.formatDouble(totalDistance / 1000), " km" },
				{ "Current elevation: ", GPSUtils.formatDouble(gpspoints[i].getElevation()), " m" },
				{ "Current speed:     ", GPSUtils.formatDouble(currentSpeed * 3.6), " km/t " },
				{ "Average speed:   ", GPSUtils.formatDouble(totalDistance / currentTime * 3.6), " km/t " },
				{ "Energy:               ",
						GPSUtils.formatDouble(gpscomp.kcal(80, currentTime, totalDistance / currentTime)),
						" kcal  " } };

		setColor(255, 255, 255);
		fillRectangle(120, 0, 45, 80);
		setColor(0, 0, 0);

		for (int row = 0; row < statisticsArray.length; row++) {
			if (i == 0) {
				drawString(statisticsArray[row][0], x, y);
				drawString(statisticsArray[row][2], x + 145, y);
			}
			drawString(statisticsArray[row][1], x + 90, y);
			y += 10;
		}
	}

	// show current height (i'th GPS point)
	public void showHeight(int ybase, int i) {
		int bredde;
		if ((2 * MARGIN + ROUTEMAPXSIZE) / N >= 2) {
			bredde = 2;
		} else {
			bredde = 1;
		}
		int x = MARGIN + i * bredde, y;

		setColor(0, 0, 255);

		if ((int) gpspoints[i].getElevation() < 0) {
			y = 0;
		} else {
			y = (int) (gpspoints[i].getElevation() / maxElevation * HEIGHTSIZE);
		}
		drawLine(x, ybase, x, ybase - y);
	}

	// show current position (i'th GPS point)
	public int showPosition(int i, int syklist, GPSPoint[] gpspoints, double maxSpeed) {
		int x, y;
		int circleRadius = 5;
		double speed;
		int ybase = MARGIN + ROUTEMAPYSIZE * 2;

		x = ROUTEMAPXSIZE + MARGIN - (int) ((maxlon - gpspoints[i].getLongitude()) * xstep);
		y = ROUTEMAPYSIZE - (int) ((maxlat - gpspoints[i].getLatitude()) * ystep);

		setColor(0, 0, 255);
		if (i == 0) {
			syklist = fillCircle(x, ybase - y, circleRadius);
		} else {
			if (i != gpspoints.length - 1) {
				speed = GPSUtils.speed(gpspoints[i-1], gpspoints[i]);
				setSpeed(1 + (int) ((speed / maxSpeed) * 9));
			}
			moveCircle(syklist, x, ybase - y);
		}
		return syklist;
	}

	// **************************************************************************************************
	public GPSPoint[] createSecondCyclist() {
		GPSPoint[] gpspoints2 = new GPSPoint[gpspoints.length];
		int time = 0;
		Random random = new Random();

		for (int i = 0; i < gpspoints.length; i++) {
			if (i == 0) {
				gpspoints2[i] = new GPSPoint(gpspoints[i].getTime(), gpspoints[i].getLatitude(),
						gpspoints[i].getLongitude(), gpspoints[i].getElevation());
			} else {
				time = gpspoints2[i-1].getTime() + (int)((gpspoints[i].getTime() - gpspoints[i - 1].getTime()) * (0.95 + (0.3 * random.nextDouble())));
				gpspoints2[i] = new GPSPoint(time, gpspoints[i].getLatitude(),
						gpspoints[i].getLongitude(), gpspoints[i].getElevation());
				if (maxSpeed2 < GPSUtils.speed(gpspoints2[i - 1], gpspoints2[i])) {
					maxSpeed2 = GPSUtils.speed(gpspoints2[i - 1], gpspoints2[i]);
				}
			}
		}
		return gpspoints2;
	}

}
