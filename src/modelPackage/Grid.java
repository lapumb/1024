package modelPackage;

public class Grid {
	
	private int[][] grid;

	
	public Grid(int[][] grid) {

		this.grid = grid;
	}
	
	
	public int[][] getGrid()
	{
		return grid;
	}
	

	private int[][]  copyGrid()
	{
		int[][] copied = new int[grid.length][grid[0].length];
		
		int j, k;
		for (j = 0; j <grid.length; j++)
		{
			for (k = 0; k < grid[0].length; k++ )
			{
				copied[j][k] = grid[j][k];
			}
		}
		return copied;
	}
	
	public Grid copy()
	{
		return new Grid( this.copyGrid() );
	}
	
	
	public Grid transpose()
	{
		int[][] transposed = new int[grid[0].length][grid.length];

		for( int i = 0; i<grid.length; i++){
			for(int k = 0; k<grid[0].length; k++){
				transposed[k][i] = grid[i][k];
			}
		}




		return new Grid( transposed );
	}

	
	public boolean equals(Grid g) {
		Boolean a= true;
		if (this.grid.length != g.grid.length || this.grid[0].length != g.grid[0].length) {
			a=false;
		}
		else {
			for(int i = 0; i< g.grid.length; i++){
				for(int k =0; k<g.grid[0].length; k++){
					if(this.grid[i][k] != g.grid[i][k]){
						a= false;
					}
				}
			}
			a= true;
		}


		return a;
	}
	
	
	public boolean equals(Object g) {
		if (g instanceof Grid) {
			return this.equals((Grid) g);
		} else {
			return false;
		}
	}	
	

	public Grid combineLeft1024( ) {
		
		Grid g = this.copy();
		
		Sequence sequence;
		
		for (int j = 0; j < g.grid.length; j++) {
			sequence = new Sequence(g.grid[j]);
			g.grid[ j ] = sequence.combineLeft1024();
		}
		return g;
	}	

	
	public Grid combineRight1024( ) {
		Grid g = this.copy();
	
		Sequence sequence;
		
		for (int j = 0; j < g.grid.length; j++) {
			sequence = new Sequence(g.grid[j]);
			g.grid[ j ] = sequence.combineRight1024();
		}
		return g;
	}
	

	public Grid combineUp1024( ) {
		
		return this.transpose().combineLeft1024().transpose();
	}	
	
	
	public Grid combineDown1024( ) {
		
		return this.transpose().combineRight1024().transpose();

	}	
	
	
    public void renderBoard() {
    	
        final int     CELL_WIDTH = 3;
        
        /* Set the string to %4d */
        final String NUM_FORMAT = String.format("%%%dd", CELL_WIDTH + 1);

        /* Set the string to %4s, but without using String.format() */
        final String BLANK_FORMAT = "%" + (CELL_WIDTH + 1) + "s";	
    	

        /* Print the 2D array using dots and numbers */
        for (int k = 0; k < this.grid.length; k++)
        {
            for (int m = 0; m < this.grid[0].length; m++)
            {
                if (this.grid[k][m]== 0)
                {
                	System.out.printf (BLANK_FORMAT, ".");
                }
                else
                {
                	System.out.printf (NUM_FORMAT, this.grid[k][m]);
                }
            }
            System.out.println();
        }
    }   
    
    
    public static void main(String[] args)
    {
    	int[][] grid = { 
    			{1,2,3,4,5,6},
       			{7,8,9,10,11,12}
    	};
    	Grid g = new Grid(grid);
    	
    	
    	System.out.println("\ngrid\n");
    	g.renderBoard();
    	
    	System.out.println("\ngrid tranposed\n");
    	g.transpose().renderBoard();
    	
    }

}
