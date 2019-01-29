package modelPackage;


import java.util.Scanner;

/**
 * Created by Hans Dulimarta on Jun 27, 2016.
 * Edited by LJK, 2-2-2017
 */
public class TextUI {

	private NumberSlider game;
    private Scanner      input;
    
    
    public TextUI() {
        this.game = new NumberGame(4,4,64);

        if (this.game == null) {
            System.err.println ("*---------------------------------------------*");
            System.err.println ("| You must first modify the UI program.       |");
            System.err.println ("| Look for the first TODO item in TextUI.java |");
            System.err.println ("*---------------------------------------------*");
            System.exit(0xE0);
        }

        this.input = new Scanner(System.in);
    }



    /**
     * The main loop for playing a SINGLE game session. Notice that
     * the following method contains NO GAME LOGIC! Its main task is
     * to accept user input and invoke the appropriate methods in the
     * game engine.
     */
	public void playLoop() {

		boolean moved = false;
		
		this.game.renderBoard();

		this.input = new Scanner(System.in);
		String response = "a";

		while (!response.equals("Q")) {

			moved = false;
			
			/*
			 * To keep the right margin within 75 columns, we split the
			 * following long string literal into two lines
			 */
			System.out.print("Slide direction (W, D, S, A), " + "[U]ndo or [Q]uit? ");
			this.input = new Scanner(System.in);
			response = input.next().trim().toUpperCase();

			switch (response) {
			case "W": /* Up */
				moved = this.game.slide(SlideDirection.UP);
				break;
			case "D":
				moved = this.game.slide(SlideDirection.RIGHT);
				break;
			case "S":
				moved = this.game.slide(SlideDirection.DOWN);
				break;
			case "A":
				moved = this.game.slide(SlideDirection.LEFT);
				break;
			/*
			 * ----------------------------------------
			 * It is possible to undo the entire game.
			 * ----------------------------------------
			 */
			case "U":
				try {
					game.undo();
				} catch (IllegalStateException exp) {
					System.err.println("Can't undo that far");
				}
			}
			
			if (moved) {
				this.game.placeRandomValue();
			}
			
			this.game.renderBoard();

			if (this.game.getStatus() != GameStatus.IN_PROGRESS)
				break;
		}

		/* Almost done.... */
		switch (this.game.getStatus()) {
		case IN_PROGRESS:
			System.out.println("Thanks for playing!");
			break;
		case USER_WON:
			System.out.println("Congrats");
			break;
		case USER_LOST:
			System.out.println("Sorry....!");
			break;
		}
	}


    public static void main(String[] arg) {
        TextUI t = new TextUI();
        t.playLoop();
    }
}



