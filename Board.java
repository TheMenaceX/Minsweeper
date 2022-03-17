import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Dimension;
/**
 * This class is the foundation for the whole game. It controls the board to be displayed, the statusbar at the
 * bottom of the window, the effects of the mouse clicks, etc. 
 */
public class Board extends JPanel
{
	/**
	 * height width and mines determine size of the board, when making minefield object
	 */
	private int height, width, mines;
	private JLabel statusbar;
	private Minefield minefield;
	private int infoCellsRemaining;//num of infocells remaining throughout the game. 
	private boolean isMineOpen;//true if a mine has been clicked false otherwise. 

	/**
	 * constructor that creates a minefield object and declares a few more private fields
	 * @param height
	 * @param width
	 * @param mines 
	 * @param statusbar
	 */
	public Board(int height, int width, int mines, JLabel statusbar)
	{
		this.height = height;
		this.width = width;
		this.mines = mines;
		this.statusbar = statusbar;

		minefield = new Minefield(height, width, mines);//minefield object 

		infoCellsRemaining = (height*width)-mines;//used to check if the game is won by clicking all infocells

		setPreferredSize(new Dimension(Configuration.BOARD_WIDTH, Configuration.BOARD_HEIGHT));
		addMouseListener(new MouseReader(this));
	}

	/**
	 * A getter method for the Minefield object
	 * @return minefield
	 */
	public Minefield getMinefield(){
		return this.minefield;
	}

	/**
	 * This method is called automatically whenever the Board paints/repaints all its components on the
	 * screen. Invokes draw method of minefield 
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		minefield.draw(g);
	}

	/**
	 * A getter method that returns true when the game is over, and false otherwise.
	 * @return boolean
	 */
	public boolean isGameOver(){
		if (isMineOpen) {//if mine has been clicked game over and set statusbar to losing status
			setStatusbar("Game over - You lost!");
			minefield.revealIncorrectMarkedCells();
			minefield.revealMines();
			return true;
		}
		else if (infoCellsRemaining == 0) {//if all infocells uncovered game won and set statusbar to winning status
			setStatusbar("Game over - You won!");
			return true;
		}
		return false;
	}

	/**
	 * setter method for statusbar
	 * @param text
	 */
	public void setStatusbar(String text){
		statusbar.setText(text);
	}

	/**
	 * getter method for statusbar
	 */
	public String getStatusbar(){
		return statusbar.getText();
	}

	/**
	 * When the player makes a right click on the mouse to mark a cell, this will reduce the indicated
	 * number of mines that remain in the field.
	 * @return
	 */
	public boolean removeMine(){
		mines--;//num of mines reduced everytime this method is called 
		if (mines < 0){//mines can't be less than 0
			setStatusbar("Invalid action");
			return false;
		}
		setStatusbar(mines + " mines remaining");//otherwise set statusbar to displace num of mines remaining
		return true;
	}

	/**
	 * should be used when the player is doing a right click to unmark a previously marked cell. 
	 * @return
	 */
	public boolean addMine(){
		mines++;//adds mine when player right clicks an already marked infocell
		setStatusbar(mines + " mines remaining");//updates the statusbar
		return true;
	}
	/**
	 * This method is called automatically when a mouse button is clicked. Decides what happens when a button on the mouse is clicked 
	 * @param x
	 * @param y
	 * @param button
	 */
	public void mouseClickOnLocation(int x, int y, String button)
	{
		Object value = minefield.getCellByScreenCoordinates(x, y); //gets a reference to the cell being clicked
		boolean b = false;

		if (!isGameOver()){
			if (button == "left" && value instanceof MineCell && ((MineCell)value).getStatus() != Configuration.STATUS_OPENED){//if left clicking a covered minecell
				MineCell val = ((MineCell)value);
				val.setStatus(Configuration.STATUS_OPENED);//open the minecell
				val.getImage(); //call the minecell's getImage() method
				isMineOpen = true;
				minefield.revealIncorrectMarkedCells(); //show all marked infocells
				minefield.revealMines();//show all mines
				isGameOver(); //end the game
				}

			else if (button == "right" && value instanceof MineCell && ((MineCell)value).getStatus() != Configuration.STATUS_OPENED){//right clicking a minecell
				MineCell val = ((MineCell)value);
				if (val.getStatus() == Configuration.STATUS_COVERED && mines > 0){//right clicking a covered minecell
					val.setStatus(Configuration.STATUS_MARKED);//set status to marked
					val.getImage();
					removeMine();
				}
				else if (val.getStatus() == Configuration.STATUS_MARKED){//right clicking a marked minecell
					val.setStatus(Configuration.STATUS_COVERED);//set status back to covered
					val.getImage();
					addMine();
				}
				else if (mines == 0) { setStatusbar("Invalid action"); } //if more right clicks than mines present, update statusbar for invalid action
			}
			else if (button != "left" && button != "right") { setStatusbar("Invalid action"); } //if a button other than left or right is clicked, update statusbar for invalid action

			else if (button == "left" && value instanceof InfoCell && ((InfoCell)value).getStatus() != Configuration.STATUS_OPENED){//left clicking an infocell
				InfoCell val = ((InfoCell)value);
				val.setStatus(Configuration.STATUS_OPENED);//set status to opened 
				val.getImage();//get new uncovered image
				minefield.openCells(val);//open cells adjacent to if count mines returns 0
				// infoCellsRemaining--;
				if (minefield.countCellsWithStatus(Configuration.STATUS_OPENED) == infoCellsRemaining) { infoCellsRemaining = 0; isGameOver();}//check to see how many infocells are remaining
				isGameOver();
			}

			else if (button == "right" && value instanceof InfoCell && ((InfoCell)value).getStatus() != Configuration.STATUS_OPENED){//right clicking an unopen infocell
				InfoCell val = ((InfoCell)value);
				if (val.getStatus() == Configuration.STATUS_COVERED && mines > 0){//right clicking a covered infocell
					val.setStatus(Configuration.STATUS_MARKED);
					val.getImage();
					removeMine();
				}
				else if (val.getStatus() == Configuration.STATUS_MARKED){//right clicking an already marked infocell
					val.setStatus(Configuration.STATUS_COVERED);
					val.getImage();
					addMine();
				}
				else if (mines == 0) { setStatusbar("Invalid action"); }
			}
		}
		repaint();
	}
}
