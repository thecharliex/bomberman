package Map;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Nodo {
	
	private Image image;
	private Image backImage;
	private int x;
	private int y;
	private double SCALE;
	private int dato;
	private int mapImage;
	private boolean hasItem = false;
	
	private Nodo arriba = null;
	private Nodo abajo = null;
	private Nodo siguiente = null;
	private Nodo anterior = null;
	
	public Nodo() { }
	
	public Nodo(int y , int x , int image , double scale , int mapImage ) {
	
		this.SCALE = scale;
		this.x = (int) ( x * 40 * ( SCALE / 2) ) ;
		this.y = (int) ( y * 60 * ( SCALE / 2 ) + 60 * ( SCALE  / 2 ) );
		this.dato = image;
		this.mapImage = mapImage;
		if( dato == 5 || dato == 6 || dato == 7 || dato == 8 )
			hasItem = true;
		
		BufferedImage img;
		try { 
			
			if( mapImage == 1 )
				img = ImageIO.read(new File("gfx/tile1.png"));
			else if( mapImage == 2 )
				img = ImageIO.read(new File("gfx/tile2.png")); 
			else
				img = ImageIO.read(new File("gfx/tile3.png"));
			
			switch( image ){
				case 0:
					this.image = img.getSubimage( 0 , 0 , 40 , 60 );
					this.backImage = null;
					break;
				case 1:
					this.backImage = img.getSubimage( 0 , 0 , 40 , 60 );
					this.image = img.getSubimage( 40 , 0 , 40 , 60 );
					break;
				case 2:
					this.image = img.getSubimage( 80 , 0 , 40 , 60 );
					this.backImage = img.getSubimage( 0 , 0 , 40 , 60 );
					break;
				case 3:
					this.image = img.getSubimage( 120 , 0 , 40 , 60 );
					this.backImage = img.getSubimage( 0 , 0 , 40 , 60 );
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					this.backImage = img.getSubimage( 0 , 0 , 40 , 60 );
					this.image = img.getSubimage( 120 , 0 , 40 , 60 );
					break;
				default:
					this.image = img.getSubimage( 0 , 0 , 40 , 60 );
			}
		}
		catch (IOException e) { e.printStackTrace(); } 
		
	}
	
	public void setDato(int dato){ this.dato = dato; }
	public int getDato(){ return this.dato; }
	public Image getImage(){ return image; }
	public void setImage( Image img ){ this.image = img; }
	public int getMapImage(){ return mapImage; }
	public boolean getHasItem(){ return hasItem; }
	public Image getBackImage(){ return backImage; }
	
	public int getX(){ return x; }
	public void setX( int x ){ this.x = x; }
	public int getY(){ return y; }
	public void setY( int y ){ this.y = y; }
	public double getScale(){ return SCALE; }
	public void setHasItemFalse(){ hasItem = false; }
	
	public void setArriba(Nodo nodo){ arriba = nodo; }	
	public void setAbajo(Nodo nodo){ abajo = nodo; }
	public void setSiguiente(Nodo nodo){ siguiente = nodo; }
	public void setAnterior(Nodo nodo){ anterior = nodo; }
	
	public Nodo getArriba(){ return arriba; }
	public Nodo getAbajo(){ return abajo; }
	public Nodo getSiguiente(){ return siguiente; }
	public Nodo getAnterior(){ return anterior; }
	
	public static void setLinks( Nodo set , Nodo up, Nodo left , Nodo right , Nodo down){
		
		up.setAbajo( set );
		left.setAnterior( set );
		right.setSiguiente( set );
		down.setArriba( set );
		
		set.setArriba( up );
		set.setAnterior( left );
		set.setSiguiente( right );
		set.setAbajo( down );
		
	}
	
	public void deleteLinks(){
		this.arriba = null;
		this.anterior = null;
		this.siguiente = null;
		this.abajo = null;
	}
	
}
