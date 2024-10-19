package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import no.hvl.dat100ptc.TODO;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 600;
	private static int MAPYSIZE = 400;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	private double maxSpeed;

	private double minlon, minlat, maxlon, maxlat;

	private double xstep, ystep;

	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
		maxSpeed = gpscomputer.maxSpeed();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));

		xstep = scale(MAPXSIZE, minlon, maxlon);
		ystep = scale(MAPYSIZE, minlat, maxlat);
		System.out.println(xstep);
		System.out.println(ystep);

		showRouteMap(MARGIN + MAPYSIZE);

		replayRoute(MARGIN + MAPYSIZE);

		showStatistics();
	}

	public double scale(int maxsize, double minval, double maxval) {
		double step = maxsize / (Math.abs(maxval - minval));

		return step;
	}

	public void showRouteMap(int ybase) {
		int x, y, x2, y2;
		int circleRadius = 2;
		setColor(0, 255, 0);
		
		for (int i = 0; i < gpspoints.length; i++) {
			x = MAPXSIZE + MARGIN - (int) ((maxlon - gpspoints[i].getLongitude()) * xstep);
			y = MAPYSIZE - (int) ((maxlat - gpspoints[i].getLatitude()) * ystep);
			fillCircle(x, ybase - y, circleRadius);
			if (i != gpspoints.length - 1) {
				x2 = MAPXSIZE + MARGIN - (int) ((maxlon - gpspoints[i + 1].getLongitude()) * xstep);
				y2 = MAPYSIZE - (int) ((maxlat - gpspoints[i + 1].getLatitude()) * ystep);
				drawLine(x, ybase - y, x2, ybase - y2);
			}
		}
		// TODO
	}

	public void showStatistics() {
		int TEXTDISTANCE = 15;
		int y = TEXTDISTANCE;

		setColor(0, 0, 0);
		setFont("Courier", 12);

		String[] tekst = gpscomputer.statisticsStringArray();

		for (String txt : tekst) {
			drawString(txt, TEXTDISTANCE, y);
			y += TEXTDISTANCE;
		}
		// TODO
	}

	public void replayRoute(int ybase) {
		int x, y;
		int circleRadius = 5;
		double speed;
		setColor(0, 0, 255);
		
		x = MAPXSIZE + MARGIN - (int) ((maxlon - gpspoints[0].getLongitude()) * xstep);
		y = MAPYSIZE - (int) ((maxlat - gpspoints[0].getLatitude()) * ystep);
		int sirkel = fillCircle(x, ybase - y, circleRadius);
		
		speed = GPSUtils.speed(gpspoints[0], gpspoints[1]);
		setSpeed(1 + (int)((speed / maxSpeed) * 9));
		for (int i = 1; i < gpspoints.length; i++) {
			x = MAPXSIZE + MARGIN - (int) ((maxlon - gpspoints[i].getLongitude()) * xstep);
			y = MAPYSIZE - (int) ((maxlat - gpspoints[i].getLatitude()) * ystep);
			moveCircle(sirkel, x, ybase - y);
			if (i != gpspoints.length - 1) {
				speed = GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
				setSpeed(1 + (int)((speed / maxSpeed) * 9));
			}
		}
		// TODO
	}

}
