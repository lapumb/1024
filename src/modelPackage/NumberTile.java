package modelPackage;

import javax.swing.border.BevelBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.util.Map;
import java.util.TreeMap;

import modelPackage.Cell;

public class NumberTile extends JLabel implements ActionListener {
	
    private Cell cell;

    static Font myTextFont = new Font(Font.SERIF, Font.BOLD, 40);    

    
    public NumberTile(String text, int horizontalAlignment, Cell cell) {
    	
        super(text, horizontalAlignment);
        this.cell = cell;
        this.setFont(myTextFont);
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.setPreferredSize(new Dimension (100, 100));
    }

    
    public void update()   
    {
    		if(this.cell != null && this.cell.value !=0){
    			this.setText("" + this.cell.value);
    		}
    		else{
    			this.setText("");
    		}
    	
    	
    	//Todo: Use the this.setText method to set the text on this 
    	//      NumberTile/JLabel to either the empty string or to a string
    	//      for the nonzero value of the cell.
   	
    	
    	
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
