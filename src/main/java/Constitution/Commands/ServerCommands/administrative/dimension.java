package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class dimension {
	@Command(name = "dimension", permission = "constitution.cmd.perm.dimension", syntax = "/dimension", alias = {}, description = "")
	public static CommandResponse dimensionCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
