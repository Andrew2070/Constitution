package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class whitelist {
	@Command(name = "whitelist", permission = "Constitution.exec.cmd.whitelist", syntax = "/whitelist", alias = {}, description = "")
	public static CommandResponse whitelistCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
