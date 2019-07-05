package constitution.commands.servercommands.general;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class teleporttoggle {
	@Command(name = "teleporttoggle", permission = "constitution.cmd.perm.teleporttoggle", syntax = "/teleporttoggle", alias = {}, description = "")
	public static CommandResponse teleporttoggleCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
