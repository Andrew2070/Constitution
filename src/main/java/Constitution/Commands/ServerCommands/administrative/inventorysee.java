package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class inventorysee {
	@Command(name = "inventorysee", permission = "constitution.cmd.perm.inventorysee", syntax = "/inventorysee", alias = {}, description = "")
	public static CommandResponse inventoryseeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
