package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class mute {
	@Command(name = "mute", permission = "constitution.cmd.perm.mute", syntax = "/mute", alias = {}, description = "")
	public static CommandResponse muteCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
