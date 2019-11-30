package main.java.com.zeruls.game;

import javax.swing.JFrame;


public class Window extends JFrame {
	public Window() {
		super.setTitle("Juhyuk is dead");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setContentPane(new GamePanel(1024, 768));
		super.pack();
		super.setLocationRelativeTo(null);
		super.setVisible(true);
	}

}