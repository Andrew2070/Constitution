/*******************************************************************************
 * Copyright (C) July/14/2019, Andrew2070
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement:
 *    This product includes software developed by Andrew2070.
 * 
 * 4. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package Constitution.Commands.Engine;

import java.util.List;
import java.util.UUID;

import constitution.localization.Localization;
import constitution.permissions.PermissionManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
public class CommandTree extends Tree<CommandTreeNode> {

	private Localization local;
	private PermissionManager permissionManager;

	public CommandTree(CommandTreeNode root, Localization local) {
		super(root);
		this.local = local;
	}

	public CommandTree(CommandTreeNode root, Localization local, PermissionManager permissionManager) {
		this(root, local);
		this.permissionManager = permissionManager;
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
			if (permissionManager.hasPermission(uuid, permission)
					|| (permissionManager != null && permissionManager.hasPermission(uuid, permission))) {
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
