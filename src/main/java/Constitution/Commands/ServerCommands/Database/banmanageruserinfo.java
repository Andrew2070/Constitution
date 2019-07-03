package Constitution.Commands.ServerCommands.Database;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class banmanageruserinfo {
	@Command(name = "banmanageruserinfo", permission = "Constitution.exec.cmd.banmanageruserinfo", syntax = "/banmanageruserinfo", alias = {}, description = "")
	public static CommandResponse banmanageruserinfoCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
