package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class Operator {
	@Command(name = "Operator", permission = "Constitution.exec.cmd.Operator", syntax = "/Operator", alias = {}, description = "")
	public static CommandResponse OperatorCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
