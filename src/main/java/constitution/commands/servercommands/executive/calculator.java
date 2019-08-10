package constitution.commands.servercommands.executive;
import constitution.chat.ChatManager;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.ServerUtilities;
import constitution.permissions.Group;
import constitution.permissions.User;
import constitution.permissions.PermissionManager;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class calculator {
	@Command(name = "calculator",
			permission = "constitution.cmd.calculator",
			syntax = "/calculator <operation>",
			alias = {"cal", "calc", "calculate"},
			description = "Does operations")
	public static CommandResponse calculatorCommand(ICommandSender sender, List<String> args) {
		
		if (args.size() < 1) {
			return CommandResponse.SEND_SYNTAX;
		}
		
		Double result = Math.floor(Double.valueOf(args.get(0)));
		if (result != null) {
			ChatManager.send(sender, "constitution.cmd.calculator.result", result.toString());
		}
		return CommandResponse.DONE;
	}
}
