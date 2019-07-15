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
 *    This product includes software developed by [the] Auxility Project.
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
package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.chat.channels.Channel;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.permissions.PermissionManager;
import constitution.utilities.ServerUtilities;
import net.minecraft.command.ICommandSender;
public class channel {
	
	private static PermissionManager getManager() {
		return ServerUtilities.getManager();
	}
	
	@Command(name = "channel", permission = "constitution.cmd.channel", syntax = "/channel", alias = {"ch", "CH", "Ch"}, description = "")
	public static CommandResponse channelCommandMethod(ICommandSender sender, List<String> args) {
		return CommandResponse.SEND_HELP_MESSAGE;
	}
	
	@Command(name = "create",
			permission = "constitution.cmd.channel.create",
			parentName = "constitution.cmd.channel",
			syntax = "/ch create <name> <password>",
			alias = {},
			description = "")
	public static CommandResponse channelCreate(ICommandSender sender, List<String> args) {
		if (args.size()<1) {
			return CommandResponse.SEND_SYNTAX;
		}
		String name = args.get(0);
		String password = "";
		if (args.size() == 2) {
			password = args.get(1);
		}
		
		Channel channel = new Channel(name, password);
		getManager().channels.add(channel);
		getManager().saveChannels();
		return CommandResponse.DONE;
	}

	@Command(name = "prefix",
			permission = "constitution.cmd.channel.prefix",
			parentName = "constitution.cmd.channel",
			syntax = "/ch prefix",
			alias = {},
			description = "")
	public static CommandResponse channelPrefix(ICommandSender sender, List<String> args) {
		return CommandResponse.SEND_HELP_MESSAGE;
	}
	
	@Command(name = "set",
			permission = "constitution.cmd.channel.prefix.set",
			parentName = "constitution.cmd.channel.prefix",
			syntax = "/ch prefix set <channel> <prefix>",
			alias = {},
			description = "")
	public static CommandResponse channelPrefixAdd(ICommandSender sender, List<String> args) {
		if (args.size()<2) {
			return CommandResponse.SEND_SYNTAX;
		}
		if (getManager().channels.get(args.get(0))!=null) {
			Channel channel = getManager().channels.get(args.get(0));
			channel.setPrefix(args.get(1));
			getManager().saveChannels();
		}
		return CommandResponse.DONE;
	}
	
	@Command(name = "clear",
			permission = "constitution.cmd.channel.prefix.clear",
			parentName = "constitution.cmd.channel.prefix",
			syntax = "/ch prefix clear <channel>",
			alias = {},
			description = "")
	public static CommandResponse channelPrefixClear(ICommandSender sender, List<String> args) {
		if (args.size()<2) {
			return CommandResponse.SEND_SYNTAX;
		}
		if (getManager().channels.get(args.get(0))!=null) {
			Channel channel = getManager().channels.get(args.get(0));
			channel.setPrefix("");
			getManager().saveChannels();
		}
		return CommandResponse.DONE;
	}
}
