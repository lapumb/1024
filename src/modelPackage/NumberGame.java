package modelPackage;

import java.util.Random;
import java.util.Stack;
import modelPackage.NumberSlider;

public class NumberGame implements NumberSlider {

	private int goalValue;

	public Cell[][] rows; // rows contains the model for the 1024 game board.
	private Cell[] allCells; // allCells is a one-dimensional array of all Cells
								// on the board

	private GameStatus gameStatus;
	private Stack<Grid> grids;
	private boolean gridChanged;

	private static Random random = new Random();

	/*
	 * ------------------------------------------------------ For starters, the
	 * following code uses a 4 by 4 board.
	 * ------------------------------------------------------
	 */
	public NumberGame(int numRows, int numColumns, int goal) {
		this.resizeBoard(numRows, numColumns, goal);
	}

	/*
	 * Return the specified cell.
	 */
	public Cell getCell(int r, int c) {
		Cell gotCell = new Cell();
		gotCell = rows[r][c];

		return gotCell;


	}

	/*
	 * ------------------------------------------------------ 1) set the goal
	 * value 2) creates the allCells and rows arrays. 3) populate the allCells
	 * and rows arrays with cells 4) invoke reset
	 * ------------------------------------------------------
	 */
	@Override
	public void resizeBoard(int numRows, int numColumns, int goal) {

		this.goalValue = goal;

		this.allCells = new Cell[numRows * numColumns];
		this.rows = new Cell[numRows][numColumns];

		/*
		 * --------------------------------------------------- Populate rows and
		 * allCells to share the same Cells
		 * ---------------------------------------------------
		 */

		Cell cell;
		int n = 0;
		for (int j = 0; j < numRows; j++) {
			for (int k = 0; k < numColumns; k++) {
				cell = new Cell();
				this.rows[j][k] = cell;
				this.allCells[n] = cell;
				n++;
			}
		}

		this.reset();
	}

	/*
	 * ------------------------------------------------------- 1) sets the game
	 * status to game IN_PROGRESS 2) zeros out all cells of the game board 3)
	 * places two random values 4) creates the grids field to be a stack of Grid
	 * values 5) pushes the current grid values onto the grids stack.
	 * --------------------------------------------------------
	 */
	@Override
	public void reset() {

		// call random number tile thing

		
		for (int i = 0; i < rows.length; i++) {
			for (int k = 0; k < rows[0].length; k++) {
				rows[i][k].value = 0;
			}
		}
		int num;
		int num2;
		int num3;
		int num4;
		num = random.nextInt(3) + 0;
		num2 = random.nextInt(3) + 0;
		num3 = random.nextInt(3) + 0;
		num4 = random.nextInt(3) + 0;
		while (num == num3 && num2 == num4) {
			num = random.nextInt(3) + 0;
			num2 = random.nextInt(3) + 0;
			num3 = random.nextInt(3) + 0;
			num4 = random.nextInt(3) + 0;
		}
		rows[num][num2].value = getRandomTileValue();
		rows[num3][num4].value = getRandomTileValue();

		gameStatus = gameStatus.IN_PROGRESS;

		this.grids = new Stack<Grid>();
		this.grids.push(new Grid(this.getValues()).copy());

	}

	/*
	 * --------------------------------------------- Returns the number of cells
	 * that are filled. ---------------------------------------------
	 */
	private int cellsFilled() {
		int filled = 0;

		for (int j = 0; j < rows.length; j++) {
			for (int k = 0; k < rows[0].length; k++) {
				if (rows[j][k].value == 0)
					filled++;
			}
		}

		return filled;
	}

	/*
	 * ------------------------------------------------ Restores the Cell values
	 * of the board with the top grid on the grids stack.
	 * ------------------------------------------------
	 */
	@Override
	public void undo() {
		setValues(grids.pop().getGrid());


	}

	/*
	 * ------------------------------------------------ Sets the values of the
	 * cells in the 2-d array. ------------------------------------------------
	 */
	@Override
	public void setValues(final int[][] grid) {
		for (int j = 0; j < this.rows.length; j++) {
			for (int k = 0; k < this.rows[0].length; k++) {
				this.rows[j][k].value = grid[j][k];
			}
		}
	}

	/*
	 * ------------------------------------------------ returns a grid of the
	 * values stored by the 2-d array of cells.
	 * ------------------------------------------------
	 */
	public int[][] getValues() {
		int[][] grid2 = new int[this.rows.length][this.rows[0].length];

		for (int j = 0; j < rows.length; j++) {
			for (int k = 0; k < rows[0].length; k++) {
				grid2[j][k] = this.rows[j][k].value;

			}
		}


		return grid2;
	}

