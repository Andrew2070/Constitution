package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class summon {
	@Command(name = "summon", permission = "constitution.cmd.perm.summon", syntax = "/summon", alias = {}, description = "")
	public static CommandResponse summonCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
