package constitution.commands.servercommands.executive;
import constitution.chat.ChatManager;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.ServerUtilities;
import constitution.permissions.Group;
import constitution.permissions.User;
import constitution.permissions.PermissionManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.GameType;

import java.util.List;
public class creative {
	@Command(name = "creative",
			permission = "constitution.cmd.creative",
			syntax = "/creative <player>",
			alias = {},
			description = "Set Creative Mode")
	public static CommandResponse creativeCommand(ICommandSender sender, List<String> args) {
		
		if (args.size() < 1) {
			return CommandResponse.SEND_SYNTAX;
		}
		
		if (args.size() == 2) {
			if (ServerUtilities.getPlayerFromName(args.get(1))!=null) {
				EntityPlayerMP target = ServerUtilities.getPlayerFromName(args.get(1));
				//TODO Document This Node:
				if (ServerUtilities.getManager().checkPermission(sender, "constitution.cmd.creative.other")) {
					target.setGameType(GameType.CREATIVE);
					ChatManager.send(sender, "constitution.cmd.creative.successful");
					ChatManager.send(target.getCommandSenderEntity(), "constitution.cmd.creative.successful");
					return CommandResponse.DONE;
				}
			}
		}
		
		EntityPlayerMP player = (EntityPlayerMP) sender.getCommandSenderEntity();
		player.setGameType(GameType.CREATIVE);
		ChatManager.send(sender, "constitution.cmd.creative.successful");
		return CommandResponse.DONE;
	}
}
