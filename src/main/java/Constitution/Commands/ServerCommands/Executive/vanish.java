package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class vanish {
	@Command(name = "vanish", permission = "Constitution.exec.cmd.vanish", syntax = "/vanish", alias = {}, description = "")
	public static CommandResponse vanishCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
