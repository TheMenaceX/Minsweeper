import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Creates MineCell objects using player defined values
 */
public class MineCell {

    private int row;//row number of the minecell
    private int column;//column number of the minecell
    private int horizontalPosition;//x position of the minecell
    private int verticalPosition;//y position of the minecell
    private String status = Configuration.STATUS_COVERED;//status of the minecell defined as covered by default 
    private Image image;//image that is returned from getImage()

    /**
     * constructor creates minecell object at specified row and column
     * @param row
     * @param column
     */
    public MineCell(int row, int column){
        this.row = row;
        this.column = column;
    }

    /**
     * draw method calls Graphics' drawImage method and draws a picture at specified point
     * @param g
     */
    public void draw(Graphics g){
        g.drawImage(getImage(), getHorizontalPosition(), getVerticalPosition(), null);
    }

    /**
     * gets the y-position of the cell in question.
     * @return verticalPosition
     */
    public int getHorizontalPosition(){
        horizontalPosition = Configuration.CELL_SIZE*column;
        return horizontalPosition;
    }

    /**
     * gets the x-position of the cell in question.
     * @return horizontalPosition
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
     * based on status of the cell (uncovered, marked, opened), getImage() returns the image that will cover the minecell
     * @return Image image
     */
    public Image getImage(){
        ImageIcon img;

        if (status == Configuration.STATUS_OPENED) { //if status is open return minecell image
            img = new ImageIcon("img/mine_cell.png");
            image = img.getImage();
        }
        else if (status == Configuration.STATUS_COVERED) { //if status is covered return covered image
            img = new ImageIcon("img/covered_cell.png");
            image = img.getImage();
        }
        else { //if neither of the above, marked cell img is returned
            img = new ImageIcon("img/marked_cell.png");
            image = img.getImage();
        }
        return image;
    }
}
