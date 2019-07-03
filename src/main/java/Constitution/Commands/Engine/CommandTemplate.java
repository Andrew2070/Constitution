package Constitution.Commands.Engine;

import java.util.Arrays;
import java.util.List;

import Constitution.Permissions.ConstitutionBridge;
import Constitution.Permissions.PermissionLevel;
import Constitution.Permissions.PermissionObject;
import Constitution.Permissions.PermissionProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * @author Andrew2070:
 * 
 * This class extends CommandBase and implements ICommand, PermissionObject.
 * The CommandTemplate class is designed to execute called commands by satisfying the desires of the MC CommandBase.
 * The CommandTree class does most of the work to supply this information to the commandTemplate.
 * 
 * 
 */
public class CommandTemplate extends CommandBase implements ICommand, PermissionObject {
	
	private CommandTree commandTree;

	public CommandTemplate(CommandTree commandTree) {
		this.commandTree = commandTree;
	}
	
	//Compares a command to another command, not really useful.
	@Override
	public int compareTo(ICommand o) {
		return 0;
	}
	
	//Returns the Name of the Command.

	@Override
	public String getName() {
		return commandTree.getRoot().getLocalizedName();
	}
	
	//Returns the Syntax of the Command.

	@Override
	public String getUsage(ICommandSender sender) {
		return commandTree.getRoot().getLocalizedSyntax();
	}
	
	//Returns a list of aliases for the command.

	@Override
	public List<String> getAliases() {
		return Arrays.asList(commandTree.getRoot().getAnnotation().alias());
	}

	//Processes/Executes the Command.
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		commandTree.commandCall(sender, Arrays.asList(args));
		
	}
	 
	//Checks if the command sender has the permission to use the command.

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return PermissionProxy.getPermissionManager().hasPermission(sender.getCommandSenderEntity().getUniqueID(), commandTree.getRoot().getAnnotation().permission());
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {

		CommandTreeNode node = commandTree.getNodeFromArgs(Arrays.asList(args));

		int argumentNumber = commandTree.getArgumentNumber(Arrays.asList(args));
		if (argumentNumber < 0)
			return null;

		return node.getTabCompletionList(argumentNumber, args[args.length - 1]);
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

	@Override
	public String getPermissionNode() {
		return commandTree.getRoot().getAnnotation().permission();
	}

	@Override
	public PermissionLevel getPermissionLevel() {
		return PermissionLevel.fromBoolean(!(PermissionProxy.getPermissionManager() instanceof ConstitutionBridge));
	}

}
