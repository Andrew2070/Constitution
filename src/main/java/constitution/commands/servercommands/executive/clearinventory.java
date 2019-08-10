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

import java.util.List;
public class clearinventory {
	@Command(name = "clearinventory",
			permission = "constitution.cmd.clearinventory",
			syntax = "/clearinventory",
			alias = {},
			description = "")
	public static CommandResponse clearinventoryCommand(ICommandSender sender, List<String> args) {
		
		if (args.size() < 1) {
			return CommandResponse.SEND_SYNTAX;
		}
		
		if (ServerUtilities.getPlayerFromName(args.get(0)) == null) {
			ChatManager.send(sender, "constituion.perm.cmd.err.player.notExist", args.get(0));
			return CommandResponse.DONE;
		}
		
		EntityPlayerMP targetPlayer = ServerUtilities.getPlayerFromName(args.get(0));
		targetPlayer.inventory.clear();
		ChatManager.send(sender, "constitution.cmd.clearinventory.succesful", targetPlayer.getDisplayNameString());
		return CommandResponse.DONE;
	}
}
