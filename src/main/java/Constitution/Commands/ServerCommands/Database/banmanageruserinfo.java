package constitution.commands.servercommands.database;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class banmanageruserinfo {
	@Command(name = "banmanageruserinfo", permission = "Constitution.exec.cmd.banmanageruserinfo", syntax = "/banmanageruserinfo", alias = {}, description = "")
	public static CommandResponse banmanageruserinfoCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
