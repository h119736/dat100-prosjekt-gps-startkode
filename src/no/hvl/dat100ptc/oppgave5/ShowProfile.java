package no.hvl.dat100ptc.oppgave5;

import no.hvl.dat100ptc.TODO;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import javax.swing.JOptionPane;

public class ShowProfile extends EasyGraphics {

	private static final int MARGIN = 50;		// margin on the sides 
	
	private static final int MAXBARHEIGHT = 500; // assume no height above 500 meters
	
	private GPSPoint[] gpspoints;

	public ShowProfile() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn (uten .csv): ");
		GPSComputer gpscomputer =  new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		int N = gpspoints.length; // number of data points

		makeWindow("Height profile", 2 * MARGIN + 3 * N, 2 * MARGIN + MAXBARHEIGHT);

		// top margin + height of drawing area
		showHeightProfile(MARGIN + MAXBARHEIGHT);

	}

	public void showHeightProfile(int ybase) {
		
		int x = MARGIN; // første høyde skal tegnes ved MARGIN
		int y;
		double skaleringsfaktor = Double.parseDouble(getText("Skaleringsfaktor: "));
		int sec;
		
		showYAxisValues();
		drawLine(x-1, ybase+1, x-1, MARGIN);
		drawLine(x-1, ybase+1, x + 3 * gpspoints.length + 1, ybase+1);
		setColor(0, 0, 255);
		
		for (int i = 0; i < gpspoints.length; i++) {
			if ((int)gpspoints[i].getElevation() < 0) {
				y = 0;
			}else {
				y = (int)gpspoints[i].getElevation();
			}
			drawLine(x, ybase, x, ybase - y);
			x += 3;
			if (i != gpspoints.length-1) {
				sec = gpspoints[i+1].getTime() - gpspoints[i].getTime();
				pause((int)(sec/skaleringsfaktor*1000));
			}
		}
		
		// TODO
	}
	
	//Oppgave 6b)
	public void showYAxisValues() {
		int textMargin = 30;
		int x = MARGIN - textMargin;
		int y = MARGIN + MAXBARHEIGHT;
		int increment = 20;

		setColor(0, 0, 0);
		setFont("Courier", 12);
		drawString("m ", x, MARGIN - 20);
		
		for (int elevation = 0; elevation <= MAXBARHEIGHT; elevation += increment) {
			drawString(Integer.toString(elevation), x, y);
			y -= increment;
		}
	}

}
