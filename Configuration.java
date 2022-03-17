import java.io.File;
import java.util.Scanner;

/**
 * The Configuration class takes care of the settings of the game. 
 * 
 * @author Siddharth Garg
 */
public class Configuration {

    /**
     * Attributes that control aspects of the game board are declared.
     */
    public static int ROWS; // Number of rows of cells on the board of the game
    public static int COLS; // Number of columns of cells on the board of the game
    public static int CELL_SIZE; // width/height of the square-size cell (in pixels)
    public static int MINES; // Number of mines hidden in the minefield
    public static int BOARD_WIDTH; // The width of the board in pixels. It’s calculated by the formula: number of columns * width of the cell + 1
    public static int BOARD_HEIGHT; // The height of the board in pixels. It’s calculated by the formula: number of rows * width of the cell + 1
    public static String STATUS_COVERED; // A string that represents the tag we use in the code when a cell is hidden.
    public static String STATUS_OPENED; // A string that represents the tag we use in the code when a cell is uncovered.
    public static String STATUS_MARKED; // A string that represents the tag we use in the code when a cell is marked as a mine.
    public static String STATUS_WRONGLY_MARKED; // A string that represents the tag we use in the code when a cell is wrongly marked as a mine.

    /**
     * Helper method for loadParameters. 
     * If numbers need to be extracted from a line in given file
     * line is passed to getNum()
     * 
     * @param str
     * @return i
     */ 
    private static int getNum(String str){
        int i;
        String str2 = "";

        // Looping through str and checking if character is a digit
        for(int j=0; j<str.length(); ++j){
            char c = str.charAt(j);
            if (Character.isDigit(c)){ str2 += c; } // If a digit is found, it is added to str2
        }
        i = Integer.parseInt(str2); // Number of type String is converted to int. 
        return i;
    }

    /**
     * helper method. Takes str as an argument, which is sc.nextLine. 
     * splits it and puts it in an array. Returns the first value of the array
     * because the 0th value will be the variable name, 1st will be variable value.
     * @param str
     * @return
     */
    private static String getStr(String str){
        String[] splitVals = str.split(" ");
        return splitVals[1];
    }

    /**
     * A method that opens the file provided by the user as a
     * command line argument and then reads and stores all the
     * parameters into the respective fields.
     * 
     * @param filename
     */
    public static void loadParameters(String filename){    
        try{
            Scanner sc = new Scanner(new File(filename)); // Scanner sc can read the file provided by user
            String str = ""; 
            while (sc.hasNext()){
                str = sc.nextLine();

                // routing the value of str to the correct variable
                if (str.contains("ROWS")) { ROWS = getNum(str); } // if int needed, getNum() method is called.
                else if (str.contains("COLS")) { COLS = getNum(str); }
                else if (str.contains("MINES")) { MINES = getNum(str); }
                else if (str.contains("CELL_SIZE")) { CELL_SIZE = getNum(str); }
                else if (str.contains("STATUS_COVERED")) { STATUS_COVERED = getStr(str); }//if needed, getStr() is called 
                else if (str.contains("STATUS_OPENED")) { STATUS_OPENED = getStr(str); }
                else if (str.contains("STATUS_MARKED")) { STATUS_MARKED = getStr(str); }
                else if (str.contains("STATUS_WRONGLY_MARKED")) { STATUS_WRONGLY_MARKED = getStr(str); }
            }
        }

        /**
         * In case file is not found, return error message.
         */
        catch (Exception e) { 
            System.out.print(e.getMessage());
        }

        /**
         * declaring BOARD_WIDTH and BOARD_HEIGHT based on other values
         */
        finally{
            BOARD_WIDTH = COLS * CELL_SIZE + 1;
            BOARD_HEIGHT = ROWS * CELL_SIZE + 1;
        }        
    }
}
