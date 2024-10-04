package no.hvl.dat100ptc.oppgave1;

public class Main {
	
	
	public static void main(String[] args) {
		GPSPoint punkt = new GPSPoint (1, 2.0, 3.0, 5.0);
		
	
		System.out.println("Tidspunkt: " + punkt.getTime());
		punkt.setTime(2);
	
		System.out.println(punkt.toString());
		
		
	}

}
