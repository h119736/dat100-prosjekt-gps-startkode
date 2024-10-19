package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSDataConverter {

	private static final int TIME_STARTINDEX = 11;
	private static final int TIME_ENDINDEX = 13;
	private static final int MINUTT_STARTINDEX = 14;
	private static final int MINUTT_ENDINDEX = 16;
	private static final int SEKUND_STARTINDEX = 17;
	private static final int SEKUND_ENDINDEX = 19;

	public static int toSeconds(String timestr) {
		int secs;
		int hr, min, sec;
		
		hr = Integer.parseInt(timestr.substring(TIME_STARTINDEX,TIME_ENDINDEX));
		min = Integer.parseInt(timestr.substring(MINUTT_STARTINDEX,MINUTT_ENDINDEX));
		sec = Integer.parseInt(timestr.substring(SEKUND_STARTINDEX,SEKUND_ENDINDEX));
		
		secs = hr * GPSUtils.SEKUNDER_PER_TIME + min * GPSUtils.SEKUNDER_PER_MINUTT + sec;
		
		return secs;
		// TODO
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {
		int sec = toSeconds(timeStr);
		double latitude = Double.parseDouble(latitudeStr);
		double longitude = Double.parseDouble(longitudeStr);
		double elevation = Double.parseDouble(elevationStr);
		
		GPSPoint gpspoint = new GPSPoint(sec, latitude, longitude, elevation);
		
		return gpspoint;
		// TODO 		
	}
	
}
