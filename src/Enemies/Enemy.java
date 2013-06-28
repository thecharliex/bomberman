package Enemies;

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

public class Enemy implements Runnable{
	
	private ArrayList<Image> upSprite;
	private ArrayList<Image> downSprite;
	private ArrayList<Image> rightSprite;
	private ArrayList<Image> leftSprite;
	private Image deadImage;
	private Bomberman bomber;
	
	private Nodo listLocation;
	
	private double scale;
	private int x;
	private int y;
	private int index = 0;
	
	private boolean dontMove = false;
	private boolean isAlive = true;
	private boolean isAnimationDead = false;
	private boolean isMoving = false;
	private int side = 3;
	private int wait = 1;
	
	private int speed = 5;
	private int move;
	private int moveDone;
	
	public Enemy( Bomberman bomber , int enemy , int x1 , int y1 , double scale ){
		
		upSprite = new ArrayList();
		downSprite = new ArrayList();
		rightSprite = new ArrayList();
		leftSprite = new ArrayList();
		this.bomber = bomber;
		
		this.scale = scale;
		this.x = x1;
		this.y = y1;
		
		try {
			BufferedImage img = null;
			switch( enemy ){
				case 11:
					img = ImageIO.read(new File("gfx/enemy11.png"));
					break;
				case 12:
					img = ImageIO.read(new File("gfx/enemy12.png"));
					break;
				case 13:
					img = ImageIO.read(new File("gfx/enemy13.png"));
					break;
			}
			
			for( int x = 0 ; x <= 80 ; x+=40)
				this.leftSprite.add( img.getSubimage( x , 0 , 40 , 60 ) );
			
			for( int x = 360; x <= 440 ; x+=40)
				this.rightSprite.add( img.getSubimage( x , 0 , 40 , 60 ) );
			
			for( int x = 240; x <= 320 ; x+=40)
				this.upSprite.add( img.getSubimage( x , 0 , 40 , 60 ) );
			
			for( int x = 120; x <= 200 ; x+=40)
				this.downSprite.add( img.getSubimage( x , 0 , 40 , 60 ) );
			
			this.deadImage = img.getSubimage( 480 , 0 , 40 , 60);
		}
		catch (IOException e) { e.printStackTrace(); }
		
		Thread thread = new Thread(this);
		thread.start();
		
	}
	
	public Image getLeftSprite( int index ){ return leftSprite.get( index ); }
	public Image getRightSprite( int index ){ return rightSprite.get( index ); }
	public Image getUpSprite( int index ){ return upSprite.get( index ); }
	public Image getDownSprite( int index ){ return downSprite.get( index ); }
	
	public Nodo getListLocation(){ return this.listLocation; }
	public void setListLocation( Nodo location ){ 
		this.listLocation = location;
		this.listLocation.setDato( 0 );
	}
	
	public void setDontMoveTrue(){ this.dontMove = true; }
	public void setDontMoveFalse(){ this.dontMove = false; }
	public void setDead(){ isAlive = false; }
	public boolean isAlive(){ return isAlive; }
	public boolean isAnimationDead(){ return this.isAnimationDead; }
	
	public int getX(){ return x; }
	public int getY(){ return y; }
	
	public void setSide( int side ){ this.side = side; }
	public void setMovingTrue(){ this.isMoving = true; }
	public void setMovingFalse(){ this.isMoving = false; }
	
	public void renderEnemy( Graphics g ){
		
		if( index > 2 )
			index = 0;
		
		if( this.listLocation == bomber.getListLocation() ){
			if( !bomber.isHitted() && this.isAlive )
				this.bomber.setHittedTrue();
		}
			
		
		switch( side ){
			case 0: // abajo
				g.drawImage( this.getDownSprite( index ), this.getX() , 
							 this.getY() , (int)(this.getX() + 40 * ( scale / 2 ) ) , 
							 (int)(this.getY() + 60 * ( scale / 2 ) ) , 0 , 0 , 40 , 60 , null );
				break;
			case 1: // arriba
				g.drawImage( this.getUpSprite( index ), this.getX() , 
							 this.getY() , (int)(this.getX() + 40 * ( scale / 2 )) , 
							 (int)(this.getY() + 60 * ( scale / 2 ) ) , 0 , 0 , 40 , 60 , null );
				break;
			case 2: // izquierda
				g.drawImage( this.getLeftSprite( index ), this.getX() , 
							 this.getY() , (int)(this.getX() + 40 * ( scale / 2 )) , 
							 (int)(this.getY() + 60 * ( scale / 2 ) ) , 0 , 0 , 40 , 60 , null );
				break;
			case 3: // derecha
				g.drawImage( this.getRightSprite( index ), this.getX() , 
							 this.getY() , (int)(this.getX() + 40 * ( scale / 2 ) ) , 
							 (int)(this.getY() + 60 * ( scale / 2 ) ) , 0 , 0 , 40 , 60 , null );
				break;
			case 4:// imagen dead
				g.drawImage( this.deadImage , this.getX() , 
						 this.getY() , (int)(this.getX() + 40 * ( scale / 2 ) ) , 
						 (int)(this.getY() + 60 * ( scale / 2 ) ) , 0 , 0 , 40 , 60 , null );
				break;
		}
		
		/*if( this.wait < 3){
			this.wait++;
		}
		else{
			this.wait = 0;
			// index++;
			
			if( index > 2 )
				index = 0;
		}*/
		
	}

