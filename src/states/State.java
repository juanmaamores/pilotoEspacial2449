package states;

import java.awt.Graphics;

public abstract class State {
	
	private static State currentState = null;
	
	public static State getCurrentState() {
		return currentState;
	}

	public static void changeState(State newState) {
		
		currentState = newState;
	}
	
	public abstract void update();
	
	public abstract void draw(Graphics g);

}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
