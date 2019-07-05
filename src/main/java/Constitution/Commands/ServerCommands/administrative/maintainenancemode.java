package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class maintainenancemode {
	@Command(name = "maintainenancemode", permission = "constitution.cmd.perm.maintainenancemode", syntax = "/maintainenancemode", alias = {}, description = "")
	public static CommandResponse maintainenancemodeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
