package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class tphere {
	@Command(name = "tphere", permission = "constitution.cmd.perm.tphere", syntax = "/tphere", alias = {}, description = "")
	public static CommandResponse tphereCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
