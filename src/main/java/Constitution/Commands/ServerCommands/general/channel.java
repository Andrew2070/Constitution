package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class channel {
	@Command(name = "channel", permission = "constitution.cmd.perm.channel", syntax = "/channel", alias = {}, description = "")
	public static CommandResponse channelCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
