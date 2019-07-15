/*******************************************************************************
 * Copyright (C) 2019, Andrew2070
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
 *    This product includes software developed by the <organization>.
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
package constitution.utilities;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class ColorUtilities {

	public static final Style stylePlayer = new Style().setColor(TextFormatting.WHITE);
	public static final Style styleOwner = new Style().setColor(TextFormatting.RED);
	public static final Style styleEmpire = new Style().setColor(TextFormatting.GOLD);
	public static final Style styleSelectedEmpire = new Style().setColor(TextFormatting.GREEN);
	public static final Style styleInfoText = new Style().setColor(TextFormatting.GRAY);
	public static final Style styleConfigurableFlag = new Style().setColor(TextFormatting.GRAY);
	public static final Style styleUnconfigurableFlag = new Style().setColor(TextFormatting.DARK_GRAY);
	public static final Style styleValueFalse = new Style().setColor(TextFormatting.RED);
	public static final Style styleValueRegular = new Style().setColor(TextFormatting.GREEN);
	public static final Style styleDescription = new Style().setColor(TextFormatting.GRAY);
	public static final Style styleCoords = new Style().setColor(TextFormatting.BLUE);
	public static final Style styleComma = new Style().setColor(TextFormatting.WHITE);
	public static final Style styleEmpty = new Style().setColor(TextFormatting.RED);
	public static final Style styleAdmin = new Style().setColor(TextFormatting.RED);
	public static final Style styleGroupType = new Style().setColor(TextFormatting.BLUE);
	public static final Style styleGroupText = new Style().setColor(TextFormatting.GRAY);
	public static final Style styleGroupParents = new Style().setColor(TextFormatting.WHITE);
	public static final Style styleGroup = new Style().setColor(TextFormatting.BLUE);

	public static final Map<Character, TextFormatting> colorMap = new HashMap<Character, TextFormatting>();
	static {
		ColorUtilities.colorMap.put('0', TextFormatting.BLACK);
		ColorUtilities.colorMap.put('1', TextFormatting.DARK_BLUE);
		ColorUtilities.colorMap.put('2', TextFormatting.DARK_GREEN);
		ColorUtilities.colorMap.put('3', TextFormatting.DARK_AQUA);
		ColorUtilities.colorMap.put('4', TextFormatting.DARK_RED);
		ColorUtilities.colorMap.put('5', TextFormatting.DARK_PURPLE);
		ColorUtilities.colorMap.put('6', TextFormatting.GOLD);
		ColorUtilities.colorMap.put('7', TextFormatting.GRAY);
		ColorUtilities.colorMap.put('8', TextFormatting.DARK_GRAY);
		ColorUtilities.colorMap.put('9', TextFormatting.BLUE);
		ColorUtilities.colorMap.put('a', TextFormatting.GREEN);
		ColorUtilities.colorMap.put('b', TextFormatting.AQUA);
		ColorUtilities.colorMap.put('c', TextFormatting.RED);
		ColorUtilities.colorMap.put('d', TextFormatting.LIGHT_PURPLE);
		ColorUtilities.colorMap.put('e', TextFormatting.YELLOW);
		ColorUtilities.colorMap.put('f', TextFormatting.WHITE);
	}
}
