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
public class disarm {
	@Command(name = "disarm", permission = "constitution.cmd.disarm", syntax = "/disarm <user>", alias = {}, description = "Forces Specified Player To Drop Held Item")
	public static CommandResponse disarmCommand(ICommandSender sender, List<String> args) {
		if (args.size() < 1) {
			return CommandResponse.SEND_SYNTAX;
		}
		
		if (ServerUtilities.getPlayerFromName(args.get(0)) == null) {
			ChatManager.send(sender, "constituion.perm.cmd.err.player.notExist", args.get(0));
			return CommandResponse.DONE;
		}
		
		EntityPlayerMP player = ServerUtilities.getPlayerFromName(args.get(0));
		

		return CommandResponse.DONE;
	}
}
