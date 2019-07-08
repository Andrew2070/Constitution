package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class enchant {
	@Command(name = "enchant", permission = "constitution.cmd.perm.enchant", syntax = "/enchant", alias = {}, description = "")
	public static CommandResponse enchantCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
