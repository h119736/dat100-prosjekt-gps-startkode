package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int antall) {
		gpspoints = new GPSPoint[antall];
		antall = 0;
		// TODO
	}

	public GPSPoint[] getGPSPoints() {
		return gpspoints;
	}

	protected boolean insertGPS(GPSPoint gpspoint) {
		boolean inserted = false;

		if (antall < gpspoints.length) {
			gpspoints[antall++] = gpspoint;
			inserted = true;
		}

		return inserted;
		// TODO
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {
		GPSPoint gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation);
		return insertGPS(gpspoint);
		// TODO
	}

	public void print() {
		System.out.println("====== GPS Data - START ======");
		for (int i = 0; i < antall; i++) {
			System.out.print(gpspoints[i].toString());
		}
		System.out.println("====== GPS Data - SLUTT ======");
		// TODO
	}
}