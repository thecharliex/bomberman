package Player;

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

public class Bomberman implements Runnable{
	
	private ArrayList<Image> upSprite;
	private ArrayList<Image> downSprite;
	private ArrayList<Image> rightSprite;
	private ArrayList<Image> leftSprite;
	
	private Nodo listLocation;
	
	private int lifes = 2;
	private int bombBag = 1;
	private int fire = 1;
	private int hitEffect = 2;
	private boolean isHitted = false;
	private int speed = 5;
	
	private double scale;
	private int x;
	private int y;
	private int index = 1;
	
	private boolean isMoving = false;
	private int side = 0;
	private boolean wait = false;
	
	public Bomberman( double scale ){
		
		upSprite = new ArrayList();
		downSprite = new ArrayList();
		rightSprite = new ArrayList();
		leftSprite = new ArrayList();
		
		this.scale = scale;
		x = (int)(20 * scale);
		y = (int)(240 * scale);
		
		try {
			
			BufferedImage img = ImageIO.read(new File("gfx/bomberman.png"));
			
			this.leftSprite.add( img.getSubimage( 20 , 0 , 20 , 30 ));
			this.rightSprite.add( img.getSubimage( 200 , 0 , 20 , 30 ));
			this.upSprite.add( img.getSubimage( 140 , 0 , 20 , 30 ));
			this.downSprite.add( img.getSubimage( 80 , 0 , 20 , 30 ));
			
			for( int x = 0; x <= 40 ; x+=20)
				this.leftSprite.add( img.getSubimage( x , 0 , 20 , 30 ) );
			
			for( int x = 180; x < 240 ; x+=20)
				this.rightSprite.add( img.getSubimage( x , 0 , 20 , 30 ) );
			
			for( int x = 120; x <= 160 ; x+=20)
				this.upSprite.add( img.getSubimage( x , 0 , 20 , 30 ) );
			
			for( int x = 60; x <= 100 ; x+=20)
				this.downSprite.add( img.getSubimage( x , 0 , 20 , 30 ) );
			
			
		}
		catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public Image getLeftSprite( int index ){ return leftSprite.get( index ); }
	public Image getRightSprite( int index ){ return rightSprite.get( index ); }
	public Image getUpSprite( int index ){ return upSprite.get( index ); }
	public Image getDownSprite( int index ){ return downSprite.get( index ); }
	
	public Nodo getListLocation(){ return this.listLocation; }
	public void setListLocation( Nodo location ){ this.listLocation = location; }
	public void setXYLocation(){ 
		x = (int)(20 * scale);
		y = (int)(240 * scale);
	}
	
	public int getBombBag(){ return bombBag; }
	public void increaseBombBag(){ bombBag++; }
	
	public int getFire(){ return fire; }
	public void increaseFire(){ fire++; }
	
	public void increaseSpeed(){ speed += 5; }
	
	public boolean isHitted(){ return this.isHitted; }
	public void setHittedTrue(){ 
		isHitted = true;
		this.lifeDown();
		Thread thread = new Thread(this);
		// System.out.println("Golpeado :c");
		// System.out.println( "Vidas" + this.lifes );
		
		try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/dead_player.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e1){}
		
		thread.start();
		
	}
	public void setHittedFalse(){ isHitted = false; }
	
	public int getHitEffect(){ return hitEffect; }
	public void changeHitEffect(){
		if( hitEffect == 2 )
			hitEffect = 4;
		else
			hitEffect = 2;
	}
	
	public int getX(){ return x; }
	public int getY(){ return y; }
	
	public void setSide( int side ){ this.side = side; }
	public void setMovingTrue(){ this.isMoving = true; }
	public void setMovingFalse(){ this.isMoving = false; }
	
	public int getLifes(){ return lifes; }
	public void lifeUp(){ lifes++; }
	public void lifeDown(){ lifes--; }
	
