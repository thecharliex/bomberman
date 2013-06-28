package Core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Enemies.Enemy;
import Item.Bomb;
import Map.ListaOrtogonal;
import Map.Maps;
import Map.Nodo;
import Player.Bomberman;

public class Engine extends Canvas implements KeyListener{
	
	private JFrame frame;
	private Bomberman bomberman;
	private ArrayList<Enemy> enemies;
	private ListaOrtogonal maps;
	private ArrayList<Bomb> bombs;
	private ArrayList<Image> heartsSprite;
	private ArrayList<Image> bombsSprite;
	private ArrayList<Image> fireSprite;
	private int map;
	
	private double SCALE;
	private boolean isRunning = false;
	private int shouldRender = 0;
	private boolean isPaused = false;
	private long systemTimeSound = 0;
	private long systemTimeMove = 0;
	
	public Engine( double SCALE1 ){
		
		this.frame = new JFrame();
		this.SCALE = SCALE1;
		
		this.frame.setLayout( new BorderLayout());
		this.frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.frame.getContentPane().setBackground( Color.BLACK );
		this.frame.setSize( (int) (380 * SCALE) , (int) (300 * SCALE) );
		this.frame.setLocationRelativeTo( null );
		this.frame.add( this , BorderLayout.CENTER );
		this.frame.setResizable( false );
		this.frame.setUndecorated( true );
		this.frame.addKeyListener( this );
		this.addKeyListener( this );
		
		this.systemTimeSound = System.currentTimeMillis();
		this.systemTimeMove = System.currentTimeMillis();
		
		// carga las imagenes del HUD
		try {
			this.heartsSprite = new ArrayList<Image>();
			BufferedImage img = ImageIO.read(new File("gfx/hearts.png"));
			for( int x = 0; x < 300 ; x+=60)
				this.heartsSprite.add( img.getSubimage( x , 0 , 60 , 60 ) );
			
			this.bombsSprite = new ArrayList<Image>();
			img = ImageIO.read(new File("gfx/bombs.png"));
			for( int x = 0; x < 180 ; x+=60)
				this.bombsSprite.add( img.getSubimage( x , 0 , 60 , 60 ) );
			
			this.fireSprite = new ArrayList<Image>();
			img = ImageIO.read(new File("gfx/fire.png"));
			for( int x = 0; x < 180 ; x+=60)
				this.fireSprite.add( img.getSubimage( x , 0 , 60 , 60 ) );
		}
		catch (IOException e) { e.printStackTrace(); }
		
		// inicializa al jugador
		this.bomberman = new Bomberman( SCALE );
		
		// inicializa el arreglo de enemigos
		this.enemies = new ArrayList<Enemy>();
		
		// inicializa el arreglo de bombas
		this.bombs = new ArrayList<Bomb>();
		
		// inicializa el mapa
		maps = new ListaOrtogonal( );
		//maps.crear( new Maps( 1 ).getMap() , bomberman , SCALE , 1 );
		
	}
	
	public ListaOrtogonal getMapList(){ return maps; }
	public Bomberman getBomber(){ return bomberman; }
	public ArrayList<Enemy> getEnemies(){ return enemies; }

	public void setMap( int map ) { this.map = map; }
	
	public synchronized void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if( bs == null){
			this.createBufferStrategy( 3 );
			return;
		}
			
