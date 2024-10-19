package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;
import no.hvl.dat100ptc.TODO;

public class ShowSpeed extends EasyGraphics {

	private static int MARGIN = 50;
	private static int BARHEIGHT = 100;

	private GPSComputer gpscomputer;

	public ShowSpeed() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Speed profile", 2 * MARGIN + 2 * gpscomputer.speeds().length, 2 * MARGIN + BARHEIGHT);

		showSpeedProfile(MARGIN + BARHEIGHT);
	}

	public void showSpeedProfile(int ybase) {

		int x = MARGIN, y;
		GPSPoint[] gpspoints = gpscomputer.getGPSPoints();
		int averageSpeed = (int) (gpscomputer.averageSpeed() * 3.6);
		int speed;
		
		showYAxisValues();

		drawLine(x - 1, ybase + 1, x - 1, MARGIN);
		drawLine(x - 1, ybase + 1, x + 2 * gpscomputer.speeds().length, ybase + 1);

		setColor(0, 255, 0);
		drawLine(x, ybase - averageSpeed, MARGIN + 2 * gpscomputer.speeds().length, ybase - averageSpeed);

		setColor(0, 0, 255);
		for (int i = 0; i < gpspoints.length - 1; i++) {
			speed = (int) (GPSUtils.speed(gpspoints[i], gpspoints[i + 1]) * 3.6);
			drawLine(x, ybase, x, ybase - speed);
			x += 2;
		}

		// TODO

	}
	//Oppgave 6b)
	public void showYAxisValues() {
		int textMargin = 20;
		int x = MARGIN - textMargin;
		int y = MARGIN + BARHEIGHT;
		int increment = 10;

		setColor(0, 0, 0);
		setFont("Courier", 8);
		
		drawString("km/t ", x, MARGIN - 10);
		
		for (int speed = 0; speed <= BARHEIGHT; speed += increment) {
			drawString(Integer.toString(speed) + " ", x, y);
			y -= increment;
		}
	}
}
