package Map;

import java.awt.Graphics;
import java.util.ArrayList;

import Enemies.Enemy;
import Player.Bomberman;

public class ListaOrtogonal {
	
	private Nodo head;
	public ListaOrtogonal(){ }
	
	//******************************************************************************************
	// CREAR
 	public void crear( int datos[][] , Bomberman bomberman , double scale , int mapImage , ArrayList<Enemy> enemies){
		
 		if( this.head != null)
 			this.head = null;
 		
		if(datos != null){
			
			for(int x = 0; x < datos.length; x++){
				
				for(int y = 0; y < datos[x].length; y++){
					
					Nodo p = new Nodo( x , y , datos[x][y] , scale , mapImage );
					Nodo q = head;
					
					// agrega el nodo donde esta el bomberman
					// agrega el nodo de de los enemigos
					switch( datos[x][y] ){
						case 10:
							bomberman.setListLocation( p );
							bomberman.setXYLocation();
							break;
						case 11:
							Enemy enemy11 = new Enemy( bomberman , 11 , p.getX() , p.getY() , scale );
							enemy11.setListLocation( p );
							enemies.add( enemy11 );
							break;
						case 12:
							Enemy enemy12 = new Enemy( bomberman , 12 , p.getX() , p.getY() , scale );
							enemy12.setListLocation( p );
							enemies.add( enemy12 );
							break;
						case 13:
							Enemy enemy13 = new Enemy( bomberman , 13 , p.getX() , p.getY() , scale );
							enemy13.setListLocation( p );
							enemies.add( enemy13 );
							break;	
					}
					
					
					if(x == 0 && y == 0)
						 head = p;
					else{
						
						if( y == 0 ){
				
							while(q.getAbajo() != null) 
								q = q.getAbajo();
				
							q.setAbajo(p);
							p.setArriba(q);
						}
						else{
							
							if( x == 0 ){
								
								while(q.getSiguiente() != null)
									q = q.getSiguiente();

								q.setSiguiente(p);
								p.setAnterior(q);
								
							}
							else{
								
								while(q.getAbajo() != null) { q = q.getAbajo(); }
								while(q.getSiguiente() != null) { q = q.getSiguiente(); }
								
								q.setSiguiente(p);
								p.setAnterior(q);
								
								if(q.getArriba() != null && q.getArriba().getSiguiente() != null){
									q = q.getArriba().getSiguiente();
									q.setAbajo(p);
									p.setArriba(q);	
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	// DESPLEGAR
	public void desplegar(){
		
		if(head != null){
			Nodo p = head;
			Nodo q = head;
			
			if(p.getAbajo() != null){
				
				while(p.getAbajo() != null){
					
					q = p;
					
					while(q.getSiguiente() != null){
						
						System.out.print(q.getDato() + "\t");
						q = q.getSiguiente();
					}
					
					System.out.println(q.getDato());
					p = p.getAbajo();
				}
				
				q = p;
				
				while(q.getSiguiente() != null){
					
					System.out.print(q.getDato() + "\t");
					q = q.getSiguiente();
				}
				
				System.out.println(q.getDato());
				
			}
			else{
			
				while(q.getSiguiente() != null){
					System.out.print(q.getDato() + "\t");
					q = q.getSiguiente();
				}
				
				System.out.println(q.getDato());

			}
		}
		else
			System.out.println("Error: Lista Vacia");
		
	}
	
	// RENDERIZA EL MAPA
	public void renderList( Graphics g ){
		
		Nodo p = head;
		Nodo q = head;
		
		if(p.getAbajo() != null){
			
			while(p.getAbajo() != null){
				
				q = p;
				
				while(q.getSiguiente() != null){
					
					if( q.getBackImage() != null ){
						
						g.drawImage( q.getBackImage(), q.getX() , 
								 q.getY() , (int)(q.getX() + 40 * (q.getScale() / 2) ) , 
								 (int)(q.getY() + 60 * ( q.getScale() / 2) ) , 0 , 0 , 40 , 60 , null );
					}
					
					g.drawImage( q.getImage(), q.getX() , 
							 q.getY() , (int)(q.getX() + 40 * (q.getScale() / 2) ) , 
							 (int)(q.getY() + 60 * ( q.getScale() / 2) ) , 0 , 0 , 40 , 60 , null );
					q = q.getSiguiente();
				}
				
				
				if( q.getBackImage() != null ){
					
					g.drawImage( q.getBackImage(), q.getX() , 
							 q.getY() , (int)(q.getX() + 40 * (q.getScale() / 2) ) , 
							 (int)(q.getY() + 60 * ( q.getScale() / 2) ) , 0 , 0 , 40 , 60 , null );
				}
				
				g.drawImage( q.getImage(), q.getX() , 
					 q.getY() , (int)(q.getX() + 40 * ( q.getScale() / 2 )) , 
					 (int)(q.getY() + 60 * ( q.getScale() / 2 ) ) , 0 , 0 , 40 , 60 , null );
				p = p.getAbajo();
			}
			
			q = p;
			
			while(q.getSiguiente() != null){
				
				if( q.getBackImage() != null ){
					
					g.drawImage( q.getBackImage(), q.getX() , 
							 q.getY() , (int)(q.getX() + 40 * (q.getScale() / 2) ) , 
							 (int)(q.getY() + 60 * ( q.getScale() / 2) ) , 0 , 0 , 40 , 60 , null );
				}
				
				g.drawImage( q.getImage(), q.getX() , 
					q.getY() , (int)(q.getX() + 40 * ( q.getScale() / 2 )) , 
					 (int)(q.getY() + 60 * ( q.getScale() / 2 )) , 0 , 0 , 40 , 60 , null );
				q = q.getSiguiente();
			}
			
			if( q.getBackImage() != null ){
				
				g.drawImage( q.getBackImage(), q.getX() , 
						 q.getY() , (int)(q.getX() + 40 * (q.getScale() / 2) ) , 
						 (int)(q.getY() + 60 * ( q.getScale() / 2) ) , 0 , 0 , 40 , 60 , null );
			}
			
			g.drawImage( q.getImage(), q.getX() , 
				 q.getY() , (int)(q.getX() + 40 * ( q.getScale() / 2 )) , 
				 (int)(q.getY() + 60 * ( q.getScale() / 2 )) , 0 , 0 , 40 , 60 , null );
			
		}
		else{
		
			while(q.getSiguiente() != null){
				g.drawImage( q.getImage(), q.getX() , 
					 q.getY() , (int)(q.getX() + 40 * ( q.getScale() / 2 )) , 
					 (int)(q.getY() + 60 * ( q.getScale() / 2 ) ) , 0 , 0 , 40 , 60 , null );
				q = q.getSiguiente();
			}
			
			g.drawImage( q.getImage(), q.getX() , 
				 q.getY() , (int)(q.getX() + 40 * ( q.getScale() / 2 )) , 
				 (int)(q.getY() + 60 * ( q.getScale() / 2 )) , 0 , 0 , 40 , 60 , null );
		}
	}
	
}