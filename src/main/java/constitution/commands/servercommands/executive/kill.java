package constitution.commands.servercommands.executive;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.ServerUtilities;
import constitution.permissions.Group;
import constitution.permissions.User;
import constitution.permissions.PermissionManager;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class kill {
	@Command(name = "kill", permission = "constitution.cmd.kill", syntax = "/kill", alias = {}, description = "")
	public static CommandResponse killCommand(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
