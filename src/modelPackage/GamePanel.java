package modelPackage;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;

import modelPackage.NumberSlider;


public class GamePanel extends JPanel {

    private NumberTile[][] gameBoardUI;

    public GamePanel(NumberSlider gameLogic, int size) {

        this.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        this.setLayout(new GridLayout(size, size));
       
        gameBoardUI = new NumberTile[size][size];
        for (int k = 0; k < gameBoardUI.length; k++) {
            for (int m = 0; m < gameBoardUI[k].length; m++) {
            	           	
                gameBoardUI[k][m] = new NumberTile("X", JLabel.CENTER, gameLogic.getCell(k, m));
                this.add(gameBoardUI[k][m]);
            }
        }
    }


    /*
     * updates the game board
     */
    public void update() {

        for (int k = 0; k < gameBoardUI.length; k++) {
            for (int m = 0; m < gameBoardUI[k].length; m++) {
            	gameBoardUI[k][m].update();
            	

            }
        }
    }

}
