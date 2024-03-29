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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;

import constitution.chat.IChatFormat;
import constitution.exceptions.FormatException;
import constitution.utilities.ColorUtilities;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;

/**
 * An IChatComponent that converts a format to a set of IChatComponents
 *
 * Each of the parenthesis pairs represent an IChatComponent with its own
 * ChatStyle (color, bold, underlined, etc.)
 *
 * Example: {2|Entity number }{%s}{2| is the }{7l| %s}{aN|Good bye!} This format
 * will create the following IChatComponents: - "Entity number " ; with
 * DARK_GREEN color - %s ; one of the parameters sent by the caller (as
 * IChatComponent or IChatFormat, since it's missing the "|" style delimiter
 * character - " is the " ; with DARK_GREEN color - %s ; one of the parameters
 * sent by the caller (as String since it HAS "|" style delimiter character) -
 * "Good bye!" ; with GREEN color and on another line. The modifier "N" ;
 * represents a new line BEFORE the component it's in
 *
 * This ChatComponentFormatted will have the following structure: - sibling1 ;
 * will be a list of all the elements before the component with ; the "N"
 * modifier in this case the first 3 components - sibling2 ; will be a list of
 * the last component until the end
 */

public class ChatComponentFormatted extends ChatComponentList {

	private ITextComponent buffer = new ChatComponentList();

	public ChatComponentFormatted(String format, Object... args) {
		this(format, Arrays.asList(args).iterator());
	}

	public ChatComponentFormatted(String format, Iterator<?> args) {
		String[] components = StringUtils.split(format, "{}");

		for (String component : components) {
			processComponent(component, args);
		}
		this.appendSibling(buffer);
	}

	private void resetBuffer() {
		if (buffer.getSiblings().size() != 0) {
			this.appendSibling(buffer);
		}
		buffer = new ChatComponentList();
	}

	private ITextComponent createComponent(String[] parts, Iterator<?> args) {
		Style chatStyle = getStyle(parts[0]);
		String[] textWithHover = parts[1].split(" << ");
		String actualText = textWithHover[0];

		while (actualText.contains("%s")) {
			actualText = actualText.replaceFirst("%s", Matcher.quoteReplacement(args.next().toString()));
		}
		ITextComponent message = new TextComponentString(actualText).setStyle(chatStyle);
 
		if (textWithHover.length == 2) {
			ITextComponent hoverText = new ChatComponentFormatted("{" + textWithHover[1] + "}", args);
			chatStyle.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
		}

		return message;
	}

	private void addComponent(Iterator<?> args) {
		// TODO: Instead of %s use other identifiers for lists of elements or
		// container (maybe)

		Object currArg = args.next();
		if (currArg instanceof IChatFormat) {
			buffer.appendSibling(((IChatFormat) currArg).toChatMessage());
		} else if (currArg instanceof ITextComponent) {
			buffer.appendSibling((ITextComponent) currArg);
		} else if (currArg instanceof ChatComponentContainer) {
			resetBuffer();
			for (ITextComponent message : (ChatComponentContainer) currArg) {
				this.appendSibling(message);
			}
		}
	}

	private void processComponent(String componentString, Iterator<?> args) {
		String[] parts = componentString.split("\\|", 2);

		if (parts.length == 2) {
			if (parts[0].contains("N")) {
				resetBuffer();
			}
			buffer.appendSibling(createComponent(parts, args));
		} else if (parts.length == 1 && parts[0].equals("%s")) {
			addComponent(args);
		} else {
			throw new FormatException("Format " + componentString + " is not valid. Valid format: {modifiers|text}");
		}
	}

	/**
	 * Converts the modifiers String to a ChatStyle {modifiers| some text}
	 * ^^^^^^^^ ^^^^^^^^^ STYLE for THIS TEXT
	 */
	private Style getStyle(String modifiers) {

		Style chatStyle = new Style();

		for (char c : modifiers.toCharArray()) {
			applyModifier(chatStyle, c);
		}

		return chatStyle;
	}

	/**
	 * Applies modifier to the style Returns whether or not the modifier was
	 * valid
	 */
	private boolean applyModifier(Style chatStyle, char modifier) {
		if (modifier >= '0' && modifier <= '9' || modifier >= 'a' && modifier <= 'f') {
			chatStyle.setColor(ColorUtilities.colorMap.get(modifier));
			return true;
		}
		switch (modifier) {
		case 'k':
			chatStyle.setObfuscated(true);
			return true;
		case 'l':
			chatStyle.setBold(true);
			return true;
		case 'm':
			chatStyle.setStrikethrough(true);
			return true;
		case 'n':
			chatStyle.setUnderlined(true);
			return true;
		case 'o':
			chatStyle.setItalic(true);
			return true;
		}
		return false;
	}

	/**
	 * Adds a ChatComponentText between all of the siblings This can be used for
	 * easily displaying a onHoverText on multiple lines
	 */
	public ChatComponentFormatted applyDelimiter(String delimiter) {
		List<ITextComponent> newSiblings = new ArrayList<ITextComponent>();
		for (ITextComponent component : siblings) {
			if (newSiblings.size() > 0) {
				newSiblings.add(new TextComponentString(delimiter));
			}
			newSiblings.add(component);
		}
		this.siblings = newSiblings;
		return this;
	}

	/**
	 * Cut down version of the client-side only method for getting formatting
	 * code from the ChatStyle class
	 *
	 * Why is this client side?
	 */
	private String getFormattingCodeForStyle(Style style) {
		StringBuilder stringbuilder = new StringBuilder();

		if (style.getColor() != null) {
			stringbuilder.append(style.getColor());
		}

		if (style.getBold()) {
			stringbuilder.append(TextFormatting.BOLD);
		}

		if (style.getItalic()) {
			stringbuilder.append(TextFormatting.ITALIC);
		}

		if (style.getUnderlined()) {
			stringbuilder.append(TextFormatting.UNDERLINE);
		}

		if (style.getObfuscated()) {
			stringbuilder.append(TextFormatting.OBFUSCATED);
		}

		if (style.getStrikethrough()) {
			stringbuilder.append(TextFormatting.STRIKETHROUGH);
		}

		return stringbuilder.toString();

	}

	/**
	 * Gets the formatted String for this component. Example: {3|This is
	 * }{1|some text} Will convert into: \u00a73This is \u00a71some text \u00a7
	 * - this is a unicode character used in Minecraft chat formatting
	 */
	public String[] getLegacyFormattedText() {
		String[] result = new String[this.siblings.size()];
		int k = 0;

		for (ITextComponent component : this.getSiblings()) {

			String actualText = "";
			for (ITextComponent subComponent : component.getSiblings()) {
				actualText += getFormattingCodeForStyle(subComponent.getStyle());
				actualText += subComponent.getUnformattedText();
				actualText += TextFormatting.RESET;
			}

			result[k++] = actualText;
		}

		return result;
	}
}
