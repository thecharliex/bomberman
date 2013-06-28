package Core;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class TitleGame extends JFrame implements Runnable, MouseListener{

	private double SCALE;
	private Thread thread;
	private boolean start;
	
	public TitleGame( double SCALE ){
		
		this.SCALE = SCALE;
		this.start = false;
		// this.getContentPane().setBackground( Color.DARK_GRAY );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setSize( (int) (380 * SCALE ) , (int) (300 * SCALE) );
		this.setLocationRelativeTo( null );
		this.setResizable( false );
		this.setUndecorated( true );
		this.setVisible( true );
		thread = new Thread( this );
		
		this.addMouseListener( this );
		
	}

	public double getScale(){ return SCALE; }
	public Thread getThread(){ return thread; }
	
	
	@Override
	public void run() {
		
		Graphics g = this.getGraphics();
		Image img;
		img = new ImageIcon("gfx/start_screen_0.png").getImage();
		g.drawImage( img , 0 , 0 , (int)( 380 * SCALE ) , (int)( 300 * SCALE ), null );
		
		try { Thread.sleep( 3000 ); }
		catch (InterruptedException e) { }
		
		img = new ImageIcon("gfx/title.png").getImage();
		g.drawImage( img , 0 , 0 , (int)( 380 * SCALE ) , (int)( 300 * SCALE ), null );
		
		img = new ImageIcon("gfx/start.png").getImage();
		g.drawImage( img , (int)( this.getWidth() / 2 - 70 * SCALE ) , (int)(this.getHeight() / 3 * 2 - 25 * SCALE ) , 
		(int)( 140 * SCALE ) , (int)( 50 * SCALE ), null );
		
		while( !start ){}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) { 
		
		if( ( e.getX() > (int)( this.getWidth() / 2 - 70 * SCALE ) &&
			  e.getX() < (int)( this.getWidth() / 2 - 70 * SCALE ) + (int)( 140 * SCALE ) ) && 
			( e.getY() > (int)(this.getHeight() / 3 * 2 - 25 * SCALE ) &&
			  e.getY() < (int)(this.getHeight() / 3 * 2 - 25 * SCALE ) + (int)( 50 * SCALE ) ) ){
			this.start = true;
			
			try {
	            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("sfx/start.wav"));
	            Clip clip = AudioSystem.getClip();
	            clip.open(audio);
	            clip.start();
	        } catch (Exception e1){}
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent arg0) { }
	
}
