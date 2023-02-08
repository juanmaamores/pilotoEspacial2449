package gameObjects;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import math.Vector2D;
import states.GameState;


public abstract class MovingObject extends GameObject{
	
	protected Vector2D velocity;
	protected AffineTransform at;
	protected double angle;
	protected double maxVel;
	protected int width;
	protected int height;
	protected GameState gameState;
	protected boolean Dead;
	
	private static final int BUFFER_SIZE = 4096;
    private static AudioFormat audioFormat;
    private static AudioInputStream audioInputStream;
    private static SourceDataLine sourceDataLine;
	
	public MovingObject(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
		
		super(position, texture);
		this.velocity = velocity;
		this.maxVel = maxVel;
		this.gameState = gameState;
		width = texture.getWidth();
		height = texture.getHeight();
		angle = 0;
		Dead = false;
		
		//esto sirve para poder solapar sonidos.
		 try {
	            File explosionFile = new File("C:.//res//sounds/explosion.wav");
	            audioInputStream = AudioSystem.getAudioInputStream(explosionFile);
	            audioFormat = audioInputStream.getFormat();
	            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
	            sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
	            sourceDataLine.open(audioFormat);
	            sourceDataLine.start();
	        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	            e.printStackTrace();
	        }
		
	}
	
	protected void collidesWith(){
		
		ArrayList<MovingObject> movingObjects = gameState.getMovingObjects(); 
		
		for(int i = 0; i < movingObjects.size(); i++){
			
			MovingObject m  = movingObjects.get(i);
			
			if(m.equals(this))
				continue;
			
			double distance = m.getCenter().subtract(getCenter()).getMagnitude();
			
			if(distance < m.width/2 + width/2 && movingObjects.contains(this) && !m.Dead && !Dead){
				objectCollision(m, this);
			}
		}
	}
	
	private void objectCollision(MovingObject a, MovingObject b){
		
		if(a instanceof Player && ((Player)a).isSpawning()) {
			return;
		}
		
		if(b instanceof Player && ((Player)b).isSpawning()) {
			return;
		}
			
		if(a instanceof Player && b instanceof PowerUp){
			((PowerUp)b).executeAction();
			b.Destroy();
			
		} else if(a instanceof PowerUp && b instanceof Player){
			((PowerUp)a).executeAction();
			a.Destroy();
							
		} else if((a instanceof Laser || a instanceof Player) && (b instanceof WarMachine|| b instanceof Laser || b instanceof Player || b instanceof Meteor || b instanceof Ufo)){
				
			gameState.playExplosion(getCenter());
			a.Destroy();
			b.Destroy();
				
		} else if ((b instanceof Laser || b instanceof Player) && (a instanceof WarMachine || a instanceof Laser || a instanceof Player || a instanceof Meteor || a instanceof Ufo)) {
			
			gameState.playExplosion(getCenter());
			a.Destroy();
			b.Destroy();
			
		} /*else if((a instanceof Player) && (b instanceof Laser || b instanceof Meteor || b instanceof Ufo)){
			
		gameState.playExplosion(getCenter());
		a.Destroy();
		b.Destroy();
			
		} else if ((b instanceof Player) && (a instanceof Laser || a instanceof Meteor || a instanceof Ufo)) {
		
		gameState.playExplosion(getCenter());
		a.Destroy();
		b.Destroy();
		}*/
		
	}
	
	protected void Destroy(){
		
		Dead = true;	
		
		if(!(this instanceof Laser || this instanceof PowerUp)) {
		
			//esto sirve para poder solapar sonidos.
			new Thread(() -> {
	            try {
	                byte[] data = new byte[BUFFER_SIZE];
	                int bytesRead;
	                while ((bytesRead = audioInputStream.read(data, 0, data.length)) != -1) {
	                    sourceDataLine.write(data, 0, bytesRead);
	                }
	                sourceDataLine.drain();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }).start();
		}	 	
	}
	
	protected Vector2D getCenter(){
		return new Vector2D(position.getX() + width/2, position.getY() + height/2);
	}
	
	public boolean isDead() {
		return Dead;
	}
	
}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