	public void moveUp(){ 
		
		/* si y - 5 es menor que el y del nodo del bomberman entonces
		 * checar si el nodo de arriba es 0 para poder pasar
		 * si no es 0 no pasa
		 * si es 0 si pasa y el nodo del bomber se sube
		 * */
		
		if( y - speed * scale < this.listLocation.getY() - 20 * scale ){
			
			/*if( this.listLocation.getArriba().getDato() == 0  && 
				this.x == this.listLocation.getX() && 
				this.x + 20 * scale == this.listLocation.getX() + 20 * scale ){
				y -= speed * scale;
				
				if( y + 30 * scale <= this.listLocation.getY() ){
					this.listLocation.setDato( 0 );
					this.listLocation = this.listLocation.getArriba();
					this.listLocation.setDato( 10 );
				}
			}*/
			
			if((this.listLocation.getArriba().getDato() == 0  || 
			   ((this.listLocation.getArriba().getDato() == 5 || 
				this.listLocation.getArriba().getDato() == 6 ||
				this.listLocation.getArriba().getDato() == 7 ||
				this.listLocation.getArriba().getDato() == 8 ) &&
				!this.listLocation.getArriba().getHasItem() )) && 
				this.x == this.listLocation.getX() && 
				this.x + 20 * scale == this.listLocation.getX() + 20 * scale ){
				y -= speed * scale;
				
				if( y + 30 * scale <= this.listLocation.getY() ){
					this.listLocation.setDato( 0 );
					this.listLocation = this.listLocation.getArriba();
					
					// agrego la imagen del mapa a una imagen
					BufferedImage img;
					try { 
						
						if( this.listLocation.getMapImage() == 1 )
							img = ImageIO.read(new File("gfx/tile1.png"));
						else if( this.listLocation.getMapImage() == 2 )
							img = ImageIO.read(new File("gfx/tile2.png")); 
						else
							img = ImageIO.read(new File("gfx/tile3.png"));
					
						// le agrega el power up al bomberman y intercambia la imagen por la 
						// imagen 0 del mapa
						switch( this.listLocation.getDato() ){
						case 5:
							this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
							this.increaseBombBag();
							try {
					            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
					            Clip clip = AudioSystem.getClip();
					            clip.open(audio);
					            clip.start();
					        } catch (Exception e1){}
							break;
						case 6:
							this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
							this.increaseFire();
							try {
					            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
					            Clip clip = AudioSystem.getClip();
					            clip.open(audio);
					            clip.start();
					        } catch (Exception e1){}
							break;
						case 7:
							this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
							this.increaseSpeed();
							try {
					            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
					            Clip clip = AudioSystem.getClip();
					            clip.open(audio);
					            clip.start();
					        } catch (Exception e1){}
							break;
						case 8:
							this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
							this.lifeUp();
							try {
					            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
					            Clip clip = AudioSystem.getClip();
					            clip.open(audio);
					            clip.start();
					        } catch (Exception e1){}
							break;
						}
						
					} catch( Exception e ){ }
					
					this.listLocation.setDato( 10 );
				}
			}
			
		}
		else
			y -= speed * scale;
	
	}
	
	public void moveDown(){ 
		
		if( y + speed * scale > this.listLocation.getY() ){
			
			/*if( this.listLocation.getAbajo().getDato() == 0 && 
				this.x == this.listLocation.getX() && 
				this.x + 20 * scale == this.listLocation.getX() + 20 * scale ){
				y += speed * scale;
				
				if( y + 20 * scale > this.listLocation.getY() ){
					this.listLocation.setDato( 0 );
					this.listLocation = this.listLocation.getAbajo();
					this.listLocation.setDato( 10 );
				}
			}*/
			

			if( (this.listLocation.getAbajo().getDato() == 0  ||
				((this.listLocation.getAbajo().getDato() == 5 || 
				this.listLocation.getAbajo().getDato() == 6 ||
				this.listLocation.getAbajo().getDato() == 7 ||
				this.listLocation.getAbajo().getDato() == 8 ) &&
				!this.listLocation.getAbajo().getHasItem() )) &&
				this.x == this.listLocation.getX() && 
				this.x + 20 * scale == this.listLocation.getX() + 20 * scale ){
				y += speed * scale;
				
				if( y + 20 * scale > this.listLocation.getY() ){
					this.listLocation.setDato( 0 );
					this.listLocation = this.listLocation.getAbajo();
					
					// agrego la imagen del mapa a una imagen
					BufferedImage img;
					try { 
						
						if( this.listLocation.getMapImage() == 1 )
							img = ImageIO.read(new File("gfx/tile1.png"));
						else if( this.listLocation.getMapImage() == 2 )
							img = ImageIO.read(new File("gfx/tile2.png")); 
						else
							img = ImageIO.read(new File("gfx/tile3.png"));
					
						// le agrega el power up al bomberman y intercambia la imagen por la 
						// imagen 0 del mapa
						switch( this.listLocation.getDato() ){
						case 5:
							this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
							this.increaseBombBag();
							try {
					            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
					            Clip clip = AudioSystem.getClip();
					            clip.open(audio);
					            clip.start();
					        } catch (Exception e1){}
							break;
						case 6:
							this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
							this.increaseFire();
							try {
					            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
					            Clip clip = AudioSystem.getClip();
					            clip.open(audio);
					            clip.start();
					        } catch (Exception e1){}
							break;
						case 7:
							this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
							this.increaseSpeed();
							try {
					            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
					            Clip clip = AudioSystem.getClip();
					            clip.open(audio);
					            clip.start();
					        } catch (Exception e1){}
							break;
						case 8:
							this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
							this.lifeUp();
							try {
					            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
					            Clip clip = AudioSystem.getClip();
					            clip.open(audio);
					            clip.start();
					        } catch (Exception e1){}
							break;
						}
						
					} catch( Exception e ){ }
					
					this.listLocation.setDato( 10 );
				}
			
			}
		}
		else
			y += speed * scale;
	}
	
