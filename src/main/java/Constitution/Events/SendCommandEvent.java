package constitution.events;

import constitution.ConstitutionMain;
import constitution.permissions.PermissionManager;
import constitution.permissions.User;
import constitution.utilities.PlayerUtilities;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.server.command.TextComponentHelper;

public class SendCommandEvent {

	public static SendCommandEvent instance = new SendCommandEvent();
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerSendCommand(CommandEvent event) {
		PermissionManager manager = PlayerUtilities.getManager();
		User user = manager.users.get(event.getSender().getCommandSenderEntity().getUniqueID());
		Boolean hasPermission = false;	
		
		if (!manager.checkPermission(event.getSender(), event.getCommand())) {
			ConstitutionMain.logger.info("Command Canceled For:" + event.getSender().toString() + ": Command: " + event.getCommand().toString());
			event.setCanceled(true);
			ITextComponent msg = TextComponentHelper.createComponentTranslation(event.getSender(), "commands.generic.permission", new Object[0]);
			msg.getStyle().setColor(TextFormatting.RED);
			event.getSender().sendMessage(msg);
		} else {
			if (manager.users.get(event.getSender().getCommandSenderEntity().getUniqueID()).isOp() == true) {
				ConstitutionMain.logger.info("OP Firing Command:");
				event.setCanceled(false);
			}
		}
	}
}
