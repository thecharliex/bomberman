package Item;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Map.Nodo;
import Player.Bomberman;

public class Bomb {
	
	private ArrayList<Image> bombSprite;
	
	private ArrayList<Image> expUp;
	private ArrayList<Image> expLeft;
	private ArrayList<Image> expRight;
	private ArrayList<Image> expDown;
	
	private ArrayList<Image> lastUp;
	private ArrayList<Image> lastLeft;
	private ArrayList<Image> lastRight;
	private ArrayList<Image> lastDown;
	
	private Bomberman bomberman = null;
	
	private int fire;
	private boolean justExpUp = false;
	private boolean justExpLeft = false;
	private boolean justExpRight = false;
	private boolean justExpDown = false;
	
	private Nodo listLocation;
	private int index = 0;
	private double SCALE;
	private boolean isBlast = false;
	private int wait = 0;
	private boolean isPlaying = false;
	
	public Bomb( Bomberman bomberman , double SCALE ){
		
		this.listLocation = bomberman.getListLocation();
		this.SCALE = SCALE;
		this.fire = bomberman.getFire();
		this.bomberman = bomberman;
		
		try {
			
			BufferedImage img = ImageIO.read(new File("gfx/bomb.png"));
			
			bombSprite = new ArrayList<Image>();
			
			expUp = new ArrayList<Image>();
			expLeft= new ArrayList<Image>();
			expRight = new ArrayList<Image>();
			expDown = new ArrayList<Image>();
			
			lastUp = new ArrayList<Image>();
			lastLeft= new ArrayList<Image>();
			lastRight = new ArrayList<Image>();
			lastDown = new ArrayList<Image>();
			
			
			// creacion del bombSprite
			for( int i = 0 ; i < 160 ; i += 20 )
				bombSprite.add( img.getSubimage( i , 0 , 20 , 20));
			
			// creacion del expUp
			for( int i = 80 ; i < 160 ; i += 20 )
				expUp.add( img.getSubimage( i , 40 , 20 , 20));
			
			// creacion del expLeft
			for( int i = 0 ; i < 80 ; i += 20 )
				expLeft.add( img.getSubimage( i , 40 , 20 , 20));
			
			// creacion del expRight
			for( int i = 0 ; i < 80 ; i += 20 )
				expRight.add( img.getSubimage( i , 80 , 20 , 20));
			
			// creacion del expDown
			for( int i = 80 ; i < 160 ; i += 20 )
				expDown.add( img.getSubimage( i , 80 , 20 , 20));
			
			// creacion del lastUp
			for( int i = 80 ; i < 160 ; i += 20 )
				lastUp.add( img.getSubimage( i , 20 , 20 , 20));
			
			// creacion del lastLeft
			for( int i = 0 ; i < 80 ; i += 20 )
				lastLeft.add( img.getSubimage( i , 20 , 20 , 20));
			
			// creacion del lastRight
			for( int i = 0 ; i < 80 ; i += 20 )
				lastRight.add( img.getSubimage( i , 60 , 20 , 20));
			
			// creacion del lastDown
			for( int i = 80 ; i < 160 ; i += 20 )
				lastDown.add( img.getSubimage( i , 60 , 20 , 20));			
		}
		catch (IOException e) { e.printStackTrace(); }
		
		try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/bomb_set.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e){}
		
	}
	
	public boolean getBlast(){ return isBlast; }
	public int getIndex(){ return index; }
	public int getFire(){ return fire; }
	public Nodo getListLocation(){ return listLocation; }
	public void setExplode(){ 
		index = 4; 
		
	}
	
	public boolean getJustExpUp(){ return justExpUp; }
	public boolean getJustExpLeft(){ return justExpLeft; }
	public boolean getJustExpRight(){ return justExpRight; }
	public boolean getJustExpDown(){ return justExpDown; }
	
	public void setJustExpUp(){ justExpUp = true; }
	public void setJustExpLeft(){ justExpLeft = true; }
	public void setJustExpRight(){ justExpRight = true; }
	public void setJustExpDown(){ justExpDown = true; }
	
	public void renderFlamesUp( Graphics g ){
		
		Nodo aux = this.listLocation;
		
		for( int i = 0; i < this.fire ; i++){
			
			if( !( aux.getArriba().getDato() == 1 || aux.getArriba().getDato() == 2) ){
				
				if( (( i == this.fire - 1 ) ||
					( aux.getArriba().getDato() == 3 ) ||
					( aux.getArriba().getDato() == 5 ) ||
					( aux.getArriba().getDato() == 6 ) ||
					( aux.getArriba().getDato() == 7 ) ||
					( aux.getArriba().getDato() == 8 ) ||
					( aux.getArriba().getArriba() != null && 
					( aux.getArriba().getArriba().getDato() == 1 || aux.getArriba().getArriba().getDato() == 2 ) ) ) && 
					!justExpUp){
					
					if( !bomberman.isHitted() && aux.getArriba() == bomberman.getListLocation() )
						bomberman.setHittedTrue();
					
					g.drawImage( lastUp.get( index - 4 ), aux.getArriba().getX() , (int)(aux.getArriba().getY() ) , 
							(int)( aux.getArriba().getX() + 20 * SCALE ) , 
							(int)( aux.getArriba().getY() + 30 * SCALE ) ,
							0 , 0 , 20 , 20 , null );
					
					// cambia la imagen de la caja si esta es igual a 3
					// osea que solo sea una caja sin item
					if( index == 7){
						
						switch( aux.getArriba().getDato() ){
						
						case 3:
							aux.getArriba().setDato( 0 );
							
							try {
								BufferedImage img = null;
								
								if( aux.getArriba().getMapImage() == 1)
									img = ImageIO.read(new File("gfx/tile1.png"));
								if( aux.getArriba().getMapImage() == 2)
									img = ImageIO.read(new File("gfx/tile2.png"));
								if( aux.getArriba().getMapImage() == 3)
									img = ImageIO.read(new File("gfx/tile3.png"));
								
								aux.getArriba().setImage( img.getSubimage( 0 , 0 , 40 , 60));	
							}
							catch (IOException e) { e.printStackTrace(); }
							break;
						case 5:
							// si es 5 y tiene un item
							/* poner el item en false
							 * pasarle la imagen del item
							 * */
							if( aux.getArriba().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getArriba().setImage( img.getSubimage( 0 , 0 , 40 , 60));
									aux.getArriba().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
						case 6:
							if( aux.getArriba().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getArriba().setImage( img.getSubimage( 40 , 0 , 40 , 60));
									aux.getArriba().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
						case 7:
							if( aux.getArriba().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getArriba().setImage( img.getSubimage( 80 , 0 , 40 , 60));
									aux.getArriba().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
						case 8:
							if( aux.getArriba().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getArriba().setImage( img.getSubimage( 120 , 0 , 40 , 60));
									aux.getArriba().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
							
						}
					}
					break;
				}
				else{
					
					// colision con bomberman
					if( !bomberman.isHitted() && aux.getArriba() == bomberman.getListLocation() )
						bomberman.setHittedTrue();
					
					g.drawImage( expUp.get( index - 4 ), aux.getArriba().getX() , (int)(aux.getArriba().getY() ) , 
							(int)( aux.getArriba().getX() + 20 * SCALE ) , 
							(int)( aux.getArriba().getY() + 30 * SCALE ) ,
							0 , 0 , 20 , 20 , null );	
				}
			}
			else
				break;
			
			aux = aux.getArriba();
		}
	}
	
	public void renderFlamesLeft( Graphics g ){
		
		Nodo aux = this.listLocation;
		
		for( int i = 0; i < this.fire ; i++){
			
			if( !( aux.getAnterior().getDato() == 1 || aux.getAnterior().getDato() == 2) ){
				
				if( (( i == this.fire - 1 ) ||
						( aux.getAnterior().getDato() == 3 ) ||
						( aux.getAnterior().getDato() == 5 ) ||
						( aux.getAnterior().getDato() == 6 ) ||
						( aux.getAnterior().getDato() == 7 ) ||
						( aux.getAnterior().getDato() == 8 ) ||
						( aux.getAnterior().getAnterior() != null && 
						( aux.getAnterior().getAnterior().getDato() == 1 || aux.getAnterior().getAnterior().getDato() == 2 ) ) ) && 
						!justExpLeft){
					
					if( !bomberman.isHitted() && aux.getAnterior() == bomberman.getListLocation() )
						bomberman.setHittedTrue();
					
					g.drawImage( lastLeft.get( index - 4 ), aux.getAnterior().getX() , (int)(aux.getAnterior().getY() ) , 
							(int)( aux.getAnterior().getX() + 20 * SCALE ) , 
							(int)( aux.getAnterior().getY() + 30 * SCALE ) ,
							0 , 0 , 20 , 20 , null );
					
					/*if( aux.getAnterior().getDato() == 3 && index == 7 ){
						aux.getAnterior().setDato( 0 );
						
						try {
							BufferedImage img = null;
							
							if( aux.getAnterior().getMapImage() == 1)
								img = ImageIO.read(new File("gfx/tile1.png"));
							if( aux.getAnterior().getMapImage() == 2)
								img = ImageIO.read(new File("gfx/tile2.png"));
							if( aux.getAnterior().getMapImage() == 3)
								img = ImageIO.read(new File("gfx/tile3.png"));
							
							aux.getAnterior().setImage( img.getSubimage( 0 , 0 , 40 , 60));	
						}
						catch (IOException e) { e.printStackTrace(); }
						
						
					}*/
					
					if( index == 7){
						
						switch( aux.getAnterior().getDato() ){
						
						case 3:
							aux.getAnterior().setDato( 0 );
							
							try {
								BufferedImage img = null;
								
								if( aux.getAnterior().getMapImage() == 1)
									img = ImageIO.read(new File("gfx/tile1.png"));
								if( aux.getAnterior().getMapImage() == 2)
									img = ImageIO.read(new File("gfx/tile2.png"));
								if( aux.getAnterior().getMapImage() == 3)
									img = ImageIO.read(new File("gfx/tile3.png"));
								
								aux.getAnterior().setImage( img.getSubimage( 0 , 0 , 40 , 60));	
							}
							catch (IOException e) { e.printStackTrace(); }
							break;
						case 5:
							// si es 5 y tiene un item
							/* poner el item en false
							 * pasarle la imagen del item
							 * */
							if( aux.getAnterior().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getAnterior().setImage( img.getSubimage( 0 , 0 , 40 , 60));
									aux.getAnterior().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
						case 6:
							if( aux.getAnterior().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getAnterior().setImage( img.getSubimage( 40 , 0 , 40 , 60));
									aux.getAnterior().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
						case 7:
							if( aux.getAnterior().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getAnterior().setImage( img.getSubimage( 80 , 0 , 40 , 60));
									aux.getAnterior().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
						case 8:
							if( aux.getAnterior().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getAnterior().setImage( img.getSubimage( 120 , 0 , 40 , 60));
									aux.getAnterior().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;		
						}
					}
					break;
				}
				else{
					
					if( !bomberman.isHitted() && aux.getAnterior() == bomberman.getListLocation() )
						bomberman.setHittedTrue();
					
					g.drawImage( expLeft.get( index - 4 ), aux.getAnterior().getX() , (int)(aux.getAnterior().getY() ) , 
							(int)( aux.getAnterior().getX() + 20 * SCALE ) , 
							(int)( aux.getAnterior().getY() + 30 * SCALE ) ,
							0 , 0 , 20 , 20 , null );			
				}
			}
			else
				break;
			aux = aux.getAnterior();
		}
	}
	
	public void renderFlamesRight( Graphics g ){
		
		Nodo aux = this.listLocation;
		
		for( int i = 0; i < this.fire ; i++){
			
			if( !( aux.getSiguiente().getDato() == 1 || aux.getSiguiente().getDato() == 2) ){
				
				if( (( i == this.fire - 1 ) ||
					( aux.getSiguiente().getDato() == 3 ) ||
					( aux.getSiguiente().getDato() == 5 ) ||
					( aux.getSiguiente().getDato() == 6 ) ||
					( aux.getSiguiente().getDato() == 7 ) ||
					( aux.getSiguiente().getDato() == 8 ) ||
					( aux.getSiguiente().getAnterior() != null && 
					( aux.getSiguiente().getSiguiente().getDato() == 1 || aux.getSiguiente().getSiguiente().getDato() == 2 ) ) ) && 
					!justExpRight){
					
					if( !bomberman.isHitted() && aux.getSiguiente() == bomberman.getListLocation() )
						bomberman.setHittedTrue();
					
					g.drawImage( lastRight.get( index - 4 ), aux.getSiguiente().getX() , (int)(aux.getSiguiente().getY() ) , 
							(int)( aux.getSiguiente().getX() + 20 * SCALE ) , 
							(int)( aux.getSiguiente().getY() + 30 * SCALE ) ,
							0 , 0 , 20 , 20 , null );
					
					/*if( aux.getSiguiente().getDato() == 3 && index == 7 ){
						aux.getSiguiente().setDato( 0 );
						
						try {
							BufferedImage img = null;
							
							if( aux.getSiguiente().getMapImage() == 1)
								img = ImageIO.read(new File("gfx/tile1.png"));
							if( aux.getSiguiente().getMapImage() == 2)
								img = ImageIO.read(new File("gfx/tile2.png"));
							if( aux.getSiguiente().getMapImage() == 3)
								img = ImageIO.read(new File("gfx/tile3.png"));
							
							aux.getSiguiente().setImage( img.getSubimage( 0 , 0 , 40 , 60));	
						}
						catch (IOException e) { e.printStackTrace(); }
						
					}*/
					
					if( index == 7){
						
						switch( aux.getSiguiente().getDato() ){
						
						case 3:
							aux.getSiguiente().setDato( 0 );
							
							try {
								BufferedImage img = null;
								
								if( aux.getSiguiente().getMapImage() == 1)
									img = ImageIO.read(new File("gfx/tile1.png"));
								if( aux.getSiguiente().getMapImage() == 2)
									img = ImageIO.read(new File("gfx/tile2.png"));
								if( aux.getSiguiente().getMapImage() == 3)
									img = ImageIO.read(new File("gfx/tile3.png"));
								
								aux.getSiguiente().setImage( img.getSubimage( 0 , 0 , 40 , 60));	
							}
							catch (IOException e) { e.printStackTrace(); }
							break;
						case 5:
							// si es 5 y tiene un item
							/* poner el item en false
							 * pasarle la imagen del item
							 * */
							if( aux.getSiguiente().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getSiguiente().setImage( img.getSubimage( 0 , 0 , 40 , 60));
									aux.getSiguiente().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
						case 6:
							if( aux.getSiguiente().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getSiguiente().setImage( img.getSubimage( 40 , 0 , 40 , 60));
									aux.getSiguiente().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
						case 7:
							if( aux.getSiguiente().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getSiguiente().setImage( img.getSubimage( 80 , 0 , 40 , 60));
									aux.getSiguiente().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
						case 8:
							if( aux.getSiguiente().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getSiguiente().setImage( img.getSubimage( 120 , 0 , 40 , 60));
									aux.getSiguiente().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
							
						}
					}					
					
					break;
				}
				else{
					
					if( !bomberman.isHitted() && aux.getSiguiente() == bomberman.getListLocation() )
						bomberman.setHittedTrue();
					
					g.drawImage( expRight.get( index - 4 ), aux.getSiguiente().getX() , (int)(aux.getSiguiente().getY() ) , 
							(int)( aux.getSiguiente().getX() + 20 * SCALE ) , 
							(int)( aux.getSiguiente().getY() + 30 * SCALE ) ,
							0 , 0 , 20 , 20 , null );			
				}
			}
			else
				break;
			aux = aux.getSiguiente();
		}
	}
	
	public void renderFlamesDown( Graphics g ){
		
		Nodo aux = this.listLocation;
		
		for( int i = 0; i < this.fire ; i++){
			
			if( !( aux.getAbajo().getDato() == 1 || aux.getAbajo().getDato() == 2) ){
				
				if( (( i == this.fire - 1 ) ||
						( aux.getAbajo().getDato() == 3 ) ||
						( aux.getAbajo().getDato() == 5 ) ||
						( aux.getAbajo().getDato() == 6 ) ||
						( aux.getAbajo().getDato() == 7 ) ||
						( aux.getAbajo().getDato() == 8 ) ||
						( aux.getAbajo().getAbajo() != null && 
						( aux.getAbajo().getAbajo().getDato() == 1 || aux.getAbajo().getAbajo().getDato() == 2 ) ) ) && 
						!justExpDown){
					
					if( !bomberman.isHitted() && aux.getAbajo() == bomberman.getListLocation() )
						bomberman.setHittedTrue();
					
					g.drawImage( lastDown.get( index - 4 ), aux.getAbajo().getX() , (int)(aux.getAbajo().getY() ) , 
							(int)( aux.getAbajo().getX() + 20 * SCALE ) , 
							(int)( aux.getAbajo().getY() + 30 * SCALE ) ,
							0 , 0 , 20 , 20 , null );
					
					/*if( aux.getAbajo().getDato() == 3 && index == 7 ){
						aux.getAbajo().setDato( 0 );
						
						try {
							BufferedImage img = null;
							
							if( aux.getAbajo().getMapImage() == 1)
								img = ImageIO.read(new File("gfx/tile1.png"));
							if( aux.getAbajo().getMapImage() == 2)
								img = ImageIO.read(new File("gfx/tile2.png"));
							if( aux.getAbajo().getMapImage() == 3)
								img = ImageIO.read(new File("gfx/tile3.png"));
							
							aux.getAbajo().setImage( img.getSubimage( 0 , 0 , 40 , 60));	
						}
						catch (IOException e) { e.printStackTrace(); }
						
					}*/
					
					if( index == 7){
						
						switch( aux.getAbajo().getDato() ){
						
						case 3:
							aux.getAbajo().setDato( 0 );
							
							try {
								BufferedImage img = null;
								
								if( aux.getAbajo().getMapImage() == 1)
									img = ImageIO.read(new File("gfx/tile1.png"));
								if( aux.getAbajo().getMapImage() == 2)
									img = ImageIO.read(new File("gfx/tile2.png"));
								if( aux.getAbajo().getMapImage() == 3)
									img = ImageIO.read(new File("gfx/tile3.png"));
								
								aux.getAbajo().setImage( img.getSubimage( 0 , 0 , 40 , 60));	
							}
							catch (IOException e) { e.printStackTrace(); }
							break;
						case 5:
							// si es 5 y tiene un item
							/* poner el item en false
							 * pasarle la imagen del item
							 * */
							if( aux.getAbajo().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getAbajo().setImage( img.getSubimage( 0 , 0 , 40 , 60));
									aux.getAbajo().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
						case 6:
							if( aux.getAbajo().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getAbajo().setImage( img.getSubimage( 40 , 0 , 40 , 60));
									aux.getAbajo().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
						case 7:
							if( aux.getAbajo().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getAbajo().setImage( img.getSubimage( 80 , 0 , 40 , 60));
									aux.getAbajo().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
						case 8:
							if( aux.getAbajo().getHasItem() ){
								try {
									BufferedImage img = null;
									img = ImageIO.read(new File("gfx/items.png"));
									
									aux.getAbajo().setImage( img.getSubimage( 120 , 0 , 40 , 60));
									aux.getAbajo().setHasItemFalse();
								}
								catch (IOException e) { e.printStackTrace(); }
							}
							break;
							
						}
					}	
					
					break;
				}
				else{
					
					if( !bomberman.isHitted() && aux.getAbajo() == bomberman.getListLocation() )
						bomberman.setHittedTrue();
					
					g.drawImage( expDown.get( index - 4 ), aux.getAbajo().getX() , (int)(aux.getAbajo().getY() ) , 
							(int)( aux.getAbajo().getX() + 20 * SCALE ) , 
							(int)( aux.getAbajo().getY() + 30 * SCALE ) ,
							0 , 0 , 20 , 20 , null );			
				}
			}
			else
				break;
			aux = aux.getAbajo();
		}
	}
	
	public void renderBomb( Graphics g ){
		
		if( !isBlast ){
			if( index < 3 ){
				
				g.drawImage( bombSprite.get( index ), this.listLocation.getX() , (int)(this.listLocation.getY() + 5 * SCALE) , 
						(int)( this.listLocation.getX() + 20 * SCALE ) , 
						(int)( this.listLocation.getY() + 5 * SCALE + 20 * SCALE ) ,
						0 , 0 , 20 , 20 , null );
				
				wait++;
				
				if( wait % 30 == 0 ){
					wait = 0;
					index++;
				}
				else{
					if( wait > 30 )
						wait = 0;
				}
			}
			else{ // imprimir la expansion de la bomba
				
				
				/* nesecito saber que tanto alcanze tiene la bomba
				 * una vez conociendo el alcanze
				 * 
				 * checar que tanto explotará dependiendo de la expancion
				 * hacia cualquier lado
				 * 		la bomba llegará hasta donde se tope con pared "1 - 2"
				 * 		la bomba explotará las cajas de los mapas "3"
				 * 			cuando llegue a una caja, la imagen se actualizará
				 * 			junto con el dato, este pasará a ser 0
				 * 		si la bomba choca con bomberman se checa si hitted es falso
				 * 			este tendrá una vida menos y se modificará su hitEffect con 3
				 * 			para que de la impresion de que fue golpeado
				 * 			tambien se pondrá en true la variable hitted
				 * 			despues de 2 segundos el hittEffect volverá a su estado anterior
				 * 		si no es falso no pasa nada
				 * */
				
				// imprime la animacion de el centro de la bomba
				g.drawImage( bombSprite.get( index ), this.listLocation.getX() , (int)(this.listLocation.getY() ) , 
						(int)( this.listLocation.getX() + 20 * SCALE ) , 
						(int)( this.listLocation.getY() + 30 * SCALE ) ,
						0 , 0 , 20 , 20 , null );
				
				// imprimir la animacion de la parte de arriba de la bomba
				// recorrer los nodos hacia arriba y si
				
				if( index > 3 ){
					
					if( this.listLocation == bomberman.getListLocation() ){
						if( !bomberman.isHitted() )
							this.bomberman.setHittedTrue();
					}
					
					if( !isPlaying ){
						try {
				            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/bomb_explosion.wav"));
				            Clip clip = AudioSystem.getClip();
				            clip.open(audio);
				            clip.start();
				        } catch (Exception e1){}
						
						isPlaying = true;
					}
					
					this.renderFlamesUp( g );
					this.renderFlamesLeft( g );
					this.renderFlamesRight( g );
					this.renderFlamesDown( g );
					
					// checa la colision con bomberman
					
				}
				
				wait++;
				
				if( index + 1 == 7)
					index++;
				else if( wait % 2 == 0 ){
					wait = 0;
					index++;
				}
				else{
					if( wait > 30 )
						wait = 0;
				}
				
				
				if( index > 7 ){
					isBlast = true;
				}
			}
		}
	}

}
