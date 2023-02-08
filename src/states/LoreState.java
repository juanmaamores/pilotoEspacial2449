package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import gameObjects.Constants;
import graphics.Assets;
import graphics.Loader;
import graphics.Sound;
import graphics.Text;
import math.Vector2D;
import ui.Action;
import ui.Button;

public class LoreState extends State{
	
	private ArrayList<Button> buttons;
	private Sound loreMusic, buttonClick;
	private Font fontTitle, fontParagraph;
	
	public LoreState() {
		
		buttons = new ArrayList<Button>();
		loreMusic = new Sound(Assets.loreStateMusic);
		fontTitle = Loader.loadFont("/fonts/futureFont.ttf", 50);
		fontParagraph = Loader.loadFont("/fonts/paragraphFont.ttf", 22);
		
		if(!MenuState.muted) {
			//loreMusic.changeVolume(-10f);
			loreMusic.loop();
		}
		
		buttonClick = new Sound(Assets.buttonClick);
		
		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, 10, 10, "Volver", new Action() { @Override public void doAction() { 
			
			loreMusic.stop();
			buttonClick.changeVolume(-10f);
			buttonClick.play();
			State.changeState(new MenuState()); 
			} 
		} ));
		
		buttons.add(new Button(Assets.greyBtnSmall, Assets.blueBtnSmall, 1250, 650, " ", new Action() { @Override public void doAction() { 
			
			buttonClick.changeVolume(-10f);
			buttonClick.play();
			
			if(!MenuState.muted) {
				loreMusic.stop();
				MenuState.muted = true;
			} else {
				loreMusic.play();
				MenuState.muted = false;
			}
		 } 
		} ));
		
	}

	@Override
	public void update() {
		
		for(Button b: buttons) {
			b.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.drawImage(Assets.loreBackground, 0, 0, Constants.WIDTH, Constants.HEIGHT, null);
		Text.drawText(g2d, "Lore - Año actual 2.449", new Vector2D(Constants.WIDTH / 2, 90), true, Color.WHITE, fontTitle);
		
		//parrafo 1
		Text.drawTextRight(g2d, "El Segundo sol es considerado, todavía a día de hoy, doscientos años después del", new Vector2D(240, 120), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "incidente, uno de los accidentes más graves de la historia aeroespacial. El tres de febrero", new Vector2D(240, 140), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "del año 2249, la primera corbeta de clase estelar clase Caronte, llamada Wisdom&Progress,", new Vector2D(240, 160), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "motor de condensación gravitatoria, un aparato lo suficientemente poderoso para poder", new Vector2D(240, 180), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "despegó  de la tierra equipada con el invento más reciente para los viajes espaciales, un", new Vector2D(240, 200), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "crear una falla gravitacional lo suficientemente amplia para transportar la nave de un punto", new Vector2D(240, 220), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "del espacio a otro. Sin embargo, por un incidente inesperado, la gravedad del artilugio atrajo", new Vector2D(240, 240), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "una gran cantidad de meteoritos que impactaron con la corbeta, haciéndola colapsar. La", new Vector2D(240, 260), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "nube de gas, luz y restos centellantes del motor y los 4700 tripulantes iluminaron el cielo", new Vector2D(240, 280), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "terrestre por casi una semana.", new Vector2D(240, 300), true, Color.WHITE, fontParagraph);
		//parrafo 2
		Text.drawTextRight(g2d, "Tras el incidente, el GUT (Gobierno Unificado de la Tierra) comenzó con una iniciativa para", new Vector2D(240, 340), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "evitar que tal catástrofe se volviera a repetir. Con la ayuda de la Doctora Liu’ Chen, la", new Vector2D(240, 360), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "diseñadora original del motor de la Wisdom&Progress  y el Comandante Jean Paul Savala", new Vector2D(240, 380), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "ministro de Defensa Planetaria, crearon la Iniciativa Égida, un grupo de pilotos de élite,", new Vector2D(240, 400), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "encargados de defender el espacio aéreo de la Tierra y de todas sus colonias a través del", new Vector2D(240, 420), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "espacio para asegurar un viaje seguro de la humanidad a través de las estrellas. Equipados", new Vector2D(240, 440), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "con tecnología de punta, los escuadrones evaporan los peligros espaciales antes de que", new Vector2D(240, 460), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "estos puedan volverse un problema mayor.", new Vector2D(240, 480), true, Color.WHITE, fontParagraph);
		//parrafo 3
		Text.drawTextRight(g2d, "Ahora, toma los controles de tu Surcacielos, afina tu puntería y prepárate para defender la", new Vector2D(240, 520), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "cuna de la humanidad contra todo aquello que busque interrumpir el avance de los tuyos", new Vector2D(240, 540), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "por el cosmos. Dos siglos han pasado desde que los primeros como tú tomaron el control", new Vector2D(240, 560), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "de sus ya arcaicas naves, cientos de crisis y peligros han sido sorteados, pero el espacio es", new Vector2D(240, 580), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "oscuro y alberga siempre peligros. Por fortuna, la humanidad te tiene a tí, y al resto de las", new Vector2D(240, 600), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "Égidas, pues, como dice su lema: “Nada que venga de fuera de la tierra detendrá el avance", new Vector2D(240, 620), true, Color.WHITE, fontParagraph);
		Text.drawTextRight(g2d, "de quienes salen de ella.” ", new Vector2D(240, 640), true, Color.WHITE, fontParagraph);
		
		for(Button b: buttons) {
			b.draw(g);
		}
		
	}
}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
