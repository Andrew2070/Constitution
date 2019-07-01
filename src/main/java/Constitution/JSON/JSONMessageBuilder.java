
package Constitution.JSON;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

/**
 * Helper to build JSON IChatComponents
 */
public class JSONMessageBuilder {
	private JSONMessageBuilder parentBuilder = null;
	private JsonObject rootObj;

	public JSONMessageBuilder() {
		rootObj = new JsonObject();
		rootObj.addProperty("text", "");
	}

	private JSONMessageBuilder(JSONMessageBuilder parentBuilder) {
		this();
		this.parentBuilder = parentBuilder;
	}

	/**
	 * Sets the text
	 */
	public JSONMessageBuilder setText(String text) {
		rootObj.addProperty("text", text);
		return this;
	}

	/**
	 * Adds a new entry in the "extra" list
	 */
	public JSONMessageBuilder addExtra() {
		JSONMessageBuilder extra = new JSONMessageBuilder(this);
		if (!rootObj.has("extra")) {
			JsonArray extraArray = new JsonArray();
			rootObj.add("extra", extraArray);
		}
		rootObj.get("extra").getAsJsonArray().add(extra.rootObj);
		return extra;
	}

	/**
	 * Sets the color of the text
	 */
	public JSONMessageBuilder setColor(TextFormatting color) {
		rootObj.addProperty("color", color.getFriendlyName());
		return this;
	}

	/**
	 * Sets if the text is bold
	 */
	public JSONMessageBuilder setBold(boolean isBold) {
		rootObj.addProperty("bold", isBold);
		return this;
	}

	/**
	 * Sets if the text is underlined
	 */
	public JSONMessageBuilder setUnderlined(boolean isUnderlined) {
		rootObj.addProperty("underlined", isUnderlined);
		return this;
	}

	/**
	 * Sets if the text is italic
	 */
	public JSONMessageBuilder setItalic(boolean isItalic) {
		rootObj.addProperty("italic", isItalic);
		return this;
	}

	/**
	 * Sets if the text is strikethrough
	 */
	public JSONMessageBuilder setStrikethrough(boolean isStrikethrough) {
		rootObj.addProperty("strikethrough", isStrikethrough);
		return this;
	}

	/**
	 * Sets if the text is "obfuscated"
	 */
	public JSONMessageBuilder setObfuscated(boolean isObfuscated) {
		rootObj.addProperty("obfuscated", isObfuscated);
		return this;
	}

	/**
	 * Sets the text to insert when shift-clicked by a player
	 */
	public JSONMessageBuilder setInsertion(String insertion) {
		rootObj.addProperty("insertion", insertion);
		return this;
	}

	/**
	 * Sets the translation identifier
	 */
	public JSONMessageBuilder setTranslate(String translate) {
		rootObj.addProperty("translate", translate);
		return this;
	}

	/**
	 * List of String arguments passed to the translation identifier
	 */
	public JSONMessageBuilder setWith(String[] with) {
		JsonArray withArray = new JsonArray();
		for (String str : with) {
			withArray.add(new JsonPrimitive(str));
		}
		rootObj.add("with", withArray);
		return this;
	}

	/**
	 * Sets the score
	 */
	public JSONMessageBuilder setScore(String name, String objective, String value) {
		JsonObject scoreObj = new JsonObject();
		scoreObj.addProperty("name", name);
		scoreObj.addProperty("objective", objective);
		scoreObj.addProperty("value", value);
		rootObj.add("score", scoreObj);
		return this;
	}

	/**
	 * Sets the selector
	 */
	public JSONMessageBuilder setSelector(String selector) {
		rootObj.addProperty("selector", selector);
		return this;
	}

	/**
	 * Sets the clickEvent to the given action with the value Possible actions
	 * are open_url, run_command, and suggest_command
	 */
	public JSONMessageBuilder setClickEvent(String action, String value) {
		JsonObject clickEventObj = new JsonObject();
		clickEventObj.addProperty("action", action);
		clickEventObj.addProperty("value", value);
		rootObj.add("clickEvent", clickEventObj);
		return this;
	}

	/**
	 * Shortcut to setClickEvent("open_url", url);
	 */
	public JSONMessageBuilder setClickEventOpenUrl(String url) {
		return setClickEvent("open_url", url);
	}

	/**
	 * Shortcut to setClickEvent("run_command", command);
	 */
	public JSONMessageBuilder setClickEventRunCommand(String command) {
		return setClickEvent("run_command", command);
	}

	/**
	 * Shortcut to setClickEvent("suggest_command", command);
	 */
	public JSONMessageBuilder setClickEventSuggestCommand(String command) {
		return setClickEvent("suggest_command", command);
	}

	/**
	 * Sets the hoverEvent to the given action with the value Possible actions
	 * are show_text, show_item, show_achievement, and show_entity
	 */
	public JSONMessageBuilder setHoverEvent(String action, String value) {
		JsonObject hoverEventObj = new JsonObject();
		hoverEventObj.addProperty("action", action);
		hoverEventObj.addProperty("value", value);
		rootObj.add("hoverEvent", hoverEventObj);
		return this;
	}

	/**
	 * Shortcut to setHoverEvent("show_text", text);
	 */
	public JSONMessageBuilder setHoverEventShowText(String text) {
		return setHoverEvent("show_text", text);
	}

	/**
	 * Shortcut to setHoverEvent("show_item", item);
	 */
	public JSONMessageBuilder setHoverEventShowItem(String item) {
		return setHoverEvent("show_item", item);
	}

	/**
	 * Shortcut to setHoverEvent("show_achievement", achievement);
	 */
	public JSONMessageBuilder setHoverEventShowAchievement(String achievement) {
		return setHoverEvent("show_achievement", achievement);
	}

	/**
	 * Shortcut to setHoverEvent("show_entity", entity);
	 */
	public JSONMessageBuilder setHoverEventShowEntity(String entity) {
		return setHoverEvent("show_entity", entity);
	}

	public JSONMessageBuilder resetHoverEvent() {
		rootObj.remove("hoverEvent");
		return this;
	}

	/**
	 * Returns the parent JsonMessageBuilder, or null if there is no parent
	 */
	public JSONMessageBuilder getParent() {
		return parentBuilder;
	}

	/**
	 * Returns the root JsonObject
	 */
	public JsonObject getRootObj() {
		return rootObj;
	}

	/**
	 * Returns the IChatComponent from this builder
	 */
	public ITextComponent build() {
		if (parentBuilder == null) {
			return ITextComponent.Serializer.jsonToComponent(rootObj.toString());
			
		} else {
			return parentBuilder.build();
		}
	}
}