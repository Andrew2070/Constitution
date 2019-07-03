package constitution.commands.servercommands.database;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class banmanagerclearall {
	@Command(name = "banmanagerclearall", permission = "Constitution.exec.cmd.banmanagerclearall", syntax = "/banmanagerclearall", alias = {}, description = "")
	public static CommandResponse banmanagerclearallCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
