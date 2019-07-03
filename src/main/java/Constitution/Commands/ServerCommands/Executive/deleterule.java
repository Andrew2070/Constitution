package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class deleterule {
	@Command(name = "deleterule", permission = "Constitution.exec.cmd.deleterule", syntax = "/deleterule", alias = {}, description = "")
	public static CommandResponse deleteruleCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
