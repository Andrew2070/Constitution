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
public class burn {
	@Command(name = "burn",
			permission = "constitution.cmd.burn",
			syntax = "/burn <duration>",
			alias = {},
			description = "Burns Target Player For Specified Seconds, Default 10 seconds")
	public static CommandResponse burnCommand(ICommandSender sender, List<String> args) {
		
		if (args.size() < 1) {
			return CommandResponse.SEND_SYNTAX;
		}
		if (ServerUtilities.getPlayerFromName(args.get(0)) == null) {
			ChatManager.send(sender, "constituion.perm.cmd.err.player.notExist", args.get(0));
			return CommandResponse.DONE;
		}
		EntityPlayerMP target = ServerUtilities.getPlayerFromName(args.get(0));
		Integer seconds = 10;
		if (args.size() == 2) {
			seconds = Integer.valueOf(args.get(1));
		}
		target.setFire(seconds);
		ChatManager.send(sender, "constitution.cmd.burn.successful", target.getDisplayNameString());
		return CommandResponse.DONE;
	}
}
