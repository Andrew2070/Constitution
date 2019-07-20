/*******************************************************************************
 * Copyright (C) July/14/2019, Andrew2070
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement:
 *    This product includes software developed by Andrew2070.
 * 
 * 4. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package constitution.chat.component;

public class ChatComponentBorders {
 
	
	public static String borderEditor(String string) {
					    //0123456789012345678901234567
		String border1 = "==========================[";
		String border2 = "]=========================";
		int border2chars = border2.length();
		int stringchars = string.length()/2;
		//lets say the word is Empire, it has 6 characters.
		//I want it to take 3 characters from border1
		//			   take 3 characters from border2
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
