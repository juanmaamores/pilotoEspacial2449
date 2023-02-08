package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.lang.Math;

import gameObjects.Chronometer;
import gameObjects.Constants;
import gameObjects.Message;
import gameObjects.Meteor;
import gameObjects.MovingObject;
import gameObjects.Player;
import gameObjects.PowerUp;
import gameObjects.Size;
import gameObjects.Ufo;
import gameObjects.WarMachine;
import graphics.Animation;
import graphics.Assets;
import graphics.Loader;
import graphics.Sound;
import graphics.Text;
import math.Vector2D;

public class GameState extends State{
	
	public static Vector2D PLAYER_START_POSITION = new Vector2D(Constants.WIDTH/2 - Assets.playerSimple.getWidth()/2, Constants.HEIGHT/2 - Assets.playerSimple.getHeight()/2);
	public static Vector2D SCREEN_CENTER = new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2);
	public static int waves = 1;
	private Vector2D randomPosition;
	private Player player;
	private ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();
	private ArrayList<Animation> explosions = new ArrayList<Animation>();
	private ArrayList<Message> messages = new ArrayList<Message>();
	private static int score = 0;
	private static int lives = 3;
	private int meteors, ufoCounter = 0, ufoPerWave = 1, warMachineSpawn = 1, warMachineCounter = 3;
	private boolean gameOver, warMachinePhase = false;
	private double playerMaxVel = 6.0; 
	private static Sound ufoMoving, backgroundMusic, playerLoose, warMachines;
	private Chronometer gameOverTimer, ufoSpawner, powerUp;
	private Font font;
	
	
	public GameState(){
		
		waves = 1;
		playerLoose = new Sound(Assets.playerLoose);
		powerUp = new Chronometer();
		powerUp.run(Constants.POWER_UP_SPAWN_RATE);
		
		if(shopState.propulsorB) {
			playerMaxVel = 8.0;
		}
		if(!shopState.doubleCanonB) {
			player = new Player(PLAYER_START_POSITION, new Vector2D(), playerMaxVel, Assets.playerSimple, this);
		} else {
			player = new Player(PLAYER_START_POSITION, new Vector2D(), playerMaxVel, Assets.playerDouble, this);
		}
		
		gameOverTimer = new Chronometer();
		gameOver = false;
		font = Loader.loadFont("/fonts/futureFont.ttf", 60);
		movingObjects.add(player);
		meteors = 1;
		ufoMoving = new Sound(Assets.ufoBackground);
		ufoMoving.changeVolume(-5);
		backgroundMusic = new Sound(Assets.backgroundMusic);
		warMachines = new Sound(Assets.warMachines);
		
		if(!MenuState.muted) {
			backgroundMusic.loop();
		}
		backgroundMusic.changeVolume(-10);
		ufoSpawner = new Chronometer();
		ufoSpawner.run(Constants.UFO_SPAWN_RATE);
		startWave();
		
	}
	
	public static int getWaves() {
		return waves-1;
	}
	
	
	public void addScore(int value, Vector2D position) {
		
		score += value;
		messages.add(new Message(position, true, "+$ "+value, Color.GREEN, false, Assets.fontMed));
	}
	
	public void sustractScore(int value, Vector2D position) {
		if(value > score) {
			score = 0;
		} else {
			score -= value;
		}
		messages.add(new Message(position, true, "-$ "+value, Color.RED, false, Assets.fontBig));
	}
	
	
	public void divideMeteor(Meteor meteor){
		
		Size size = meteor.getSize();
		
		BufferedImage[] textures = size.textures;
		
		Size newSize = null;
		
		switch(size){
		case BIG:
			newSize =  Size.MED;
			break;
		case MED:
			newSize = Size.SMALL;
			break;
		case SMALL:
			newSize = Size.TINY;
			break;
		default:
			return;
		}
		
		for(int i = 0; i < size.quantity; i++){
			movingObjects.add(new Meteor(meteor.getPosition(), new Vector2D(0, 1).setDirection(Math.random()*Math.PI*2), Constants.METEOR_VEL*Math.random() + 1, textures[(int)(Math.random()*textures.length)], this, newSize));
		}
	}
	
	
	private void startWave(){
		
		warMachineSpawn = 1;
		
		messages.add(new Message(new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2), false, "Oleada "+waves, Color.WHITE, true, Assets.fontBig));
		
		double x, y;
		
		for(int i = 0; i < meteors; i++){
			 
			x = i % 2 == 0 ? Math.random()*Constants.WIDTH : 0;
			y = i % 2 == 0 ? 0 : Math.random()*Constants.HEIGHT;
			BufferedImage texture = Assets.bigs[(int)(Math.random()*Assets.bigs.length)];
			movingObjects.add(new Meteor(new Vector2D(x, y), new Vector2D(0, 1).setDirection(Math.random()*Math.PI*2), Constants.METEOR_VEL*Math.random() + 1, texture, this, Size.BIG));
			
		}
		meteors ++;
		
		if(ufoPerWave >= 4) {
			ufoPerWave = 4;
		}
		
		for(int i = 0; i < ufoPerWave; i++) {
			spawnUfo();
		}
		
		waves++;
		ufoPerWave = waves;
		
	}
	
	public void playExplosion(Vector2D position){
		
		explosions.add(new Animation(Assets.exp, 50, position.subtract(new Vector2D(Assets.exp[0].getWidth()/2, Assets.exp[0].getHeight()/2))));
	}
	
	public void subtractUfoCounter(int u) {
		ufoCounter = ufoCounter - u;
	}
	
	public void subtractWarMachineCounter(int w) {
		warMachineCounter = warMachineCounter - w;
	}
	
	public static void stopUfoSound() {
		ufoMoving.stop();
	}
	
	public static void stopWarMachinesSound() {
		warMachines.stop();
	}
	
	private void spawnUfo(){
		
		ufoCounter++;
		ufoMoving.loop();
		
		int rand = (int) (Math.random()*2);
		double x = rand == 0 ? (Math.random()*Constants.WIDTH): Constants.WIDTH;
		double y = rand == 0 ? Constants.HEIGHT : (Math.random()*Constants.HEIGHT);
		
		ArrayList<Vector2D> path = new ArrayList<Vector2D>();
		
		double posX, posY;
		//cuadrante 1
		posX = Math.random()*Constants.WIDTH/2;
		posY = Math.random()*Constants.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		//cuadrante 2
		posX = Math.random()*(Constants.WIDTH/2) + Constants.WIDTH/2;
		posY = Math.random()*Constants.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		//cuadrante 3 
		posX = Math.random()*Constants.WIDTH/2;
		posY = Math.random()*(Constants.HEIGHT/2) + Constants.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		//cuadrante 4
		posX = Math.random()*(Constants.WIDTH/2) + Constants.WIDTH/2;
		posY = Math.random()*(Constants.HEIGHT/2) + Constants.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		movingObjects.add(new Ufo(new Vector2D(x, y), new Vector2D(), Constants.UFO_MAX_VEL, Assets.ufo, path, this));
		
	}
	
	private void spawnWarMachine(){
		
		warMachines.play();
	
		warMachineSpawn--;
		double xWM1 = (Constants.WIDTH/2)-50  + 400;
		ArrayList<Vector2D> path1 = new ArrayList<Vector2D>();
		path1.add(new Vector2D(xWM1,768));
		movingObjects.add(new WarMachine(new Vector2D(xWM1, 0), new Vector2D(), Constants.WAR_M_MAX_VEL, Assets.warMachine, path1, this));
		
		double xWM2 = (Constants.WIDTH/2)-50 ;
		ArrayList<Vector2D> path2 = new ArrayList<Vector2D>();
		path2.add(new Vector2D(xWM2,768));
		movingObjects.add(new WarMachine(new Vector2D(xWM2, 20), new Vector2D(), Constants.WAR_M_MAX_VEL, Assets.warMachine, path2, this));
		
		double xWM3 = (Constants.WIDTH/2)-50 - 400;
		ArrayList<Vector2D> path3 = new ArrayList<Vector2D>();
		path3.add(new Vector2D(xWM3,768));
		movingObjects.add(new WarMachine(new Vector2D(xWM3, 0), new Vector2D(), Constants.WAR_M_MAX_VEL, Assets.warMachine, path3, this));

	}
	
	public void spawnLifePowerUp(Vector2D position) {
		movingObjects.add(new PowerUp(position, Assets.lifePowerUp, this, true));
	}
	
	private void spawnMoneyPowerUp() {
		movingObjects.add(new PowerUp(randomPosition, Assets.moneyPowerUp, this, false));
	}

	public void update(){
				
		if(!powerUp.isRunning()) {
						
			double powerUpx = Math.random()*Constants.WIDTH;
			double powerUpy = Math.random()*Constants.HEIGHT;
			randomPosition = new Vector2D(powerUpx,powerUpy);
			spawnMoneyPowerUp();
			powerUp.run(Constants.POWER_UP_SPAWN_RATE);
		}
		
		powerUp.update();
		
		
		for(int i = 0; i < movingObjects.size(); i++) {
			
			MovingObject mo = movingObjects.get(i);
			mo.update();
			
			if(mo.isDead()) {
				movingObjects.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < explosions.size(); i++){
			Animation anim = explosions.get(i);
			anim.update();
			if(!anim.isRunning()){
				explosions.remove(i);
			}
			
		}
		
		if(gameOver && !gameOverTimer.isRunning()){
			
			State.changeState(new GameOverState());
		}
		
		if(ufoCounter == 0) {
			ufoMoving.stop();
		}	
		
		gameOverTimer.update();
		
		ufoSpawner.update();
		
		if(!ufoSpawner.isRunning()) {
			ufoSpawner.run(Constants.UFO_SPAWN_RATE);
			if(!warMachinePhase) {
				spawnUfo();
			}
		}
		
		for(int i = 0; i < movingObjects.size(); i++) { // esto podría traer el error del sonido del ufo ??
			if(movingObjects.get(i) instanceof Meteor) {
				return; // si de todos los objetos movibles aun hay un meteorito, retorna.
			} 
		}
		
		if(warMachinePhase == false) {
			warMachinePhase = true;
		}
		
		if(warMachineCounter == 0) {
			warMachinePhase = false;
			warMachineCounter = 3;
			warMachines.stop();
		}
		
		//programar lo que ejecute la fase de los war machine
		if(!warMachinePhase) {
			startWave();
			warMachines.stop();
			
		} else {
			for(int i = 0; i < warMachineSpawn; i++) {
				spawnWarMachine();
			}
		}
		
	}
	
	public void draw(Graphics g){
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.drawImage(Assets.gamePlayBackground, 0, 0, Constants.WIDTH, Constants.HEIGHT, null);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if(gameOver) {
			Text.drawText(g2d, "GAME OVER", SCREEN_CENTER, gameOver, Color.RED, font);
		}
		
		for(int i = 0; i < messages.size(); i++) {
			messages.get(i).draw(g2d);
			if(messages.get(i).isDead())
				messages.remove(i);
		}
		
		for(int i = 0; i < movingObjects.size(); i++)
			movingObjects.get(i).draw(g);
		
		for(int i = 0; i < explosions.size(); i++){
			Animation anim = explosions.get(i);
			g2d.drawImage(anim.getCurrentFrame(), (int)anim.getPosition().getX(), (int)anim.getPosition().getY(), null);
			
		}
		drawScore(g);
		drawLives(g);
		
	}
	
	private void drawScore(Graphics g) {
		
		Vector2D pos = new Vector2D(1200, 25);
		String scoreToString = Integer.toString(score);
		
		for(int i = 0; i < scoreToString.length(); i++) {
			
			g.drawImage(Assets.numbers[Integer.parseInt(scoreToString.substring(i, i + 1))], (int)pos.getX(), (int)pos.getY(), null);
			pos.setX(pos.getX() + 20);
			
		}
		
	}
	
	private void drawLives(Graphics g){
		
		Vector2D livePosition = new Vector2D(25, 25);
		g.drawImage(Assets.life, (int)livePosition.getX(), (int)livePosition.getY(), null);
		g.drawImage(Assets.numbers[10], (int)livePosition.getX() + 40, (int)livePosition.getY() + 5, null);
		String livesToString = Integer.toString(lives);
		Vector2D pos = new Vector2D(livePosition.getX(), livePosition.getY());
		
		for(int i = 0; i < livesToString.length(); i ++) {
			
			int number = Integer.parseInt(livesToString.substring(i, i+1));		
			g.drawImage(Assets.numbers[number], (int)pos.getX() + 60, (int)pos.getY() + 5, null);
			pos.setX(pos.getX() + 20);
		}
	}
	
	public ArrayList<MovingObject> getMovingObjects() {
		
		return movingObjects;
	}
	
	public ArrayList<Message> getMessages() {
		
		return messages;
	}
	
	public Player getPlayer() {
		
		return player;
	}
	
	public boolean substractLife() {
			lives--;
			return lives > 0;
	}
	
	public static void addLife() {
		lives++;
	}
	
	public void gameOver() {
		
		gameOver = true;
		warMachines.stop();
		ufoMoving.stop();
		backgroundMusic.stop();
		playerLoose.play();
		PLAYER_START_POSITION = new Vector2D(Constants.WIDTH/2 - Assets.playerSimple.getWidth()/2, Constants.HEIGHT/2 - Assets.playerSimple.getHeight()/2);
		gameOverTimer.run(Constants.GAME_OVER_TIME);
		
	}
	
	public static int getScore() {
		return score;
	}
	
	public static void setScore(int s) {
		score = s;
	}
	
}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