	public void walkRight(){
		
		if( x + speed * scale > this.listLocation.getX() ){
				
			if( this.listLocation.getSiguiente().getDato() == 0 ||
				this.listLocation.getSiguiente().getDato() == 10 ||
				((this.listLocation.getSiguiente().getDato() == 5 || 
				this.listLocation.getSiguiente().getDato() == 6 ||
				this.listLocation.getSiguiente().getDato() == 7 ||
				this.listLocation.getSiguiente().getDato() == 8 ) && 
				!this.listLocation.getSiguiente().getHasItem() 
				&& move < moveDone)){
				x += ( speed * scale);
				index++;
				moveDone++;
				
				if( x + 20 * scale > this.listLocation.getSiguiente().getX() + 10 * scale )
					this.listLocation = this.listLocation.getSiguiente();
			}
			else
				this.isMoving = false;
		}
		else{
			x += ( speed * scale );
			this.index++;
			moveDone++;
		}
		
		if( move <= this.moveDone )
			this.isMoving = false;
		
	}
	
	public void walkLeft(){
		
		/* tiene que moverse a la izquierda siempre y cuando
		 * 		el nodo de la izq no sea == 1, 2 o 3
		 * 		tambien si es 5,6,7,8 no tiene que tener el item
		 * 		la cantidad que haya recorrido sea menor a la cantidad que haya que recorrer
		 * si se mueve checar si no paso del nodo actual
		 * si se mueve actualizar la cantidad recorrida
		 * si se pasa del nodo asignar el valor del enemigo en el nodo siguiente
		 * */
		
		if( x - speed * scale < this.listLocation.getX() ){
				
			if( this.listLocation.getAnterior().getDato() == 0 ||
				this.listLocation.getAnterior().getDato() == 10 ||
				((this.listLocation.getAnterior().getDato() == 5 || 
				this.listLocation.getAnterior().getDato() == 6 ||
				this.listLocation.getAnterior().getDato() == 7 ||
				this.listLocation.getAnterior().getDato() == 8 ) && 
				!this.listLocation.getAnterior().getHasItem() ) ){
				x -= ( speed * scale );
				index++;
				moveDone++;
				
				if( x < this.listLocation.getAnterior().getX() + 10 * scale )
					this.listLocation = this.listLocation.getAnterior();	
			}
			else
				this.isMoving = false;
				
		}
		else{
			
			x -= speed * scale;
			index++;
			moveDone++;
		}
	
		if( move <= this.moveDone )
			this.isMoving = false;
		
	}
	
	public void walkUp(){ 
		
		if( y - speed * scale < this.listLocation.getY() - 20 * scale ){
			
			if((this.listLocation.getArriba().getDato() == 0  ||
				this.listLocation.getArriba().getDato() == 10 ||
			   ((this.listLocation.getArriba().getDato() == 5 || 
				this.listLocation.getArriba().getDato() == 6 ||
				this.listLocation.getArriba().getDato() == 7 ||
				this.listLocation.getArriba().getDato() == 8 ) &&
				!this.listLocation.getArriba().getHasItem() )) && 
				this.x == this.listLocation.getX() && 
				this.x + 20 * scale == this.listLocation.getX() + 20 * scale ){
				y -= speed * scale;
				index++;
				moveDone++;
				
				if( y + 30 * scale <= this.listLocation.getY() )
					this.listLocation = this.listLocation.getArriba();
			}
			else
				this.isMoving = false;
			
		}
		else{
			y -= ( speed * scale );
			index++;
			moveDone++;
		}
		
		if( move <= this.moveDone )
			this.isMoving = false;
		
	}
	
	public void walkDown(){ 
		
		if( y + speed * scale > this.listLocation.getY() ){

			if( (this.listLocation.getAbajo().getDato() == 0  ||
				this.listLocation.getAbajo().getDato() == 10 ||
				((this.listLocation.getAbajo().getDato() == 5 || 
				this.listLocation.getAbajo().getDato() == 6 ||
				this.listLocation.getAbajo().getDato() == 7 ||
				this.listLocation.getAbajo().getDato() == 8 ) &&
				!this.listLocation.getAbajo().getHasItem() )) &&
				this.x == this.listLocation.getX() && 
				this.x + 20 * scale == this.listLocation.getX() + 20 * scale ){
				y += speed * scale;
				index++;
				moveDone++;
				
				if( y + 20 * scale > this.listLocation.getY() )
					this.listLocation = this.listLocation.getAbajo();
			
			}
			else
				this.isMoving = false;
		}
		else{
			y += speed * scale;
			index++;
			moveDone++;
		}

		if( move <= this.moveDone )
			this.isMoving = false;
		
	}
	
