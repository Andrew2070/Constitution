package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class whitelist {
	@Command(name = "whitelist", permission = "constitution.cmd.perm.whitelist", syntax = "/whitelist", alias = {}, description = "")
	public static CommandResponse whitelistCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
