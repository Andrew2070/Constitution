package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class enchant {
	@Command(name = "enchant", permission = "Constitution.exec.cmd.enchant", syntax = "/enchant", alias = {}, description = "")
	public static CommandResponse enchantCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
