package Constitution.Chat;

import net.minecraft.util.text.Style;

public class ChatComponentList extends  net.minecraft.util.text.TextComponentString  {

	public ChatComponentList() {
		super("");
	}

	public Style getChatStyle() {
		if (this.getSiblings().size() == 1) {
			return this.getSiblings().get(0).getStyle();
		} else {
		return super.getStyle();
		 }
	}
}