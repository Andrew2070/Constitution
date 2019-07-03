package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class deleterule {
	@Command(name = "deleterule", permission = "Constitution.exec.cmd.deleterule", syntax = "/deleterule", alias = {}, description = "")
	public static CommandResponse deleteruleCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
