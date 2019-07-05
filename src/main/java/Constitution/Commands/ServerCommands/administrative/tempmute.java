package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class tempmute {
	@Command(name = "tempmute", permission = "constitution.cmd.perm.tempmute", syntax = "/tempmute", alias = {}, description = "")
	public static CommandResponse tempmuteCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