	/*
	 * ------------------------------ returns a random power of 2.
	 * ------------------------------
	 */
	private int getRandomTileValue() /* generate random value 2, 4, 8, 16 */
	{

		int rndNum;
		Random rnd = new Random();
		rndNum = random.nextInt(16 + 1);
		while (rndNum != 2 || rndNum != 4 || rndNum != 8 || rndNum != 16) {
			rndNum = random.nextInt(16 + 1);
			if (rndNum == 2 || rndNum == 4 || rndNum == 8 || rndNum == 16) {
				break;
			}
		}

		return rndNum;

	}

	/*
	 * -------------------------------------------------------------- Starts
	 * with a random cell index. Loops forward to find a cell is not yet filled.
	 * --------------------------------------------------------------
	 */
	public void placeRandomValue() {
		if (this.cellsFilled() < allCells.length) {
			Boolean test = false;
			Boolean test2 = false;
			int num;
			int num2;
			num = random.nextInt(4) + 0;
			num2 = random.nextInt(4) + 0;
			for (int i = num; i < rows.length; i++) {
				for (int k = num2; k < rows[0].length; k++) {
					if (getCell(i, k).value == 0) {
						this.rows[i][k].value = getRandomTileValue();
						test = true;
						test2 = true;
						break;
					}
					else if( i == 4 && k == 4){
						i = random.nextInt(4) + 0;
						k= random.nextInt(4) + 0;;
					}

				}
				if (test = true) {
					break;
				}
			}
			

		} 
		else {
			throw new RuntimeException("Cannot place a new cell; board is full.");
		}

	}

	/*
	 * ------------------------------------------------------------------ The
	 * following slide method uses the Grid class to do the sliding.
	 * ------------------------------------------------------------------
	 */
	/* return true if the move changes the board */
	@Override
	public boolean slide(SlideDirection direction) {

		Grid grid = new Grid(this.getValues());

		//moves up
		switch (direction) {
		case UP:
			this.grids.push(new Grid(this.getValues()).copy());
			this.setValues(grid.combineUp1024().getGrid());
			placeRandomValue();

			break;
			
			//moves down
		case DOWN:

			this.grids.push(new Grid(this.getValues()).copy());
			this.setValues(grid.combineDown1024().getGrid());
			placeRandomValue();

			break;
			
			//moves left
		case LEFT:

			this.grids.push(new Grid(this.getValues()).copy());
			this.setValues(grid.combineLeft1024().getGrid());
			placeRandomValue();
			break;
			
			//moves right
		case RIGHT:

			this.grids.push(new Grid(this.getValues()).copy());
			this.setValues(grid.combineRight1024().getGrid());
			placeRandomValue();
			break;
		default:
		}

		Grid newGrid = new Grid(this.getValues());
		this.gridChanged = !newGrid.equals(grid);

		if (this.gridChanged) {
			this.grids.push(new Grid(this.getValues()));
		}

		return this.gridChanged;
	}

	/*
	 * -------------------------------------------------------------- A move is
	 * possible if a space is still available on the board or if the board has
	 * changed on the last turn.
	 * --------------------------------------------------------------
	 */
	private boolean moveIsPossible() {

		// to do
		for (int i = 0; i < rows.length; i++) {
			for (int k = 0; k < rows[0].length; k++) {
				if (getCell(i, k).value == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * ------------------------------------------------------------------ The
	 * player wins the game if one of the cells holds the goalValue.
	 * ------------------------------------------------------------------
	 */
	public boolean hasWon() {
		Boolean answer = false;
		for (Cell x : this.allCells) {
			if (x.value == goalValue) {

				gameStatus = gameStatus.USER_WON;
				answer = true;
				// complete this

			}

		}
		return answer;
		// complete this
	}

	@Override
	public GameStatus getStatus() {

		if (this.hasWon()) {
			return GameStatus.USER_WON;
		} else if (this.moveIsPossible()) {
			return this.gameStatus;
		} else {
			return GameStatus.USER_LOST;
		}
	}

	/*
	 * ---------------------------------------------------------- Invokes the
	 * Grid renderBoard method to print the current state of the game board.
	 * ----------------------------------------------------------
	 */
	public void renderBoard() {

		Grid grid = new Grid(this.getValues());

		grid.renderBoard();
	}

	public static void main(String[] args) {
		int[][] matrix = { { 32, 32, 32, 32 }, { 32, 32, 32, 32 }, { 32, 32, 32, 32 }, { 32, 32, 32, 32 } };

		NumberGame gameLogic = new NumberGame(4, 4, 64);
		// gameLogic.resizeBoard(4, 4, 64);
		gameLogic.setValues(matrix);
		gameLogic.grids.push(new Grid(matrix));

		gameLogic.renderBoard();
		gameLogic.slide(SlideDirection.DOWN);
		gameLogic.slide(SlideDirection.DOWN);
		gameLogic.renderBoard();
	}
}