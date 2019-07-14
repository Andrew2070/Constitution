package constitution.events;

import constitution.chat.ChatManager;
import constitution.permissions.PermissionManager;
import constitution.utilities.ServerUtilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Andrew2070
 * Handles All Constitution/Player Related Minecraft Events:
 *
 */
public class ChatEvent {

	public static final ChatEvent instance = new ChatEvent();

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void serverChatEvent(ServerChatEvent event) {
		PermissionManager manager = ServerUtilities.getManager();
		if (manager.users != null) {
			event.setCanceled(true);
			EntityPlayerMP player = event.getPlayer();
			ITextComponent component = event.getComponent();
			String msg = event.getMessage();
			ChatManager.sendUniversalChanneledMessage(manager, player, component, msg);
		}
	}
}
