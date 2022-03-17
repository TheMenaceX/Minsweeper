import java.util.Random;
import java.awt.Graphics;

/**
 * This class is the brains of the application. First, it creates and manages the 2D array of the cell objects
 * that are drawn on the Board. Second, it performs all major computations in the game.
 * It has one private field only which is a 2D rectangular array of cells
 */
public class Minefield {
    
    private Object[][] field;//field is the playing board. All minecells and infocells are put inside it
    private int numRows;//number of rows in field
    private int numColumns;//number of columns in field

    /**
     * default constructor. If no arguments are passed, Minefield creates field with 10 rows
     * 10 columns, 10 minecells. 
     */
    public Minefield(){
        field = new Object[10][10];
        numRows = 10; numColumns = 10;
        mineLaying(10);
        addInfoCells();
    }


    /**
     * custom constructor. If arguments are passed, Minefield creates field with 
     * numRows, numColumns, and numMines
     */
    public Minefield(int numRows, int numColumns, int numMines){
        this.numRows = numRows; this.numColumns = numColumns;
        field = new Object[numRows][numColumns];
        mineLaying(numMines);
        addInfoCells();
    }

    public void revealMines(){
		for (int i=0; i<field.length; i++){
			for (int j=0; j<field[i].length; j++){
                if (field[i][j] instanceof MineCell && ((MineCell)field[i][j]).getStatus() == Configuration.STATUS_COVERED){
                    ((MineCell)field[i][j]).setStatus(Configuration.STATUS_OPENED);
                    ((MineCell)field[i][j]).getImage();
                }
            }
		}
	}

    /**
     * method which places numOfMines number of mines in the field randomly
     * @param numOfMines
     */
    public void mineLaying(int numOfMines){
        Random r1 = new Random();
        int count = 0;

        while (count<numOfMines){//while there aren't enough mines, keep adding mines.
            int num1 = r1.nextInt(numRows);//creating 2 random numbers
            int num2 = r1.nextInt(numColumns);

            if (field[num1][num2] == null){//only place mine if location is empty. 
                MineCell mine = new MineCell(num1, num2);//creating mine at num1, num2 location
                field[num1][num2] = mine;
                count++;//only increment mines if one is actually placed. 
            }
        }
    }

    /**
     * method which places infoCells everywhere in the field where there 
     * isn't already a minecell
     */
    public void addInfoCells(){
        for (int i=0; i<field.length; i++){//loop through field
            for (int j=0; j<field[i].length; j++){
                if (field[i][j] == null){//place infocell if location is empty
                    InfoCell info = new InfoCell(i, j, countMines(i, j));
                    field[i][j] = info;
                }
            }
        }
    }

    /**
     * Private helper method to count number of adjacent mines. Called in addInfoCells. 
     * Passes this value to InfoCells and set equal to numOfAdjacentMines
     * @param i
     * @param j
     * @return count
     */
    private int countMines(int i, int j){
        int count = 0;
        for (int l=i-1; l<=i+1; ++l){ //initialize l to i-1, which is the row above i
            if (l<0 || l>field.length-1){ continue; } //if i is the first row, or i is the last row, don't check i-1 or i+1 becuase those rows don't exist
            for (int k=j-1; k<=j+1; k++){//initialize k to j-1, which is the cell before the cell in question
                if (k<=field[l].length-1 && k>=0){ //if k is between the bounds of the field, keep going
                    if (field[l][k] instanceof MineCell) { count++; } //check to see if the cell is an instance of MineCell
                    }
                }
            }
            return count; //return value
    }

    /*
    public void openCells(Object cell){
        int row = ((InfoCell)cell).getVerticalPosition()/Configuration.CELL_SIZE;
        int col = ((InfoCell)cell).getHorizontalPosition()/Configuration.CELL_SIZE;

        if (((InfoCell)cell).getNumOfAdjacentMines() == 0) {
            for (int l=row-1; l<=row+1; l++){
                if (l<0 || l>field.length-1) { continue; }
                for (int k=col-1; k<=col+1; k++){
                    if (k<=field[l].length-1 && k>=0){

                    }
                }
            }
        }
    }
    */