	public void moveRight(){
		
		if( x + speed * scale > this.listLocation.getX() ){
			
			if( this.listLocation.getSiguiente().getDato() == 0 ||
				((this.listLocation.getSiguiente().getDato() == 5 || 
				this.listLocation.getSiguiente().getDato() == 6 ||
				this.listLocation.getSiguiente().getDato() == 7 ||
				this.listLocation.getSiguiente().getDato() == 8 ) && 
				!this.listLocation.getSiguiente().getHasItem() )){
				x += speed * scale;
				
				if( x + 20 * scale > this.listLocation.getSiguiente().getX() + 10 * scale ){
					/*this.listLocation.setDato( 0 );
					this.listLocation = this.listLocation.getSiguiente();
					this.listLocation.setDato( 10 );*/
					
					this.listLocation.setDato( 0 );
					this.listLocation = this.listLocation.getSiguiente();
					
					// agrego la imagen del mapa a una imagen
					BufferedImage img;
					try { 
						
						if( this.listLocation.getMapImage() == 1 )
							img = ImageIO.read(new File("gfx/tile1.png"));
						else if( this.listLocation.getMapImage() == 2 )
							img = ImageIO.read(new File("gfx/tile2.png")); 
						else
							img = ImageIO.read(new File("gfx/tile3.png"));
					
						// le agrega el power up al bomberman y intercambia la imagen por la 
						// imagen 0 del mapa
						switch( this.listLocation.getDato() ){
						case 5:
							this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
							this.increaseBombBag();
							try {
					            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
					            Clip clip = AudioSystem.getClip();
					            clip.open(audio);
					            clip.start();
					        } catch (Exception e1){}
							break;
						case 6:
							this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
							this.increaseFire();
							try {
					            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
					            Clip clip = AudioSystem.getClip();
					            clip.open(audio);
					            clip.start();
					        } catch (Exception e1){}
							break;
						case 7:
							this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
							this.increaseSpeed();
							try {
					            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
					            Clip clip = AudioSystem.getClip();
					            clip.open(audio);
					            clip.start();
					        } catch (Exception e1){}
							break;
						case 8:
							this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
							this.lifeUp();
							try {
					            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
					            Clip clip = AudioSystem.getClip();
					            clip.open(audio);
					            clip.start();
					        } catch (Exception e1){}
							break;
						}
						
					} catch( Exception e ){ }
					
					this.listLocation.setDato( 10 );
					
				}
			}
		}
		else
			x += speed * scale;
		
	}
	
