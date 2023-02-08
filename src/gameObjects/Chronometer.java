package gameObjects;

public class Chronometer {
	private long delta, lastTime, time;
	private boolean running;
	
	public Chronometer(){ // constructor por defecto
		delta = 0;
		lastTime = System.currentTimeMillis();
		running = false;
	}
	
	public void run(long time){
		running = true;
		this.time = time;
	}
	
	public void update(){	
		if(running)
			delta += System.currentTimeMillis() - lastTime;
		if(delta >= time){
			running = false;
			delta = 0;
		}
		
		lastTime = System.currentTimeMillis();
	}
	
	public boolean isRunning(){
		return running;
	}
	
}

/*
 ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
 EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
