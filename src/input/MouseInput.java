package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.Button;

public class MouseInput extends MouseAdapter {

	public static int X, Y;
	public static boolean MLB = false;
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getButton() == MouseEvent.BUTTON1 && !MLB) {
			MLB = true;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			MLB = false;
			Button.clicked = false;
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		X = e.getX();
		Y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		X = e.getX();
		Y = e.getY();
	}

}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
