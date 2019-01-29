package modelPackage;


import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import modelPackage.*;

/**
 * Created by Hans Dulimarta on Summer 2014
 */
public class GUI implements KeyListener, ActionListener {

    private final static int DEFAULT_SIZE = 4;
    private JFrame top;
    private JPanel gamePanel;
    private NumberTile[][] gameBoardUI;
    private NumberSlider gameLogic;
    private JMenuItem reset, quit;
    public GUI()
    {
    	
    	
        top = new JFrame ("Blake Lapum   1024");
 
        
        resizeBoard();

        
        JMenuBar mb = new JMenuBar();
        top.setJMenuBar(mb);
        JMenu game = new JMenu("Game");
        mb.add(game);
        reset = new JMenuItem ("Reset");
        reset.addActionListener(this);
        quit = new JMenuItem("Quit");
        quit.addActionListener(this);
        game.add(reset);
        game.addSeparator();
        game.add(quit);

        update();
        top.pack();
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        top.setVisible(true);
        
 //       gamePanel.requestFocus(); 
        gamePanel.setFocusable( true );
    }
    
    public void resizeBoard()
    {
		this.gameLogic = new NumberGame(DEFAULT_SIZE, DEFAULT_SIZE, 1024);
        
     // gameLogic = new NumberGame( );
      if (gameLogic == null) {
          JOptionPane.showMessageDialog(top, "Did you read the TODO comment?");
          System.exit(0);
      }

      gameLogic.resizeBoard(DEFAULT_SIZE, DEFAULT_SIZE, 1024);
      gamePanel = new JPanel();
      gamePanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
      gamePanel.setLayout(new GridLayout(DEFAULT_SIZE, DEFAULT_SIZE));
      gamePanel.addKeyListener(this);
      top.add(gamePanel, BorderLayout.CENTER);

      gameBoardUI = new NumberTile[DEFAULT_SIZE][DEFAULT_SIZE];
      for (int k = 0; k < gameBoardUI.length; k++)
          for (int m = 0; m < gameBoardUI[k].length; m++)
          {
              gameBoardUI[k][m] = new NumberTile("X", JLabel.CENTER, gameLogic.getCell(k,m) );
              
              gamePanel.add(gameBoardUI[k][m]);
          }
    }
    
    /**
     * Updates the gameBoardUI
     */
	public void update() {
		for (int k = 0; k < gameBoardUI.length; k++){
	          for (int m = 0; m < gameBoardUI[k].length; m++)
	          {
	        	  NumberTile test1 = new NumberTile("test", JLabel.CENTER, gameLogic.getCell(k, m));
	          gameBoardUI[k][m] = test1;
	          }
		}

	}


    @Override
    public void keyPressed(KeyEvent e) {
        boolean moved = false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            	gameLogic.slide(SlideDirection.UP);
            	moved = true;
                break;
            case KeyEvent.VK_LEFT:
            	gameLogic.slide(SlideDirection.LEFT);
            	moved=true;
                break;
            case KeyEvent.VK_DOWN:
            	gameLogic.slide(SlideDirection.DOWN);
            	moved=true;
                break;
            case KeyEvent.VK_RIGHT:
            	gameLogic.slide(SlideDirection.RIGHT);
            	moved=true;
                break;
            case KeyEvent.VK_U:
                try {
                    System.out.println ("Attempt to undo");
                    gameLogic.undo();
                    moved = true;
                }
                catch (IllegalStateException exp) {
                    JOptionPane.showMessageDialog(top, "Can't undo beyond the first move");
                    moved = false;
                }
        }
        if (moved) {
			gameLogic.placeRandomValue();

            update();
            GameStatus test = GameStatus.USER_WON;
            if(test == gameLogic.getStatus()){
            	
            }
        }
    }

    
    // NOTHING TO DO IN THE FOLLOWING TWO METHODS
    @Override
    public void keyTyped(KeyEvent e) {
    }   
    @Override
    public void keyReleased(KeyEvent e) {
    }

    
    @Override
    public void actionPerformed (ActionEvent e)
    {
        Object source = e.getSource();
        
        
        
        
        
        
        System.exit(0);   // TO QUIT

    }
    
    
    public static void main (String[] args)
    {
        new GUI();
    }
}

