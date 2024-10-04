package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {

	private static int TIME_STARTINDEX = 11;

	public static int toSeconds(String timestr) {

		int secs;
		int hr, min, sec;

		hr = Integer.parseInt(timestr.substring(11, 13));
		min = Integer.parseInt(timestr.substring(14, 16));
		sec = Integer.parseInt(timestr.substring(17, 19));

		secs = hr * 3600 + min * 60 + sec;

		return secs;

	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {

		
		
		int time;
		double latitude;
		double longitude;
		double elevation;
		
		time = toSeconds(timeStr);
		latitude = Double.parseDouble(latitudeStr);
		longitude = Double.parseDouble(longitudeStr);
		elevation = Double.parseDouble(elevationStr);
		
		GPSPoint gpspoint = new GPSPoint(time, latitude, longitude, elevation);

		return gpspoint;
		
	}

}
