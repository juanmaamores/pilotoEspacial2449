package graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

public class Assets {
	
	public static boolean loaded = false;
	public static float count = 0;
	public static float MAX_COUNT = 78;
	
	//backgrounds
	public static BufferedImage menuBackground;
	public static BufferedImage gamePlayBackground; 
	public static BufferedImage gameOverBackground;
	public static BufferedImage shopBackground;
	public static BufferedImage loreBackground;
	
	// player
	public static BufferedImage playerSimple;
	public static BufferedImage playerDouble;
	
	// effects
	public static BufferedImage propulsionDefault;
	public static BufferedImage propulsionBoosted;
	
	// explosion
	public static BufferedImage[] exp = new BufferedImage[11];
	
	// lasers
	public static BufferedImage blueLaser, greenLaser, redLaser;
	
	// Meteors
	public static BufferedImage[] bigs = new BufferedImage[4];
	public static BufferedImage[] meds = new BufferedImage[2];
	public static BufferedImage[] smalls = new BufferedImage[2];
	public static BufferedImage[] tinies = new BufferedImage[2];
	
	// ufo
	public static BufferedImage ufo;
	
	// numbers
	public static BufferedImage[] numbers = new BufferedImage[11];
	
	// life 
	public static BufferedImage life;
	
	// fonts
	public static Font fontBig;
	public static Font fontMed;
	
	// ui
	public static BufferedImage blueBtn;
	public static BufferedImage blueBtnSmall;
	public static BufferedImage blueBtnSmallInfo;
	public static BufferedImage greyBtn;
	public static BufferedImage greyBtnSmall;
	public static BufferedImage greyBtnPurchased;
	public static BufferedImage greyBtnSmallInfo;
	
	//powerUps
	public static BufferedImage lifePowerUp;
	public static BufferedImage moneyPowerUp;
	
	//warMachines
	public static BufferedImage warMachine;
	
	//sounds
	public static Clip backgroundMusic, gameOverMusic, shopStateMusic, loreStateMusic, menuStateMusic;
	public static Clip explosion, playerLoose, playerShoot /*playerPropulsion*/, propulsionBoostedSound;
	public static Clip announcement, CashRegister, errorSound, powerUpSound, warMachines;
	public static Clip powerUpPop, ufoShoot, ufoBackground, buttonClick, propulsionDefaultSound;
	
	public static void init() {
		
		menuBackground = loadImage("/backgrounds/menuBackground.png");
		gamePlayBackground = loadImage("/backgrounds/gamePlayBackground.png");
		gameOverBackground = loadImage("/backgrounds/gameOverBackground.png");
		shopBackground = loadImage("/backgrounds/shopBackground.png");
		loreBackground = loadImage("/backgrounds/loreBackground.png");
		playerSimple = loadImage("/ships/player.png");
		playerDouble = loadImage("/ships/player_double.png");
		propulsionDefault = loadImage("/effects/fire.png");
		propulsionBoosted = loadImage("/effects/firePropulsor.png");
		blueLaser = loadImage("/lasers/laserBlue01.png");
		greenLaser = loadImage("/lasers/laserGreen11.png");
		redLaser = loadImage("/lasers/laserRed01.png");
		ufo = loadImage("/ships/ufo.png");
		warMachine = loadImage("/ships/warMachine1.png");
		life = loadImage("/others/life.png");
		fontBig = loadFont("/fonts/futureFont.ttf", 42);
		fontMed = loadFont("/fonts/futureFont.ttf", 28);
		
		for(int i = 0; i < bigs.length; i++)
			bigs[i] = loadImage("/meteors/big"+(i+1)+".png");
		
		for(int i = 0; i < meds.length; i++)
			meds[i] = loadImage("/meteors/med"+(i+1)+".png");
		
		for(int i = 0; i < smalls.length; i++)
			smalls[i] = loadImage("/meteors/small"+(i+1)+".png");
		
		for(int i = 0; i < tinies.length; i++)
			tinies[i] = loadImage("/meteors/tiny"+(i+1)+".png");
		
		for(int i = 0; i < exp.length; i++)
			exp[i] = loadImage("/explosion/"+i+".png");
		
		for(int i = 0; i < numbers.length; i++)
			numbers[i] = loadImage("/numbers/"+i+".png");
		
		//buttons
		blueBtn = loadImage("/ui/blue_button.png");
		blueBtnSmall = loadImage("/ui/blue_button_small.png");
		blueBtnSmallInfo = loadImage("/ui/blue_button_info.png");
		greyBtn = loadImage("/ui/grey_button.png");
		greyBtnSmall = loadImage("/ui/grey_button_small.png");
		greyBtnPurchased = loadImage("/ui/grey_button_purchased.png");
		greyBtnSmallInfo = loadImage("/ui/grey_button_info.png");
		
		//powerUps
		lifePowerUp = loadImage("/others/lifePowerUp.png");
		moneyPowerUp = loadImage("/others/moneyPowerUp.png");
		
		//warMachines
		warMachines = loadSound("/sounds/warMachinesSound.wav");
		
		// sounds
		explosion = loadSound("/sounds/explosion.wav");
		playerLoose = loadSound("/sounds/playerLoose.wav");
		playerShoot = loadSound("/sounds/playerShoot.wav");
		ufoShoot = loadSound("/sounds/ufoShoot.wav");
		ufoBackground = loadSound("/sounds/ufoBackground.wav");
		buttonClick = loadSound("/sounds/buttonClick.wav");
		CashRegister = loadSound("/sounds/buy.wav");
		errorSound = loadSound("/sounds/noBuy.wav");
		announcement = loadSound("/sounds/announcement.wav");
		propulsionDefaultSound = loadSound("/sounds/propulsionBoostedSound.wav");
		propulsionBoostedSound = loadSound("/sounds/propulsionBoostedSound.wav");
		powerUpSound = loadSound("/sounds/powerUp.wav");
		powerUpPop = loadSound("/sounds/pop.wav");
		
		//music
		menuStateMusic = loadSound("/sounds/menuState.wav");
		gameOverMusic = loadSound("/sounds/gameOverState.wav");
		shopStateMusic = loadSound("/sounds/shopTheme.wav");
		loreStateMusic = loadSound("/sounds/loreMusic.wav");
		backgroundMusic = loadSound("/sounds/backgroundMusic.wav");
		
		loaded = true;
		
	}
	
	public static BufferedImage loadImage(String path) {
		count++;
		return Loader.ImageLoader(path);
	}
	
	public static Clip loadSound(String path) {
		count++;
		return Loader.loadSound(path);
	}
	
	public static Font loadFont(String path, int size) {
		count++;
		return Loader.loadFont(path, size);
	}
	
}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
