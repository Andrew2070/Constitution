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
package constitution.chat;


import java.util.UUID;

import constitution.ConstitutionMain;
import constitution.chat.channels.Channel;
import constitution.chat.component.ChatComponentBorders;
import constitution.chat.component.ChatComponentFormatted;
import constitution.localization.LocalizationManager;
import constitution.permissions.Group;
import constitution.permissions.PermissionManager;
import constitution.permissions.User;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;

public class ChatManager {

	/**
	 * Global method for sending localized messages
	 */
	public static void send(ICommandSender sender, String localizationKey, Object... args) {
		send(sender, LocalizationManager.get(localizationKey, args));
	}

	/**
	 * Global method for sending messages If the message sent is a
	 * ChatComponentList then only its siblings are sent, omitting the root
	 * component
	 */
	public static void send(ICommandSender sender, ITextComponent message) {
		if (sender == null) {
			return;
		}

		if (message == null) {
			return;
		}

		if (message instanceof ITextComponent) {
			for (ITextComponent sibling : message.getSiblings()) {
				if (sibling == null) {
					continue;
				}

				addChatMessageFixed(sender, sibling);
			}
		} else {
			addChatMessageFixed(sender, message);
		}
	}

	public static void addChatMessageFixed(ICommandSender sender, ITextComponent message) {
		if (sender == null || message == null)
			return;
		if (sender instanceof EntityPlayerMP) {
			if (((EntityPlayerMP) sender).connection != null) {
				sender.sendMessage(message);
			} else {
				// TODO Find a way to re-send the message.
			}
		} else {
			sender.sendMessage(message);
		}
	}

	public static void sendUniversalChanneledMessage(PermissionManager manager, EntityPlayerMP player, ITextComponent component, String msg) {
		if (player!=null) {
			if (msg!=null && !msg.isEmpty()) {
				
				String playerName = player.getDisplayNameString();
				UUID playerUUID = player.getUniqueID();
				User user = manager.users.get(playerUUID);
				Group group = user.getDominantGroup();
				String originalComponent = component.getFormattedText();
				Integer playerNameIndex = originalComponent.indexOf(playerName);
				String modifiedComponent = originalComponent.substring(0, playerNameIndex-3);
				String colorCode = "";
				Channel channel = manager.channels.get(user.getChannel());
				String userName = colorCode + player.getDisplayNameString();
				
				//Access Control Strings:
				
				//User:
				String userPrefixAC = user.getPrefix();
				String userSuffixAC = user.getSuffix();
				String userNodesAC = user.getNodes();
				String userUUIDAC = user.getUUID().toString().replace("-", "");
				String userLocationAC = user.getLocationAsString();
				String userIPAC = user.getIP();
				Group userGroupsAC = user.getGroups();
				
				//Group:
				Integer groupRankAC = group.getRank();
				String groupDescAC = group.getDesc();
				String groupPrefixAC = group.getPrefix();
				String groupSuffixAC = group.getSuffix();
				String groupNodesAC = group.getNodes();
				
				//TODO: Channel?

				ITextComponent channelPrefix = new TextComponentString(channel.getPrefix());
				ITextComponent modifiedTextComponent = new TextComponentString(modifiedComponent);
				ITextComponent groupPrefix = new TextComponentString(group.getPrefix());
				ITextComponent groupSuffix = new TextComponentString(group.getSuffix());
				ITextComponent userPrefix = new TextComponentString(user.getPrefix());
				ITextComponent userSuffix = new TextComponentString(user.getSuffix());
				ITextComponent singleSpace = new TextComponentString(" ");
				ITextComponent Colon = new TextComponentString(": ");
				ITextComponent message = new TextComponentString(msg.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1"));

				ITextComponent channelHeader = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{9|%s}", ChatComponentBorders.borderEditorHover((channel.getName().toString()))));
				ITextComponent channelHoverComponent =  ((ChatComponentFormatted)LocalizationManager.get("constitution.format.channel.long.hover",
						channelHeader,
						channel.getEnabled(),
						channel.getForced(),
						channel.getHidden(),
						channel.getVerbose(),
						channel.getDimensions(),
						channel.getPassword(),
						channel.getModerators(),
						channel.getMutedUsers(),
						channel.getBlackListedUsers(),
						channel.getWhiteListedUsers(),
						channel.getUsers().toChatMessage())).applyDelimiter("\n");
				channelHoverComponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ch " + channel.getName() + " "));
				ITextComponent groupHeader = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{9|%s}", ChatComponentBorders.borderEditorHover((group.getName()))));
				ITextComponent groupHoverComponent = ((ChatComponentFormatted)LocalizationManager.get("constitution.format.group.long.hover",
						groupHeader,
						groupDescAC,
						groupRankAC,
						groupPrefixAC,
						groupSuffixAC,
						groupNodesAC)).applyDelimiter("\n");
				groupHoverComponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cperm group info " + group.getName()));
				ITextComponent userHeader = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{9|%s}", ChatComponentBorders.borderEditorHover(user.getUserName())));
				ITextComponent userHoverComponent = ((ChatComponentFormatted)LocalizationManager.get("constitution.format.user.long.hover",
						userHeader, 						
						userUUIDAC,
						userPrefixAC,
						userSuffixAC,
						userLocationAC,
						userGroupsAC,
						userIPAC,
						userNodesAC)).applyDelimiter("\n");
				userHoverComponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + userName + " "));
				ITextComponent groupPrefixHover = LocalizationManager.get("constitution.format.short", group.getPrefix(), groupHoverComponent);
				ITextComponent groupSuffixHover = LocalizationManager.get("constitution.format.short", group.getSuffix(), groupHoverComponent);
				ITextComponent userNameHover = LocalizationManager.get("constitution.format.short", userName, userHoverComponent);
				ITextComponent channelHover = LocalizationManager.get("constitution.format.short", channel.getPrefix(), channelHoverComponent);
				ITextComponent finalComponentWithHover = new TextComponentString("")
						.appendSibling(modifiedTextComponent)
						.appendSibling(channelHover)
						.appendSibling(groupPrefixHover)
						.appendSibling(userPrefix)
						.appendSibling(singleSpace)
						.appendSibling(userNameHover)
						.appendSibling(userSuffix)
						.appendSibling(groupSuffixHover)
						.appendSibling(Colon)
						.appendSibling(message);
				ITextComponent finalComponentWithoutHover = new TextComponentString("")
						.appendSibling(modifiedTextComponent)
						.appendSibling(channelPrefix)
						.appendSibling(groupPrefix)
						.appendSibling(userPrefix)
						.appendSibling(singleSpace)
						.appendSibling(player.getDisplayName())
						.appendSibling(userSuffix)
						.appendSibling(groupSuffix)
						.appendSibling(Colon)
						.appendSibling(message);

					if (manager.checkPermission(player, ""
							+ "")) {
						channel.sendMessage(finalComponentWithHover);
					} else {
						if (manager.checkPermission(player, "constitution.cmd.perm.user.list.partial")) {
							userUUIDAC = "Insufficient Viewership Permissions";
							userIPAC = "Insufficient Viewership Permissions";
							userLocationAC = "Insufficient Viewership Permissions";
							userNodesAC = "Insufficient Viewership Permissions";
							groupNodesAC ="Insufficient Viewership Permissions";
							channel.sendMessage(finalComponentWithHover);
						} else {
						channel.sendMessage(finalComponentWithoutHover);
						}
					ConstitutionMain.logger.info("[CHAT] " + finalComponentWithoutHover.getUnformattedText());
				}
			}
		}
	}
}
