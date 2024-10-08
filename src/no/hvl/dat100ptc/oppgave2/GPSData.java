package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int antall) {
		int n = antall;

		gpspoints = new GPSPoint[n];

		antall = 0;

	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	protected boolean insertGPS(GPSPoint gpspoint) {

		boolean inserted = false;

		if (antall < gpspoints.length) {
			gpspoints[antall] = gpspoint;
			antall++;

			inserted = true;
			return inserted;
		} else {
			return inserted;
		}

	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

		// Omgjør tekst til tall
		GPSPoint gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation);

		

		// bruk insert metode for å putte objektet i tabellen.

		return insertGPS(gpspoint); //metodekallet virker ikke...
		

	}

	public void print() {

		/*
		 * public void print() som skriver ut GPS data som finnes i gpspoints-tabellen
		 * på følgende formen
		 * 
		 * ====== GPS Data - START ====== 1 (1.0,2.0) 3.0 2 (4.0,5.0) 6.0 3 (7.0,8.0)
		 * 9.0 ====== GPS Data - SLUTT ====== Hint: bruk løkke og toString-metoden på
		 * GPSPoint-objekt.
		 * 
		 * Metodene som allerede er implementert i klassen GPSDataFileReader.java leser
		 * - linje for linje - i GPS datafilen og lagrer data i tabellen ved å bruke
		 * insert-metoden som ble implementert ovenfor.
		 * 
		 * Dette betyr at punktene i gpspoint-tabellen svarer til ruten som er
		 * representert i GPS datafilen og gpspoint-referansetabellen vil ha samme
		 * lengde som antallet av GPS punkter som er leste inn. Det siste betyr videre
		 * at hvert element i gpspoint-tabellen vil peke på et objekt. dvs. tabellen er
		 * full.
		 * 
		 */
		
		System.out.println("====== GPS Data - START ======");
		
		for(int i = 0; i < antall; i++) { 
			System.out.println(gpspoints[i].toString());
		}
		
		System.out.println("====== GPS Data - SLUTT ======");
	}
}