	public synchronized void playSoundStep(){
		
		try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/step_enemy.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e1){}
		
	}
	
	public synchronized void playSoundDead(){
		
		try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/dead_enemy.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e1){}
		
	}
	
	@Override
	public void run() {
		
		while( isAlive ){
			
			// si no se está moviendo ejecutar el método que diga hacia a donde va a ir
			// si no se esta moviendo ejecutar el metodo que vaya moviendo los x o y dependiendo
			// hacia donde se vaya a mover
			
			if( isMoving ){ // ejecutar el metodo que se mueva hacia donde vaya el enemigo
				/* abajo
				 * arriba
				 * izquierda
				 * derecha
				 * */
				if( !isAlive )
					break;
				
				try { Thread.sleep( 200 ); } catch (InterruptedException e) { }
				
				if( !dontMove ){
					
					// this.playSoundStep();
					
					switch( side ){
						case 0:// abajo
							this.walkDown();
							break;
						case 1:// arriba
							this.walkUp();
							break;
						case 2:// izquierda
							this.walkLeft();
							break;
						case 3:// derecha
							this.walkRight();
							break;
					}
				}
			}
			else{
				// crear un lado hacia donde se moverá el enemigo
				// crear una cantidad de cuanto se moverá el enemigo
				// poner el moveDone en 0 
				
				java.util.Random random = new java.util.Random( );

				boolean done = false;
				do{
					done = false;
					this.side = random.nextInt( 4 );
					
					if( !isAlive )
						break;
					
					switch( this.side){
						
						case 0: // abajo
							try{
								if( this.listLocation.getAbajo().getDato() == 0 ||
									this.listLocation.getAbajo().getDato() == 10 ||
									(this.listLocation.getAbajo().getDato() == 5 || 
									this.listLocation.getAbajo().getDato() == 6 ||
									this.listLocation.getAbajo().getDato() == 7 ||
									this.listLocation.getAbajo().getDato() == 8 ) && 
									!this.listLocation.getAbajo().getHasItem() )
									done = true;
							}catch( Exception e ){}
							
							break;
						case 1: // arriba
							
							try{
								if( this.listLocation.getArriba().getDato() == 0 ||
									this.listLocation.getArriba().getDato() == 10 ||
									(this.listLocation.getArriba().getDato() == 5 || 
									this.listLocation.getArriba().getDato() == 6 ||
									this.listLocation.getArriba().getDato() == 7 ||
									this.listLocation.getArriba().getDato() == 8 ) && 
									!this.listLocation.getArriba().getHasItem() )
									done = true;
							}catch( Exception e ){}
							
							
							break;
						case 2:// izquierda
							try{
								if( this.listLocation.getAnterior().getDato() == 0 ||
									this.listLocation.getAnterior().getDato() == 10 ||
									(this.listLocation.getAnterior().getDato() == 5 || 
									this.listLocation.getAnterior().getDato() == 6 ||
									this.listLocation.getAnterior().getDato() == 7 ||
									this.listLocation.getAnterior().getDato() == 8 ) && 
									!this.listLocation.getAnterior().getHasItem() )
									done = true;
							}catch( Exception e ){}
							
							break;
						case 3:
							try{
								if( this.listLocation.getSiguiente().getDato() == 0 ||
									this.listLocation.getSiguiente().getDato() == 10 ||
								(this.listLocation.getSiguiente().getDato() == 5 || 
								this.listLocation.getSiguiente().getDato() == 6 ||
								this.listLocation.getSiguiente().getDato() == 7 ||
								this.listLocation.getSiguiente().getDato() == 8 ) && 
								!this.listLocation.getSiguiente().getHasItem() )
								done = true;
							}catch( Exception e ){}
							break;
					
					}
						
					if( !done && isAlive )
						try { Thread.sleep( 1500 ); } catch (InterruptedException e) { }
					
				}while( !done );
				
				if( !isAlive )
					break;
				
				if( this.side == 0 || this.side == 1 )
					do{ this.move = random.nextInt( 10 ) * 6; }while( this.move == 0);
				else
					do{ this.move = random.nextInt( 10 ) * 4; }while( this.move == 0);
				
				this.moveDone = 0;
				this.isMoving = true;
				
				try { Thread.sleep( 1000 ); } catch (InterruptedException e) { }
				
			}
			
		}
		
		this.isAnimationDead = false;
		this.side = 4;
		this.playSoundDead();
		try { Thread.sleep( 2000 ); } catch (InterruptedException e) { e.printStackTrace(); }
		this.isAnimationDead = true;
		
	}
	
}
