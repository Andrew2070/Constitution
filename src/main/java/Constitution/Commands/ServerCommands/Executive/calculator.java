package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class calculator {
	@Command(name = "calculator", permission = "Constitution.exec.cmd.calculator", syntax = "/calculator", alias = {}, description = "")
	public static CommandResponse calculatorCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