		Graphics g = bs.getDrawGraphics();
		
		
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint( RenderingHints.KEY_ALPHA_INTERPOLATION , RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT );
		}
		
		// liberar bombas
		ArrayList<Bomb> auxBomb = new ArrayList<Bomb>();
		for( Bomb b : this.bombs ) // agarra las bombas que ya explotaron
			if( b.getBlast() )
				auxBomb.add( b );
		for( Bomb b : auxBomb ) // remueve las bombas de el arreglo de bombas
			this.bombs.remove( b );
		
		// liberar enemigos
		ArrayList<Enemy> auxEnemy = new ArrayList<Enemy>();
		for( Enemy e : this.enemies ) // agarra los enemigos muertos
			if( !e.isAlive() && e.isAnimationDead())
				auxEnemy.add( e );
		for( Enemy e : auxEnemy ) // remueve los enemigos muertos del arreglo de bombas
			this.enemies.remove( e );
		
		// colision entre bombas
		this.collisionBetweenBombs();
		
		// colision entre bombas y enemigos
		this.collisionBetweenBombsEnemies();
		
		// renderiza el background
		if( shouldRender % 2 == 0)
			maps.renderList( g );
		
		// renderizar el HUD
		if( shouldRender % 2 == 0 && this.bomberman.getLifes() >= 1 )
			this.renderHUD( g );
			
		// renderizar las bombas
		try{
			if( shouldRender % 2 == 0 )
				for( Bomb b : this.bombs ){
					if( !b.getBlast() )
						b.renderBomb( g );
				}
		}catch( Exception e ){ }
		
		// renderizar los enemigos
		try{
			if( shouldRender % 2 == 0 )
				for( Enemy e : this.enemies ){
					e.renderEnemy( g );
				}
		}catch( Exception e ){ }
		
		// renderizar a bomberman
		if( shouldRender % bomberman.getHitEffect() == 0)
			bomberman.renderBomberMan( g );
		
		g.dispose();
		bs.show();
		
	}
	
	public void start(){
		this.isRunning = true;
		this.loop();
	}

	public void stop(){
		this.isRunning = false;
	}
	
	public void loop() {

		if( this.getBomber().getLifes() > 0 ){
			this.getGraphics().drawImage( new ImageIcon("gfx/start_screen_0.png").getImage() , 0 , 0 , (int)( 380 * SCALE ) , (int)( 300 * SCALE ), null );
			
			switch( map ){
				case 1:
					Image img1 = new ImageIcon("gfx/stage1.png").getImage();
					this.getGraphics().drawImage( img1 , 
							(int)( (this.frame.getWidth() / 2) - ( 150 / 2* this.SCALE ))  , 
							(int)( 100 * this.SCALE ) , 
							(int)( 150 * this.SCALE) , 
							(int)( 100 * this.SCALE ) , null);
					try { Thread.sleep( 1500 ); } catch (InterruptedException e) { }
					break;
				case 2:
					Image img2 = new ImageIcon("gfx/stage2.png").getImage();
					this.getGraphics().drawImage( img2 , 
							(int)( (this.frame.getWidth() / 2) - ( 150 / 2* this.SCALE ))  , 
							(int)( 100 * this.SCALE ) , 
							(int)( 150 * this.SCALE) , 
							(int)( 100 * this.SCALE ) , null);
					try { Thread.sleep( 1500 ); } catch (InterruptedException e) { }
					break;
				case 3:
					Image img3 = new ImageIcon("gfx/stage3.png").getImage();
					this.getGraphics().drawImage( img3 , 
							(int)( (this.frame.getWidth() / 2) - ( 150 / 2* this.SCALE ))  , 
							(int)( 100 * this.SCALE ) , 
							(int)( 150 * this.SCALE) , 
							(int)( 100 * this.SCALE ) , null);
					try { Thread.sleep( 1500 ); } catch (InterruptedException e) { }
					break;
			}
			map = 0;
			
			while( isRunning && this.bomberman.getLifes() > 0 ){
				
				while( !isPaused  && this.bomberman.getLifes() > 0){
					
					try { Thread.sleep( 15 ); }
					catch (InterruptedException e) { }
					
					shouldRender++;
					
					if( shouldRender > 100){
						shouldRender = 0;
						bomberman.displayStats();
						// this.maps.desplegar();
						System.out.println("Enemigos: " + this.enemies.size() );
						System.out.println("img Fire: " + this.fireSprite.size() );
						System.out.println("img bombs: " + this.bombsSprite.size() );
						System.out.println("********");
					}
					
					this.render();
					
					if( this.enemies.size() == 0 ){
						this.stop();
						System.out.println("Nivel terminado");
						break;
					}
				}
				
				if( isPaused){
					Image img = new ImageIcon("gfx/pause.png").getImage();
					this.getGraphics().drawImage( img , (int)( (this.frame.getWidth() / 2) - ( 150 / 2* this.SCALE ))  , (int)( 100 * this.SCALE ) , (int)( 150 * this.SCALE) , (int)( 100 * this.SCALE ) , null);
				}
			}
			
			this.bombs.clear();
			this.enemies.clear();
		}
		else{
			
			System.out.println( "Is Dead");
		}
		
	}

	public synchronized void playSoundStep(){
		
		if( System.currentTimeMillis() - this.systemTimeSound > 100 ){
			try {
	            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/step.wav"));
	            Clip clip = AudioSystem.getClip();
	            clip.open(audio);
	            clip.start();
	        } catch (Exception e1){}
			
			this.systemTimeSound = System.currentTimeMillis();
		}
		
	}

	public void playBackSound(){
		
		Thread player = new Thread( new Runnable(){
			@Override
			public void run() {
				
				Clip clip = null;
				try {
					AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/as_i_lay_dying_anodyne_sea.wav"));
					clip = AudioSystem.getClip();
		            clip.open(audio);
		            
				} catch (Exception e1){}
				
				clip.loop( 5 );
				
				/*while( bomberman.getLifes() > 0){ }
					if( !clip.isRunning() ){
						clip.start();
						System.out.println("Resume by Running");
					}
					
					
					if( !clip.isActive() ){
						clip.start();
						System.out.println("Resume by Active");
					}
				}*/
				//clip.stop();
				
			} });
		
		player.start();
	}
	

	@Override
	public void keyPressed(KeyEvent e) { 
		
		switch( e.getExtendedKeyCode() ){
			
			case KeyEvent.VK_UP:
				
				if( !isPaused){
					bomberman.setMovingTrue();
					bomberman.moveUp();
					bomberman.setSide( 1 );
					
					this.playSoundStep();
					
				}
				break;
			case KeyEvent.VK_DOWN:
				if( !isPaused){
					bomberman.setMovingTrue();
					bomberman.moveDown();
					bomberman.setSide( 0 );
					
					this.playSoundStep();
					
				}
				break;
			case KeyEvent.VK_RIGHT:
				if( !isPaused){
					bomberman.setMovingTrue();
					bomberman.moveRight();
					bomberman.setSide( 3 );
					this.playSoundStep();
				}
				break;
			case KeyEvent.VK_LEFT:
				if( !isPaused ){
					bomberman.setMovingTrue();
					bomberman.moveLeft();
					bomberman.setSide( 2 );
					this.playSoundStep();	
				}
				break;
			case KeyEvent.VK_P:
				if( isPaused ){
					isPaused = false;
					for( Enemy e1 : this.enemies )
						e1.setDontMoveFalse();
					
				}
				else{
					isPaused = true;
					
					for( Enemy e1 : this.enemies )
						e1.setDontMoveTrue();
					
					try {
			            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/pause.wav"));
			            Clip clip = AudioSystem.getClip();
			            clip.open(audio);
			            clip.start();
			        } catch (Exception e1){}
					
				}
				break;
			case KeyEvent.VK_SPACE:
				
				if( !isPaused ){
					if( this.bombs.size() < this.bomberman.getBombBag() ){
						boolean existBomb = false;
						for( Bomb b : this.bombs )
							if( bomberman.getListLocation() == b.getListLocation())
								existBomb = true;
						
						if( !existBomb )
							this.bombs.add( new Bomb( this.bomberman , SCALE ));
						
					}
				}
				break;
		}
	}
 
	@Override
	public void keyReleased(KeyEvent arg0) {
		bomberman.setMovingFalse();
	}

	@Override
	public void keyTyped(KeyEvent e ) { }

	public void collisionBetweenBombs(){
		
		/* checar que tanto alcanze tiene la bomba
		 * si el fuego choca con la bomba
		 * 		adelanta la explosion
		 * 		y hace que entre ellos no exista la flama fin
		 * */
		
		for( int i = 0 ; i < bombs.size() ; i++ ){
			
			if( i + 1 < bombs.size() ){
				Bomb bombA = bombs.get( i );
				Bomb bombB = bombs.get( i + 1 );
				
				if( bombA.getIndex() == 4 ){
					
					Nodo a = bombA.getListLocation();
					// checa hacia arriba del primer nodo
					for( int x = 0 ; x < bombA.getFire() && !bombA.getJustExpUp() ; x++ ){
						
						if( !(a.getArriba().getDato() == 1 || a.getArriba().getDato() == 2) )
							a = a.getArriba();
						
						if( a == bombB.getListLocation() ){
							bombA.setJustExpUp();
							bombB.setJustExpDown();
							if( bombB.getIndex() <= 3)
								bombB.setExplode();
						}
						
					}
					
					
					a = bombA.getListLocation();
					// checa hacia izquierda del primer nodo
					for( int x = 0 ; x < bombA.getFire() && !bombA.getJustExpLeft() ; x++ ){
						
						if( !(a.getAnterior().getDato() == 1 || a.getAnterior().getDato() == 2) )
							a = a.getAnterior();
						
						if( a == bombB.getListLocation() ){
							bombA.setJustExpLeft();
							bombB.setJustExpRight();
							if( bombB.getIndex() <= 3)
								bombB.setExplode();
						}
						
					}
					
					a = bombA.getListLocation();
					// checa hacia izquierda del primer nodo
					for( int x = 0 ; x < bombA.getFire() && !bombA.getJustExpRight() ; x++ ){
						
						if( !(a.getSiguiente().getDato() == 1 || a.getSiguiente().getDato() == 2) )
							a = a.getSiguiente();
						
						if( a == bombB.getListLocation() ){
							bombA.setJustExpRight();
							bombB.setJustExpLeft();
							if( bombB.getIndex() <= 3)
								bombB.setExplode();
						}
						
					}
					
					a = bombA.getListLocation();
					// checa hacia izquierda del primer nodo
					for( int x = 0 ; x < bombA.getFire() && !bombA.getJustExpDown() ; x++ ){
						
						if( !(a.getAbajo().getDato() == 1 || a.getAbajo().getDato() == 2) )
							a = a.getAbajo();
						
						if( a == bombB.getListLocation() ){
							bombA.setJustExpDown();
							bombB.setJustExpUp();
							if( bombB.getIndex() <= 3)
								bombB.setExplode();
						}
					}
					
				}
			}
		}
	}
	
	public void collisionBetweenBombsEnemies(){ 
		
		/* recorrer el arreglo de bombas
		 * 		si la bomba esta en la ultima explocion index == 7
		 * 			recorrer el arreglo de enemigos
		 * 				for para las flamas de todas las direcciones
		 * 				si una flama hacia cualquier lado toca al enemigo
		 * 					enemigo setDead
		 * */
		
		for( Bomb b : this.bombs ){
			
			if( b.getIndex() == 7 ){
				
				for( Enemy e : this.enemies ){
					
					Nodo aux = b.getListLocation();
					
					for( int i = 0 ; i < b.getFire() ; i++ ){
						
						if( aux == e.getListLocation() )
							e.setDead();
						
						if( aux.getSiguiente().getDato() == 0 || 
							aux.getSiguiente().getDato() == 10 || 
							(aux.getSiguiente().getDato() == 5 || 
							aux.getSiguiente().getDato() == 6 ||
							aux.getSiguiente().getDato() == 7 || 
							aux.getSiguiente().getDato() == 8 && 
							!aux.getSiguiente().getHasItem())){
							aux = aux.getSiguiente();
						}
					}
					
					if( aux == e.getListLocation() )
						e.setDead();
					
					aux = b.getListLocation();
					
					for( int i = 0 ; i < b.getFire() ; i++ ){
						
						if( aux == e.getListLocation() )
							e.setDead();
						
						if( aux.getAnterior().getDato() == 0 || 
							aux.getAnterior().getDato() == 10 || 
							(aux.getAnterior().getDato() == 5 || 
							aux.getAnterior().getDato() == 6 ||
							aux.getAnterior().getDato() == 7 || 
							aux.getAnterior().getDato() == 8 && 
							!aux.getAnterior().getHasItem())){
							aux = aux.getAnterior();
						}
					}
					
					if( aux == e.getListLocation() )
						e.setDead();
					
					aux = b.getListLocation();
					
					for( int i = 0 ; i < b.getFire() ; i++ ){
						
						if( aux == e.getListLocation() )
							e.setDead();
						
						if( aux.getArriba().getDato() == 0 || 
							aux.getArriba().getDato() == 10 || 
							(aux.getArriba().getDato() == 5 || 
							aux.getArriba().getDato() == 6 ||
							aux.getArriba().getDato() == 7 || 
							aux.getArriba().getDato() == 8 && 
							!aux.getArriba().getHasItem())){
							aux = aux.getArriba();
						}
					}
					
					if( aux == e.getListLocation() )
						e.setDead();
					
					aux = b.getListLocation();
					
					for( int i = 0 ; i < b.getFire() ; i++ ){
						
						if( aux == e.getListLocation() )
							e.setDead();
						
						if( aux.getAbajo().getDato() == 0 || 
							aux.getAbajo().getDato() == 10 || 
							(aux.getAbajo().getDato() == 5 || 
							aux.getAbajo().getDato() == 6 ||
							aux.getAbajo().getDato() == 7 || 
							aux.getAbajo().getDato() == 8 && 
							!aux.getAbajo().getHasItem())){
							aux = aux.getAbajo();
						}
					}
					
					if( aux == e.getListLocation() )
						e.setDead();
				}
			}
		}
		
	}
	
	public void renderHUD( Graphics g ){
		
		Image img = new ImageIcon("gfx/hud.png").getImage();
		// imagen del bomberman
		g.drawImage( img , 0, 0, 
						   (int)( 60 * (this.SCALE / 2)) , (int)( 60 * ( this.SCALE / 2 ) ) , 
							0 , 0 , 60, 60 ,
						   null);
		// corazones
		g.drawImage( this.heartsSprite.get( this.bomberman.getLifes() - 1 ) , 
					(int)( 60 * (SCALE / 2)), 0, (int)( 60 * (SCALE / 2)) * 2, (int)( 60 * (SCALE / 2)), 
					0 , 0 , 60, 60 ,
				   null);
		// bombas
		g.drawImage( this.bombsSprite.get( this.bomberman.getBombBag() - 1 ) , 
				(int)( 120 * (SCALE / 2)), 0, 
				(int)( 120 * (SCALE / 2)) + (int)( 60 * (SCALE / 2)) , (int)( 60 * (SCALE / 2)), 
				0 , 0 , 60, 60 ,
			   null);
		
		// fuego
		g.drawImage( this.fireSprite.get( this.bomberman.getFire() - 1 ) , 
				(int)( 180 * (SCALE / 2)), 0, 
				(int)( 180 * (SCALE / 2)) + (int)( 60 * (SCALE / 2)) , (int)( 60 * (SCALE / 2)), 
				0 , 0 , 60, 60 ,
			   null);
		
		
		// hud titulo
		img = new ImageIcon("gfx/hud_title.png").getImage();
		g.drawImage(  img , 
				(int)( 240 * (SCALE / 2)), 0, 
				(int)( 240 * (SCALE / 2)) + (int)( 280 * (SCALE / 2)) , (int)( 60 * (SCALE / 2)), 
				0 , 0 , 280, 60 ,
			   null);
		
	}
	
	public static void main(String[] args) {
		
		// yvalles@uabcs.mx
		
		double SCALE = 2;
		
		// inicializar la primera ventana
		TitleGame titleGame = new TitleGame( SCALE );
				
		titleGame.run();
		titleGame.dispose();
		
		// inicio del juego
		Engine game = new Engine( SCALE );
		game.playBackSound();
		game.frame.setVisible( true );
		
		for( int i = 1; i <= 3 ; i++){
			// game.getEnemies().clear();
			if( game.getBomber().getLifes() > 0 ){
				game.setMap( i );
				game.getMapList().crear( new Maps( i ).getMap() , game.getBomber() , SCALE , i , game.getEnemies() );
				game.start();
				
				System.out.println("Pasaste el nivel: " + i );
			}
			
		}
		
		if( game.getBomber().getLifes() > 0 ){
			game.getGraphics().drawImage( new ImageIcon("gfx/end.png").getImage() , 0 , 0 , (int)( 380 * SCALE ) , (int)( 300 * SCALE ), null );
			game.getGraphics().drawImage( new ImageIcon("gfx/clear.png").getImage() , 
					(int)( (game.frame.getWidth() / 2) - ( 150 / 2* game.SCALE ))  , 
					(int)( 100 * game.SCALE ) , 
					(int)( 150 * game.SCALE) , 
					(int)( 100 * game.SCALE ) , null);
			try { Thread.sleep( 10000 ); } catch (InterruptedException e) { }
			
		}
		else{
			game.getGraphics().drawImage( new ImageIcon("gfx/start_screen_0.png").getImage() , 0 , 0 , (int)( 380 * SCALE ) , (int)( 300 * SCALE ), null );
			game.getGraphics().drawImage( new ImageIcon("gfx/game_over.png").getImage() , 
					(int)( (game.frame.getWidth() / 2) - ( 150 / 2* game.SCALE ))  , 
					(int)( 100 * game.SCALE ) , 
					(int)( 150 * game.SCALE) , 
					(int)( 100 * game.SCALE ) , null);
			
			try { Thread.sleep( 5000 ); } catch (InterruptedException e) { }
			
		}
		
		game.frame.dispose();
		System.exit( 0 );
	}
	
}
