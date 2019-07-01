package Constitution.Utilities;

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