import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * Creates infocells for the game, based on a number of user defined variables.
 * Controls which numbered image (if any) shows up on the board for each infocell.
 */
public class InfoCell {

    private int row;//row number of the infocell
    private int column;//column number of the infocell
    private int numOfAdjacentMines;//how many mines are immediately around the infocell
    private int horizontalPosition;//horizontal position based on cell size of the infocell
    private int verticalPosition;//vertical position based on cell size of the infocell
    private String status;//status of the infocell
    private Image image;//image that will be returned from getImage

    /**
     * Constructor creates InfoCell object. 
     * @param int row
     * @param int column
     * @param int numOfAdjacentMines
     */
    public InfoCell(int row, int column, int numOfAdjacentMines){
        this.row = row;
        this.column = column;
        this.numOfAdjacentMines = numOfAdjacentMines;
        status = Configuration.STATUS_COVERED;
    }

    /**
     * calls Graphic's drawImage method which draws a specified Image (from getImage)
     * at a specified location (horizontalPosition, verticalPosition)
     * @param g
     */
    public void draw(Graphics g){
        g.drawImage(getImage(), getHorizontalPosition(), getVerticalPosition(), null);
    }

    /**
     * gets the x-position of the cell in question.
     * @return horizontalPosition
     */
    public int getHorizontalPosition(){
        horizontalPosition = Configuration.CELL_SIZE*column;
        return horizontalPosition;
    }

    /**
     * gets the y-position of the cell in question.
     * @return verticalPosition
     */
    public int getVerticalPosition(){
        verticalPosition = Configuration.CELL_SIZE*row;
        return verticalPosition;
    }

    /**
     * getter method for the cell's status
     * @return status
     */
    public String getStatus(){
        return this.status;
    }

    /**
     * setter method for the cell's status
     * @param status
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * returns the number of mines in the immediate vicinity of an infocell
     * @return numOfAdjacentMines
     */
    public int getNumOfAdjacentMines(){
        return numOfAdjacentMines;
    }

    /**
     * based on status of the cell (uncovered, marked, opened), getImage() returns the image that will cover the infoCell
     * At the end of the game, if the player loses, any cells with status marked will change their status to wrongly_marked
     * @return image
     */
    public Image getImage(){
        ImageIcon img;
        String f = "";

        /**
         * cases cover all the possible statuses of the infocell.
         */
        if (status == Configuration.STATUS_COVERED) {//if infocell is covered return covered image
            img = new ImageIcon("img/covered_cell.png");
            image = img.getImage();
        }
        else if (status == Configuration.STATUS_MARKED) {//if infocell is marked return marked image
            img = new ImageIcon("img/marked_cell.png");
            image = img.getImage();
        }
        else if (status == Configuration.STATUS_WRONGLY_MARKED) { //if infocell is wrongly marked return wrongly marked image
            img = new ImageIcon("img/wrong_mark.png");
            image = img.getImage();
        }
        else{//if none of them then infocell is uncovred, and return 1 of 8 numbered images
            switch (getNumOfAdjacentMines()){
                case 0://if theres 0 adj mines return info_0 img
                    img = new ImageIcon("img/info_0.png"); 
                    image = img.getImage();
                    break;
                case 1://so on
                    img = new ImageIcon("img/info_1.png");
                    image = img.getImage();
                    break;
                case 2:
                    img = new ImageIcon("img/info_2.png");
                    image = img.getImage();
                    break;
                case 3:
                    img = new ImageIcon("img/info_3.png");
                    image = img.getImage();
                    break;
                case 4:
                    img = new ImageIcon("img/info_4.png");
                    image = img.getImage();
                    break;
                case 5:
                    img = new ImageIcon("img/info_5.png");
                    image = img.getImage();
                    break;
                case 6:
                    img = new ImageIcon("img/info_6.png");
                    image = img.getImage();
                    break;
                case 7:
                    img = new ImageIcon("img/info_7.png");
                    image = img.getImage();
                    break;
                default:
                    img = new ImageIcon("img/info_8.png");
                    image = img.getImage();
                    break;
            }
        }
        return image;
    }
}
