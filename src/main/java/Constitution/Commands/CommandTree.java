package Constitution.Commands;

import java.util.List;
import java.util.UUID;

import Constitution.Localization.Localization;
import Constitution.Permissions.IPermissionBridge;
import Constitution.Permissions.PermissionProxy;
import Constitution.Permissions.Tree;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;

public class CommandTree extends Tree<CommandTreeNode> {

	private Localization local;
	private IPermissionBridge customManager;

	public CommandTree(CommandTreeNode root, Localization local) {
		super(root);
		this.local = local;
	}

	public CommandTree(CommandTreeNode root, Localization local, IPermissionBridge customManager) {
		this(root, local);
		this.customManager = customManager;
	}

	public void commandCall(ICommandSender sender, List<String> args) throws CommandException {
		CommandTreeNode node = getRoot();
		while (!args.isEmpty() && node.getChild(args.get(0)) != null) {
			node = node.getChild(args.get(0));
			args = args.subList(1, args.size());
		}

		if (hasPermission(sender, node)) {
			node.commandCall(sender, args);
		}
	}

	public CommandTreeNode getNodeFromArgs(List<String> args) {
		CommandTreeNode child = getRoot();
		while (!args.isEmpty() && child.getChild(args.get(0)) != null) {
			child = child.getChild(args.get(0));
			args = args.subList(1, args.size());
		}
		return child;
	}

	public int getArgumentNumber(List<String> args) {
		CommandTreeNode current = getRoot();
		while (!args.isEmpty() && current.getChild(args.get(0)) != null) {
			current = current.getChild(args.get(0));
			args = args.subList(1, args.size());
		}

		return args.size() - 1;
	}

	public boolean hasCommandNode(String perm) {
		return hasCommandNode(getRoot(), perm);
	}

	public boolean hasCommandNode(CommandTreeNode current, String perm) {
		if (perm.equals(current.getAnnotation().permission()))
			return true;

		boolean exists = false;
		for (CommandTreeNode child : current.getChildren()) {
			if (hasCommandNode(child, perm))
				return true;
		}
		return false;
	}

	public boolean hasPermission(ICommandSender sender, CommandTreeNode node) throws CommandException {
		if (!node.getAnnotation().console() && (sender instanceof MinecraftServer || sender instanceof RConConsoleSource
				|| sender instanceof CommandBlockBaseLogic)) {
			throw new CommandException("commands.generic.permission");
		}

		if (sender instanceof EntityPlayer) {
			UUID uuid = ((EntityPlayer) sender).getUniqueID();
			String permission = node.getAnnotation().permission();

			if (PermissionProxy.getPermissionManager().hasPermission(uuid, permission)
					|| (customManager != null && customManager.hasPermission(uuid, permission))) {
				return true;
			}
			throw new CommandException("commands.generic.permission");
		}
		return true;
	}

	public Localization getLocal() {
		return local;
	}
}
