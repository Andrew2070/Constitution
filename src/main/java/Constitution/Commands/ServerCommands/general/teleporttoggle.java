package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class teleporttoggle {
	@Command(name = "teleporttoggle", permission = "constitution.cmd.perm.teleporttoggle", syntax = "/teleporttoggle", alias = {}, description = "")
	public static CommandResponse teleporttoggleCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
