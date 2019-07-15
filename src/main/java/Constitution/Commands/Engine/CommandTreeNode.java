/*******************************************************************************
 * Copyright (C) 2019, Andrew2070
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
 *    This product includes software developed by the <organization>.
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import constitution.ConstitutionMain;
import constitution.chat.ChatManager;
import constitution.chat.component.ChatComponentHelpMenu;
import constitution.exceptions.CommandException;
import constitution.localization.Localization;
import constitution.permissions.TreeNode;
import constitution.utilities.StringUtilities;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandTreeNode extends TreeNode<CommandTreeNode> {

	private Command commandAnnot;
	private Method method;
	private String localizationKey;

	private ChatComponentHelpMenu helpMenu;
	private Supplier<String> name = Suppliers.memoizeWithExpiration(new Supplier<String>() {
		@Override
		public String get() {
			String key = getLocalizationKey() + ".name";
			return getLocal().hasLocalization(key) ? getLocal().getLocalization(key).getUnformattedText()
					: getAnnotation().name();
		}
	}, 5, TimeUnit.MINUTES);

	private Supplier<String> syntax = Suppliers.memoizeWithExpiration(new Supplier<String>() {
		@Override
		public String get() {
			String key = getLocalizationKey() + ".syntax";
			return getLocal().hasLocalization(key) ? getLocal().getLocalization(key).getUnformattedText()
					: getAnnotation().syntax();
		}
	}, 5, TimeUnit.MINUTES);

	public CommandTreeNode(Command commandAnnot, Method method) {
		this(null, commandAnnot, method);
	}

	public CommandTreeNode(CommandTreeNode parent, Command commandAnnot, Method method) {
		this.parent = parent;
		this.commandAnnot = commandAnnot;
		this.method = method;

		String name = getAnnotation().name();
		CommandTreeNode parentNode = this;
		while ((parentNode = parentNode.getParent()) != null) {
			name = parentNode.getAnnotation().name() + "." + name;
		}
		localizationKey = "command." + name;
	}

	public Command getAnnotation() {
		return commandAnnot;
	}

	public Method getMethod() {
		return method;
	}

	public void commandCall(ICommandSender sender, List<String> args) {

		/*
		 * // Check if the player has access to the command using the
		 * firstpermissionbreach method first Method permMethod =
		 * firstPermissionBreaches.get(permission); if(permMethod != null) {
		 * Boolean result = true; try { result =
		 * (Boolean)permMethod.invoke(null, permission, sender); } catch
		 * (Exception e) {
		 * Empires.instance.LOG.error(ExceptionUtils.getStackTrace(e)); }
		 * if(!result) { // If the first permission breach did not allow the
		 * method to be called then call is aborted throw new
		 * CommandException("commands.generic.permission"); } }
		 */

		try {
			CommandResponse response = (CommandResponse) method.invoke(null, sender, args);
			if (response == CommandResponse.SEND_HELP_MESSAGE) {
				int page = 1;
				if (!args.isEmpty() && StringUtilities.tryParseInt(args.get(0)))
					page = Integer.parseInt(args.get(0));
				sendHelpMessage(sender, page);
			} else if (response == CommandResponse.SEND_SYNTAX) {
				sendSyntax(sender);
			}
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof CommandException) {
				ChatManager.send(sender, ((CommandException) e.getCause()).message);
			} else if (e.getCause() instanceof RuntimeException) {
				throw (RuntimeException) e.getCause();
			} else {
				ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(e));
			}
		} catch (IllegalAccessException e) {
			ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(e));
		}
	}

	public List<String> getTabCompletionList(int argumentNumber, String argumentStart) {
		List<String> completion = new ArrayList<String>();
		if (commandAnnot.completionKeys().length == 0) {
			for (CommandTreeNode child : getChildren()) {
				String localizedCommand = child.getLocalizedName();
				if (localizedCommand.startsWith(argumentStart)) {
					completion.add(localizedCommand);
				}
			}
		} else {
			if (argumentNumber < commandAnnot.completionKeys().length) {
				for (String s : CommandCompletion.getCompletionList(commandAnnot.completionKeys()[argumentNumber])) {
					if (s.startsWith(argumentStart)) {
						completion.add(s);
					}
				}
			}
		}
		return completion;
	}

	public void sendHelpMessage(ICommandSender sender, int page) {
		if (helpMenu == null) {
			helpMenu = new ChatComponentHelpMenu(9, this);
		}
		helpMenu.sendPage(sender, page);
	}

	public void sendSyntax(ICommandSender sender) {
		sender.sendMessage(new TextComponentString(TextFormatting.DARK_AQUA + getLocalizedSyntax()));
	}

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getLocalizedSyntax() {
		return syntax.get();
	}

	public String getLocalizedName() {
		return name.get();
	}

	public CommandTreeNode getChild(String name) {
		for (CommandTreeNode child : getChildren()) {
			if (child.getLocalizedName().equals(name)) {
				return child;
			} else {
				for (String alias : child.getAnnotation().alias()) {
					if (alias.equals(name)) {
						return child;
					}
				}
			}
		}
		return null;
	}

	public String getCommandLine() {
		if (getParent() == null)
			return "/" + getLocalizedName();
		else
			return getParent().getCommandLine() + " " + getLocalizedName();
	}

	public Localization getLocal() {
		return getCommandTree().getLocal();
	}

	public CommandTree getCommandTree() {
		CommandTreeNode node = this;

		while (node.getParent() != null) {
			node = node.getParent();
		}

		return CommandManager.getTree(node.getAnnotation().permission());

	}
}