	public void moveLeft(){ 
		
		if( x - speed * scale < this.listLocation.getX() ){
			/*if( this.listLocation.getAnterior().getDato() == 0){
				x -= speed * scale;
				
				if( x < this.listLocation.getAnterior().getX() + 10 * scale){
					this.listLocation.setDato( 0 );
					this.listLocation = this.listLocation.getAnterior();
					this.listLocation.setDato( 10 );
				}
			}*/
			
			if( this.listLocation.getAnterior().getDato() == 0 ||
				((this.listLocation.getAnterior().getDato() == 5 || 
				this.listLocation.getAnterior().getDato() == 6 ||
				this.listLocation.getAnterior().getDato() == 7 ||
				this.listLocation.getAnterior().getDato() == 8 ) && 
				!this.listLocation.getAnterior().getHasItem() )){
					x -= speed * scale;
					
					if( x < this.listLocation.getAnterior().getX() + 10 * scale ){
						/*this.listLocation.setDato( 0 );
						this.listLocation = this.listLocation.getSiguiente();
						this.listLocation.setDato( 10 );*/
						
						this.listLocation.setDato( 0 );
						this.listLocation = this.listLocation.getAnterior();
						
						// agrego la imagen del mapa a una imagen
						BufferedImage img;
						try { 
							
							if( this.listLocation.getMapImage() == 1 )
								img = ImageIO.read(new File("gfx/tile1.png"));
							else if( this.listLocation.getMapImage() == 2 )
								img = ImageIO.read(new File("gfx/tile2.png")); 
							else
								img = ImageIO.read(new File("gfx/tile3.png"));
						
							// le agrega el power up al bomberman y intercambia la imagen por la 
							// imagen 0 del mapa
							switch( this.listLocation.getDato() ){
							case 5:
								this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
								this.increaseBombBag();
								try {
						            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
						            Clip clip = AudioSystem.getClip();
						            clip.open(audio);
						            clip.start();
						        } catch (Exception e1){}
								break;
							case 6:
								this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
								this.increaseFire();
								try {
						            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
						            Clip clip = AudioSystem.getClip();
						            clip.open(audio);
						            clip.start();
						        } catch (Exception e1){}
								break;
							case 7:
								this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
								this.increaseSpeed();
								try {
						            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
						            Clip clip = AudioSystem.getClip();
						            clip.open(audio);
						            clip.start();
						        } catch (Exception e1){}
								break;
							case 8:
								this.listLocation.setImage( img.getSubimage( 0 , 0 , 40 , 60 ));
								this.lifeUp();
								try {
						            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/item_collected.wav"));
						            Clip clip = AudioSystem.getClip();
						            clip.open(audio);
						            clip.start();
						        } catch (Exception e1){}
								break;
							}
							
						} catch( Exception e ){ }
						
						this.listLocation.setDato( 10 );
						
					}
				}
		}
		else
			x -= speed * scale;
	}
	
	public void renderBomberMan( Graphics g ){
		
		if( isMoving ){
			
			switch( side ){
			case 0: // abajo
				g.drawImage( this.getDownSprite( index ), this.getX() , 
							 this.getY() , (int)(this.getX() + 20 * scale) , 
							 (int)(this.getY() + 30 * scale) , 0 , 0 , 20 , 30 , null );
				break;
			case 1: // arriba
				g.drawImage( this.getUpSprite( index ), this.getX() , 
							 this.getY() , (int)(this.getX() + 20 * scale) , 
							 (int)(this.getY() + 30 * scale) , 0 , 0 , 20 , 30 , null );
				break;
			case 2: // izquierda
				g.drawImage( this.getLeftSprite( index ), this.getX() , 
							 this.getY() , (int)(this.getX() + 20 * scale) , 
							 (int)(this.getY() + 30 * scale) , 0 , 0 , 20 , 30 , null );
				break;
			case 3: // derecha
				g.drawImage( this.getRightSprite( index ), this.getX() , 
							 this.getY() , (int)(this.getX() + 20 * scale) , 
							 (int)(this.getY() + 30 * scale) , 0 , 0 , 20 , 30 , null );
				break;
			}
			
		}
		else{
			
			switch( side ){
			case 0: // abajo
				g.drawImage( this.getDownSprite( 0 ), this.getX() , 
							 this.getY() , (int)(this.getX() + 20 * scale) , 
							 (int)(this.getY() + 30 * scale) , 0 , 0 , 20 , 30 , null );
				break;
			case 1: // arriba
				g.drawImage( this.getUpSprite( 0 ) , this.getX() , 
							 this.getY() , (int)(this.getX() + 20 * scale) , 
							 (int)(this.getY() + 30 * scale) , 0 , 0 , 20 , 30 , null );
				break;
			case 2: // izquierda
				g.drawImage( this.getLeftSprite( 0 ), this.getX() , 
							 this.getY() , (int)(this.getX() + 20 * scale) , 
							 (int)(this.getY() + 30 * scale) , 0 , 0 , 20 , 30 , null );
				break;
			case 3: // derecha
				g.drawImage( this.getRightSprite( 0 ), this.getX() , 
							 this.getY() , (int)(this.getX() + 20 * scale) , 
							 (int)(this.getY() + 30 * scale) , 0 , 0 , 20 , 30 , null );
				break;
			}
		}
		
		
		
		if( this.wait){
			this.wait = false;
		}
		else{
			this.wait = true;
			index++;
			
			if( index > 3 )
				index = 1;
			
		}
		
	}

	public void displayStats(){
		
		System.out.println("Bomberman");
		System.out.println("Lifes: " + lifes);
		System.out.println("Bomb-Bag: " + bombBag);
		System.out.println("Fire: " + fire);
		System.out.println("Speed: " + speed);
		
	}
	
	@Override
	public void run() {
		
		while( isHitted ){
			this.changeHitEffect();
			try { Thread.sleep( 2000 ); }
			catch (InterruptedException e) { }
			this.setHittedFalse();
			this.changeHitEffect();
		}
		
	}
	
}
