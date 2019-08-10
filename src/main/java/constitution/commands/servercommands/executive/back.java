package constitution.commands.servercommands.executive;
import constitution.chat.ChatManager;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.ServerUtilities;
import constitution.permissions.User;
import constitution.teleport.Teleport;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;
public class back {
	@Command(name = "back",
			permission = "constitution.cmd.back",
			syntax = "/back",
			alias = {},
			description = "Return To Previous Location (Death/Past Teleport)")
	public static CommandResponse backCommand(ICommandSender sender, List<String> args) {
		EntityPlayerMP player = (EntityPlayerMP) sender.getCommandSenderEntity();
		User user = ServerUtilities.getManager().users.get(sender.getCommandSenderEntity().getUniqueID());
		
		if (user.getLastLocation() == null) {
		ChatManager.send(sender, "constitution.cmd.back.err");
		return CommandResponse.DONE;
		}
		
		ChatManager.send(sender, "constitution.cmd.back.teleporting");
		Teleport tp = new Teleport(
					"back: " + player.getDisplayNameString(),
					user.getLastDimension(),
					user.getLastLocation().getX(),
					user.getLastLocation().getY(),
					user.getLastLocation().getZ());
		tp.teleport(player);
		return CommandResponse.DONE;
	}
}
