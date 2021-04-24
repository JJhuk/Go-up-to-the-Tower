package main.java.com.game;

import main.java.com.game.util.KeyHandler;
import main.java.com.game.util.MouseHandler;
import main.java.com.game.states.GameStateManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	public static int width;
	public static int height;
	public static int frames;

	public static int oldFrameCount;
	
	private Thread thread;
	private boolean running = false;
	
	private BufferedImage img;
	private	Graphics2D g;
	private MouseHandler mouse;
	private KeyHandler key;
	private GameStateManager gsm;


	public GamePanel(int width,int height ) {
		GamePanel.width = width;
		GamePanel.height = height;
		setPreferredSize(new Dimension(width,height));
		setFocusable(true);
		requestFocus();
		
	}

	public void addNotify() {
		super.addNotify();

		if(thread==null) {
			thread = new Thread(this,"GameThread");
			thread.start();
			
		}
	}
	
	public void init() throws Exception {
		running = true;
		
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) img.getGraphics();
      
     	mouse = new MouseHandler(this);
		key = new KeyHandler(this);
		gsm = new GameStateManager();
	}
	@Override
	public void run() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }

		long initialTime = System.nanoTime();
		int UPS = 25;
		int FPS = 60;
		double div = 1000000000;
		final double timeU = div / UPS;
		final double timeF = div / FPS;
		double deltaU = 0, deltaF = 0;
		long timer = System.currentTimeMillis();
      
        oldFrameCount = 0;
		
		while(running) {
			long currentTime = System.nanoTime();
			deltaU += (currentTime - initialTime) /timeU;
			deltaF += (currentTime - initialTime) / timeF;
			initialTime = currentTime;

			if(deltaU >=1) {
                try {
                    input(mouse,key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
				try {
                    update();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
				deltaU--;
			}

			if(deltaF >=1) {
				render();
				draw();
				frames++;
				deltaF--;
			}
			if(System.currentTimeMillis() - timer > 1000) {
				frames = 0;
				timer += 1000;
			}
		}
	}
	
	public void update() throws CloneNotSupportedException {
		gsm.update();
	}
	
	public void input(MouseHandler mouse, KeyHandler key) throws Exception {

		gsm.input(mouse, key);
	}

	public void render() {
		if(g != null) {
			g.setColor(Color.BLACK);
			g.fillRect(0,0,width,height);
			gsm.render(g);
		}
	}
	
	public void draw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(img,0,0,width,height,null);
		g2.dispose();
	}
}
