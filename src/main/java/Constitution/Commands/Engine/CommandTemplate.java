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
package constitution.commands.engine;

import java.util.Arrays;
import java.util.List;

import constitution.permissions.PermissionObject;
import constitution.utilities.ServerUtilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.server.permission.DefaultPermissionLevel;

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
		return ServerUtilities.getManager().hasPermission(sender.getCommandSenderEntity().getUniqueID(), commandTree.getRoot().getAnnotation().permission());
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
	public DefaultPermissionLevel getPermissionLevel() {
		return DefaultPermissionLevel.ALL;
	}

}
