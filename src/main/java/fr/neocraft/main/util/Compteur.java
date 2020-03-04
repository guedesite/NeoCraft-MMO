package fr.neocraft.main.util;

public class Compteur {

	private static long startTime;
	
	public static void startCompteur() {
		startTime = System.currentTimeMillis();
	}
	
	public static long endCompteur() {
		 return System.currentTimeMillis() - startTime;
	}
	
}
