package constitution.commands.servercommands.database;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class banmanagerclear {
	@Command(name = "banmanagerclear", permission = "Constitution.exec.cmd.banmanagerclear", syntax = "/banmanagerclear", alias = {}, description = "")
	public static CommandResponse banmanagerclearCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
