package Constitution.Chat;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class ChatComponentBorders {

	public static String borderEditor(String string) {
					    //0123456789012345678901234567
		String border1 = "==========================[";
		String border2 = "]=========================";
		int border1chars = border1.length();
		int border2chars = border2.length();
		int stringchars = string.length()/2;
		//lets say the word is Empire, it has 6 characters.
		//I want it to take 3 characters from border1
		//			   take 3 characters from border2
		int b1diff = (border1chars - stringchars);
		int b2diff = (border2chars - stringchars);
		String leftborder = border1.substring(stringchars, 27); //cut from 3, 27
		String rightborder = border2.substring(0, b2diff); //cut from 0 to 24
		String finalstring = (leftborder + string + rightborder); //rejoin all 3 strings.
		return finalstring;
	}
	
	public static String borderEditorHover(String string) {
		//36 characters allowed left to right in hover component:
		// "[" and "]" = 2 chars, so subtract 2.
		
		int length = (36 - string.length()-2);
		int leftBorderLength = Math.round(length/2);
		int rightBorderLength = Math.round(length/2);
		String leftBorder = "";
		String rightBorder = "";
	
		for (int i=0; i<leftBorderLength; i++) {
			leftBorder = leftBorder + "=";
		}
		for (int i=0; i<rightBorderLength; i++) {
			rightBorder = rightBorder + "=";
		}
		
		String finalString = leftBorder + "["+string+"]" + rightBorder;
		return finalString;
	}	
}
