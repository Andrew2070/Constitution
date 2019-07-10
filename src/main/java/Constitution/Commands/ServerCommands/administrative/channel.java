package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.chat.channels.Channel;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.permissions.PermissionManager;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
public class channel {
	
	private static PermissionManager getManager() {
		return PlayerUtilities.getManager();
	}
	
	@Command(name = "channel", permission = "constitution.cmd.channel", syntax = "/channel", alias = {"ch", "CH", "Ch"}, description = "")
	public static CommandResponse channelCommandMethod(ICommandSender sender, List<String> args) {
		return CommandResponse.SEND_HELP_MESSAGE;
	}
	
	@Command(name = "create",
			permission = "constitution.cmd.channel.create",
			parentName = "constitution.cmd.channel",
			syntax = "/ch create <name> <password>",
			alias = {},
			description = "")
	public static CommandResponse channelCreate(ICommandSender sender, List<String> args) {
		if (args.size()<1) {
			return CommandResponse.SEND_SYNTAX;
		}
		String name = args.get(0);
		String password = "";
		if (args.size() == 2) {
			password = args.get(1);
		}
		
		Channel channel = new Channel(name, password);
		getManager().channels.add(channel);
		getManager().saveChannels();
		return CommandResponse.DONE;
	}

	@Command(name = "prefix",
			permission = "constitution.cmd.channel.prefix",
			parentName = "constitution.cmd.channel",
			syntax = "/ch prefix",
			alias = {},
			description = "")
	public static CommandResponse channelPrefix(ICommandSender sender, List<String> args) {
		return CommandResponse.SEND_HELP_MESSAGE;
	}
	
	@Command(name = "set",
			permission = "constitution.cmd.channel.prefix.set",
			parentName = "constitution.cmd.channel.prefix",
			syntax = "/ch prefix set <channel> <prefix>",
			alias = {},
			description = "")
	public static CommandResponse channelPrefixAdd(ICommandSender sender, List<String> args) {
		if (args.size()<2) {
			return CommandResponse.SEND_SYNTAX;
		}
		if (getManager().channels.get(args.get(0))!=null) {
			Channel channel = getManager().channels.get(args.get(0));
			channel.setPrefix(args.get(1));
			getManager().saveChannels();
		}
		return CommandResponse.DONE;
	}
	
	@Command(name = "clear",
			permission = "constitution.cmd.channel.prefix.clear",
			parentName = "constitution.cmd.channel.prefix",
			syntax = "/ch prefix clear <channel>",
			alias = {},
			description = "")
	public static CommandResponse channelPrefixClear(ICommandSender sender, List<String> args) {
		if (args.size()<2) {
			return CommandResponse.SEND_SYNTAX;
		}
		if (getManager().channels.get(args.get(0))!=null) {
			Channel channel = getManager().channels.get(args.get(0));
			channel.setPrefix("");
			getManager().saveChannels();
		}
		return CommandResponse.DONE;
	}
}
