package constitution.chat.component;

import java.util.ArrayList;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.ITextComponent;

public class ChatComponentContainer extends ArrayList<ITextComponent> {
	/**
	 * Sends all chat components to the sender
	 */
	public void send(ICommandSender sender) {
		for (ITextComponent component : this) {
			sender.sendMessage(component);
		}
	}
}