package gameObjects;

public class Constants {
	
	// frame dimensions
	
	public static final int WIDTH = 1366;
	public static final int HEIGHT = 768;
	
	// player properties
	
	public static final int FIRERATE = 500; // original 300
	public static final double DELTAANGLE = 0.07; //original 0.1
	//public static final double ACC = 0.2; // original 0.2
	//public static final double PLAYER_MAX_VEL = 6.0; // original 6.0
	public static final long FLICKER_TIME = 200;
	public static final long SPAWNING_TIME = 2500;
	public static final long GAME_OVER_TIME = 4000;
	
	// Laser properties
	
	public static final double LASER_VEL = 14.0;
	
	// Meteor properties
	
	public static final double METEOR_VEL = 0.6;
	public static final int METEOR_SCORE = 90;
	
	// Ufo properties
	
	public static final int NODE_RADIUS = 160;
	public static final double UFO_MASS = 60;
	public static final int UFO_MAX_VEL = 3;
	public static long UFO_FIRE_RATE = 2000;
	public static double UFO_ANGLE_RANGE = Math.PI / 2;
	public static final int UFO_SCORE = 499;
	public static final long UFO_SPAWN_RATE = 15000; //15k
	
	//loading bar
	
	public static final int LOADING_BAR_WIDTH = 500;
	public static final int LOADING_BAR_HEIGHT = 50;
	
	//powerUp
	public static long POWER_UP_DURATION = 7000;
	public static long POWER_UP_SPAWN_RATE = 30000;
	
	//war machine properties
	
	public static final double WAR_M_MAX_VEL = 1.5;
	public static long WAR_M_FIRE_RATE = 800;
	public static final int WAR_M_SCORE = 999;
	public static final int WAR_M_SCORE_LOOSE = 9999;
	public static final int WAR_M_LASER_SPEED = 8;
	
}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/