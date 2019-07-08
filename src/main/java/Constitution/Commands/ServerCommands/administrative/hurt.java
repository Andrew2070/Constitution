package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class hurt {
	@Command(name = "hurt", permission = "constitution.cmd.perm.hurt", syntax = "/hurt", alias = {}, description = "")
	public static CommandResponse hurtCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
