import java.util.*;
import java.io.*;

public class FoobarList {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		getAllFOOBAR();
	}
	
    public static void getAllFOOBAR() throws FileNotFoundException, IOException {
        Scanner input = new Scanner(System.in);

        // ask user for file name, create file
        System.out.print("Enter the file name (include ext): ");
        String fileName = input.nextLine();
        Scanner foobarScanner = new Scanner(new File(fileName));
        
        if(foobarScanner == null) {
        	System.out.println("There's nothing there.");
        }

        // reading through file
        while(foobarScanner.hasNext()) {
        	String line = foobarScanner.nextLine();
        	String[] words = line.split(" ");

        	// iterate over each even-lettered word
        	for(String word : words) {
        		if(word.length() % 2 == 0) {

        			// create copy of word for manipulation
        			String wordCopy = new String(word);
        			wordCopy = wordCopy.toUpperCase();

        			// convert to array for sorting
        			char[] wordArray = wordCopy.toCharArray();
        			Arrays.sort(wordArray);
        			String sortedWord = new String(wordArray);

        			// test for foobars
        			boolean isFoobar = FOOBAR(sortedWord);
        			if(isFoobar) {
        				System.out.println(word);
        			}
        		}
        	}
        }
    }
    
    public static boolean FOOBAR(String word) throws FileNotFoundException, IOException {

    	// convert first and last characters to ASCII
    	int firstASCII = word.charAt(0);
    	int lastASCII = word.charAt(word.length() -1);

    	// check for alphabet index matches
    	while(firstASCII + lastASCII == 155) {

    		// remove first and last characters if match
    		word = word.substring(1, word.length() -1);

    		// confirm foobar once empty
    		if(word.length() == 0) {
    			return true;
    		}

    		// a line worth 40% of my grade
    		FOOBAR(word);
    	}

    	// false for non-foobars
    	return false;
    }
}
