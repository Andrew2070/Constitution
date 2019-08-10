package constitution.commands.servercommands.executive;
import constitution.chat.ChatManager;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.configuration.Config;
import constitution.utilities.ServerUtilities;
import constitution.permissions.Group;
import constitution.permissions.User;
import constitution.permissions.PermissionManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;

import java.util.List;
public class ban {
	@Command(name = "ban",
			permission = "constitution.cmd.ban",
			syntax = "/ban <player> <reason>",
			alias = {},
			description = "Ban a player for specified reason, if any.")
	public static CommandResponse banCommand(ICommandSender sender, List<String> args) {
		
		if (args.size() < 1 || args.size() > 2) {
			return CommandResponse.SEND_SYNTAX;
		}
		
		User user = ServerUtilities.getManager().users.get(args.get(0));
		String banreason = Config.instance.defaultBanMessage.get();
		
		if (args.size() == 2) {
			banreason = args.get(1);
		}
		
		user.setBanned(true);
		ServerUtilities.getPlayerFromUser(user).connection.disconnect(new TextComponentString(banreason));
		ChatManager.send(sender, "constitution.cmd.ban.successful", user.toChatMessage(), banreason);
		return CommandResponse.DONE;
	}
}
