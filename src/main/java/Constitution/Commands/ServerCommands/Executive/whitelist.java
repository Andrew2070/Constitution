package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class whitelist {
	@Command(name = "whitelist", permission = "Constitution.exec.cmd.whitelist", syntax = "/whitelist", alias = {}, description = "")
	public static CommandResponse whitelistCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
