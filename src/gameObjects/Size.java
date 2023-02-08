package gameObjects;

import java.awt.image.BufferedImage;

import graphics.Assets;

public enum Size {
	
	BIG(2, Assets.meds), MED(2, Assets.smalls), SMALL(2, Assets.tinies), TINY(0, null);
	public int quantity;
	public BufferedImage[] textures;
	
	private Size(int quantity, BufferedImage[] textures){
		this.quantity = quantity;
		this.textures = textures;
	}
	
}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
