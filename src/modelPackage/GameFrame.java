package modelPackage;

import java.awt.BorderLayout;
/****
 * 
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import modelPackage.GameStatus;
import modelPackage.NumberGame;
import modelPackage.NumberSlider;
import modelPackage.SlideDirection;
/*****
 * 
 * @author Nicholas and Blake
 *
 */

public class GameFrame extends JFrame {

	private JMenuItem reset, quit, undo,stats;
	private NumberSlider gameLogic;
	private GamePanel gamePanel;
	private NumberGame numberGame;
	private final static int DEFAULT_SIZE = 4;
	public int numberslides = 0, numbergames = 0, currentslides = 0, win = 0, lost = 0, highscore = 0;
	/****
	 * 
	 */
	public GameFrame() {
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*--------------------------------------------------------
		 * Make the game model first, so that the cells exist 
		 * for sharing in the NumberTile class.
		 * -------------------------------------------------------
		 */
		
		this.gameLogic = new NumberGame(DEFAULT_SIZE, DEFAULT_SIZE, 1024); 
		
		 

		
		
		
		
		if (this.gameLogic == null) {
			JOptionPane.showMessageDialog(this, "Did you read the TODO comment, above?");
			System.exit(0);
		}

		this.gamePanel = new GamePanel(this.gameLogic, DEFAULT_SIZE);
		this.add(this.gamePanel, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);

		ActionListener menuListener = new MenuListener();

		this.reset = new JMenuItem("Reset");
		this.reset.addActionListener(menuListener);

		this.quit = new JMenuItem("Quit");
		this.quit.addActionListener(menuListener);
		
		this.undo = new JMenuItem("Undo");
		this.undo.addActionListener(menuListener);

		this.stats = new JMenuItem("Statistics");
		this.stats.addActionListener(menuListener);
		
		gameMenu.add(this.reset);
		gameMenu.addSeparator();
		gameMenu.add(this.quit);
		gameMenu.addSeparator();
		gameMenu.add(this.undo);
		gameMenu.addSeparator();
		gameMenu.add(this.stats);

		ArrowKeyListener keyListener = new ArrowKeyListener();
		this.addKeyListener(keyListener);

		this.pack();
		this.setVisible(true);

		gamePanel.update();

		this.setFocusable(true);
	}
	private class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Object eventSource = e.getSource();
			if (eventSource == reset) {
				gameLogic.reset();
				gamePanel.update();
			}
			else if(eventSource== undo){
				gameLogic.undo();
				gamePanel.update();
			
			}
			else if(eventSource==stats){
				if(numbergames == 0)
					numbergames=1;
				JOptionPane.showMessageDialog(stats, "Number of Slides: " + currentslides + " Number of Games: " + numbergames + " Number of Wins: " + win + " Number of Games Lost: " + lost + " Highest Score: " + highscore);
			}
			else {
				System.exit(1);
			}
		}
	}
	
	
	private class ArrowKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {

			boolean moved = false;

			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:	

				gameLogic.slide(SlideDirection.UP);	
				moved = true;
				break;
			case KeyEvent.VK_DOWN:
				gameLogic.slide(SlideDirection.DOWN);
				moved=true;
				break;
			case KeyEvent.VK_LEFT: 
				gameLogic.slide(SlideDirection.LEFT);
				moved=true;
				break;
				
			case KeyEvent.VK_RIGHT:
				gameLogic.slide(SlideDirection.RIGHT);
				moved=true;
				break;


			}
			if (moved = true) {
				currentslides =currentslides +1;
				gamePanel.update();
								
				
							
				
		
			}
			GameStatus test = GameStatus.USER_WON;
			
			if(gameLogic.getStatus().equals(GameStatus.USER_WON)){
				JOptionPane.showMessageDialog(null, "User Has WON!");
				
				numbergames =numbergames +1;
				int response = JOptionPane.showConfirmDialog(null,"Would You Like to play again?","Play again?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(response == JOptionPane.YES_OPTION){
					if( currentslides>highscore)
						highscore = currentslides;
					numbergames = numbergames +1;
					win = win+1;
					gameLogic.reset();
					gamePanel.update();
					
				}
				else
					System.exit(0);
			}
			if(gameLogic.getStatus().equals(GameStatus.USER_LOST)){
				JOptionPane.showMessageDialog(null,"User Has LOST!");
				int response = JOptionPane.showConfirmDialog(null,"Would You Like to play again?","Play again?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(response == JOptionPane.YES_OPTION){
					//highscore = currentslides;
					numbergames =numbergames +1;
					numberslides = numberslides +currentslides;
					
					lost = lost +1;
					numbergames = numbergames +1;
					
					gameLogic.reset();
					gamePanel.update();
					
				}
				else
					System.exit(0);
			}
			
			/* if the player wants to play again,
			 * 
			 *  1) reset the game logic 
			 *  2) update the gamePanel
			 *  
			 *  otherwise
			 *  
			 *  System.exit(0);
			 */


		}
		
		

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

	public static void main(String[] args) {
		new GameFrame();
	}

}
