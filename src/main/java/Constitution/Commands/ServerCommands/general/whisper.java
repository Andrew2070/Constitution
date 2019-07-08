package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class whisper {
	@Command(name = "whisper", permission = "constitution.cmd.perm.whisper", syntax = "/whisper", alias = {}, description = "")
	public static CommandResponse whisperCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
