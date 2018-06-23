import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Iterator;

public class InvadersApplication extends JFrame implements Runnable, KeyListener
{
	private static final Dimension WindowSize = new Dimension(800, 600);
	private static final int NUMALIENS = 30;
	private Alien[] aliens = new Alien[NUMALIENS];
	private Spaceship Playership;
	private Image image1;
	private Image image2;
	private Image image3;
	private static String workingDirectory;
	private boolean isGraphicsInitialised=false;
	private BufferStrategy strategy;
	private Image bulletImage;
	private ArrayList bulletsList = new ArrayList();
	private Graphics offscreenGraphics;
	private PlayerBullet b;
	private int score=0;
	private int enemyWave=1;
	private int highScore=0;
	private boolean isGameInProgress=false;
	
	public InvadersApplication()
	{
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) screensize.getWidth()/2 - WindowSize.width/2;
		int y = (int) screensize.getHeight()/2 - WindowSize.height/2;
		setBounds(x,  y, WindowSize.width, WindowSize.height);
		setVisible(true);
		
		 
		this.setTitle("Space Invaders");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		 
		
		
		ImageIcon icon = new ImageIcon(workingDirectory + "//alien_ship_1.png");
 
		image2 = icon.getImage();
		
		ImageIcon icon2 = new ImageIcon(workingDirectory + "//alien_ship_2.png");
		image3 = icon2.getImage();
		
		
		for(int i = 0; i < NUMALIENS; i++){
			
			    aliens [i] = new Alien(image2,image3,800);
			}
		
		
		
		ImageIcon icon1 = new ImageIcon(workingDirectory + "//player.png");
		image1 = icon1.getImage();
		Playership = new Spaceship(image1, 600);
		Playership.setPosition(300, 500);
		
		ImageIcon icon3 = new ImageIcon(workingDirectory + "//bullet.png");
		bulletImage = icon3.getImage();
		
		b = new PlayerBullet(bulletImage,WindowSize.width);
		b.setPosition(Playership.x+54/2, Playership.y);

		
		addKeyListener(this);
		
	
		
		Thread t = new Thread(this);
		t.start();
		
		createBufferStrategy(2);
		strategy=getBufferStrategy();
		offscreenGraphics = strategy.getDrawGraphics();
		
		isGraphicsInitialised=true;
	}

	
	
	
	public void keyTyped(KeyEvent e)
	{
		
	}

	
	public void keyPressed(KeyEvent e)
	{
		if(isGameInProgress=true) {
			
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			Playership.setXSpeed(5);
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			Playership.setXSpeed(-5);
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			shootBullet();
		}
		
		} else {
			newGame();
		}
	}

	
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
			Playership.setXSpeed(0);
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			shootBullet();
		}
	}

	public void run()
	{
		while(true)
		{
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
           boolean anyAliensAlive = false;
           boolean x=false;
			for(int i=0; i<NUMALIENS; i++) {
			if(aliens[i].move()) {
				x=true;
				if(((aliens[i].x<b.x && aliens[i].x + image2.getWidth(null)>b.x) || (b.x<aliens[i].x && b.x + bulletImage.getWidth(null) > aliens[i].x)) && (aliens[i].y<b.y && aliens[i].y + image2.getHeight(null)>b.y) || (b.y<aliens[i].y && b.y + bulletImage.getHeight(null) > aliens[i].y))
				bulletsList.remove(b);
				b.setPosition(-30,30);
				score = score+10;
			}
				
			}
			
			if(x) {
				Alien.reverseDirection();
				for(int i=0; i<NUMALIENS; i++) {
					aliens[i].jumpDownwards();

				}
			}
			
			if(!anyAliensAlive) {
				enemyWave++;
				newWave();
			}
				
			Playership.move();	
			shootBullet();	
			
			Iterator iterator = bulletsList.iterator();
			while(iterator.hasNext()){
			PlayerBullet b = (PlayerBullet) iterator.next();
			b.move();
			}
			
			this.repaint();
		}
	}
	
	public void newWave() {
		for (int i=0; i<NUMALIENS; i++) {
			double xx=i%5*80+70;
			double yy=i/5*40+50;
			
			aliens[i].setPosition(xx, yy);
			aliens[i].setXSpeed(1+enemyWave);
			aliens[i].framesDrawn=0;
		}
		Playership.setPosition(300,530);
	}
	
	public void newGame() {
		enemyWave=1;
		score=0;
		isGameInProgress=true;
		newWave();
	}
	
	
	public void shootBullet() {
		// add a new bullet to our list
		
		bulletsList.add(b);
		b.move();
		}
	
	public void paint(Graphics g)
	{
		if(!isGraphicsInitialised) 
			return;
		
		g=offscreenGraphics;
		
		g.setColor(Color.BLACK);
		g.fillRect(0,  0,  800,  600);
		
		if(isGameInProgress=true) {
		for(int i=0; i<NUMALIENS; i++){
			aliens[i].paint(g);
		}
		
		Playership.paint(g);
		
		strategy.show();
		g.dispose();
		Iterator iterator = bulletsList.iterator();
		while(iterator.hasNext()){
		PlayerBullet b = (PlayerBullet) iterator.next();
		b.paint(g);
		
		g.setColor(Color.WHITE);
		writeString(g,WindowSize.width/2,60,30,"Score: "+score+"   Best:"+highScore);	
		}
		
		} else {
			g.setColor(Color.white);
			writeString(g,WindowSize.width/2,200,60,"Game Over");
			writeString(g,WindowSize.width/2,300,30,"Press any key to play");
			writeString(g,WindowSize.width/2,350,25,"[Arrow keys to move, space to fire]");
		}
		
		
		strategy.show();
	}
	
	private void writeString(Graphics g, int x, int y, int fontSize, String message) {
		Font f = new Font("Times", Font.PLAIN, fontSize);
		g.setFont(f);;
		FontMetrics fm = getFontMetrics(f);
		int width = fm.stringWidth(message);
		g.drawString(message, x-width/2, y);
	}




	
	public static void main(String[] args)
	{
		workingDirectory = System.getProperty("user.dir");
		System.out.println(workingDirectory);
		InvadersApplication Assignment4 = new InvadersApplication();
	}
}