    /**
     * When the player uncovers a cell that a) is not a mine and b) has zero mines in its adjacency, it’s
     * obvious that all 8 adjacent cells can now be safely uncovered
     * @param cell
     *
     * */
    public void openCells(Object cell){
        int row = ((InfoCell)cell).getVerticalPosition()/Configuration.CELL_SIZE;
        int col = ((InfoCell)cell).getHorizontalPosition()/Configuration.CELL_SIZE;
        int infocells = 0;

        if (((InfoCell)cell).getNumOfAdjacentMines() == 0){//similar execution to countMines
            for (int l=row-1; l<=row+1; ++l){
                if (l<0 || l>field.length-1) { continue; }
                for (int k=col-1; k<=col+1; ++k){
                    if (k<=field[l].length-1 && k>=0){
                        ((InfoCell)field[l][k]).setStatus(Configuration.STATUS_OPENED);
                        ((InfoCell)field[l][k]).getImage();
                        
                    }
                }
            }
        }
    }   
    

    /**
     * Calls the draw methods of MineCell and InfoCell
     */
    public void draw(Graphics g){
        for (int i=0; i<field.length; i++){//loops through field
            for (int j=0; j<field[i].length; j++){
                Object value = field[i][j];//if cell is Minecell calls minecell.draw else infocell.draw
                if (value instanceof MineCell) { ((MineCell)value).draw(g); }
                else { ((InfoCell)value).draw(g); }
            }
        }
    }

    /**
     * Finds what cell object corresponds to the mouse-clicked location on the Board, and returns a
     * reference to it
     * @param x
     * @param y
     * @return
     */
    public Object getCellByScreenCoordinates(int x, int y){
        int row = y/Configuration.CELL_SIZE;
        int column = x/Configuration.CELL_SIZE;
        return field[row][column];
    }
    
    /**
     * Returns a reference to the cell object that resides at row,col on the Board
     * @param row
     * @param col
     * @return
     */
    public Object getCellByRowCol(int row, int col){
        return field[row][col];
    }

    /**
     * A setter method for a MineCell type of object
     * @param row
     * @param col
     * @param cell
     */
    public void setMineCell(int row, int col, MineCell cell){
        field[row][col] = cell;
    }

    
    /**
     * A setter method for an InfoCell type of object
     * @param row
     * @param col
     * @param cell
     */
    public void setInfoCell(int row, int col, InfoCell cell){
        field[row][col] = cell;
    }

    /**
     * Counts the number of cells in the minefield that have a specific status.
     * @param status
     * @return
     */
    public int countCellsWithStatus(String status){
        int count = 0;
        for (int i=0; i<field.length; ++i){//loops through the field
            for (int j=0; j<field[i].length; ++j){
                if (field[i][j] instanceof MineCell) {//if minecell object status matches status passed count++
                    if (((MineCell) field[i][j]).getStatus() == status) { ++count; }
                }
                else {
                    if (((InfoCell) field[i][j]).getStatus() == status) { ++count; }
                }
            }
        }
        return count;
    }

    /**
     * When the game is over because the player has lost, we want to check if any of the cells they
     * marked as mines do not hold a mine. This method is discovering these cells and changes their
     * status to wrongly marked so that the player gets some feedback
     */
    public void revealIncorrectMarkedCells(){
        for (int i=0; i<field.length; ++i){//loop through field
            for (int j=0; j<field[i].length; ++j){
                if (field[i][j] instanceof InfoCell && ((InfoCell)field[i][j]).getStatus() == Configuration.STATUS_MARKED){//if any infocell is marked, change the status to wrongly_marked and call getImage
                    ((InfoCell)field[i][j]).setStatus(Configuration.STATUS_WRONGLY_MARKED);
                    ((InfoCell)field[i][j]).getImage();
                }
            }
        }
    }
}
