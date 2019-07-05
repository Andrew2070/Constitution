package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class breakblock {
	@Command(name = "breakblock", permission = "constitution.cmd.perm.breakblock", syntax = "/breakblock", alias = {}, description = "")
	public static CommandResponse breakblockCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
