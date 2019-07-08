package constitution.chat;


import java.util.UUID;

import constitution.ConstitutionMain;
import constitution.chat.channels.Channel;
import constitution.chat.component.ChatComponentBorders;
import constitution.chat.component.ChatComponentFormatted;
import constitution.localization.LocalizationManager;
import constitution.permissions.ConstitutionBridge;
import constitution.permissions.Group;
import constitution.permissions.User;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

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
	@SuppressWarnings("unchecked")
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

	public static void sendUniversalChanneledMessage(ConstitutionBridge manager, EntityPlayerMP player, ITextComponent component, String msg) {
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
				String modifiedUUID = user.getUUID().toString().replace("-", ""); //Decrease the Character Count by removing "-" between characters.
				Channel channel = user.getChannelObject();
				String userName = colorCode + player.getDisplayNameString();
				ITextComponent channelPrefix = new TextComponentString(channel.getPrefix());
				ITextComponent modifiedTextComponent = new TextComponentString(modifiedComponent);
				ITextComponent groupPrefix = new TextComponentString(group.getPrefix());
				ITextComponent groupSuffix = new TextComponentString(group.getSuffix());
				ITextComponent userPrefix = new TextComponentString(user.getPrefix());
				ITextComponent userSuffix = new TextComponentString(user.getSuffix());
				ITextComponent singleSpace = new TextComponentString(" ");
				ITextComponent Colon = new TextComponentString(": ");
				ITextComponent message = new TextComponentString(msg.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1"));

				if (!group.getPrefix().equals("")) {
					int index = group.getPrefix().lastIndexOf("§")-1;
					colorCode = group.getPrefix().substring(index, index+1);
					if (!user.getPrefix().equals("")) {
						index = user.getPrefix().lastIndexOf("§")-1;
						colorCode = user.getPrefix().substring(index, index+1);
					}
				} else {
					if (!user.getPrefix().equals("")) {
						int index = user.getPrefix().lastIndexOf("§")-1;
						colorCode = user.getPrefix().substring(index, index+1);
					}
				}
				ConstitutionMain.logger.info(userName);
				ITextComponent channelHeader = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{9|%s}", ChatComponentBorders.borderEditorHover((channel.getName().toString()))));
				ITextComponent channelHoverComponent =  ((ChatComponentFormatted)LocalizationManager.get("constitution.format.channel.long.hover",
						channelHeader,
						channel.getEnabled(),
						channel.getForced(),
						channel.getHidden(),
						channel.getVerbose(),
						channel.getDimensions(),
						channel.getPassword(),
						channel.getUsers().toChatMessage(),
						channel.getModerators(),
						channel.getMutedUsers(),
						channel.getBlackListedUsers(),
						channel.getWhiteListedUsers())).applyDelimiter("\n");
				ITextComponent groupHeader = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{9|%s}", ChatComponentBorders.borderEditorHover((group.getName()))));
				ITextComponent groupHoverComponent = ((ChatComponentFormatted)LocalizationManager.get("constitution.format.group.long.hover",
						groupHeader,
						group.getDesc(),
						group.getRank(),
						group.getPrefix(),
						group.getSuffix(),
						group.getNodes())).applyDelimiter("\n");
				ITextComponent userHeader = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{9|%s}", ChatComponentBorders.borderEditorHover(user.getUserName())));
				ITextComponent userHoverComponent = ((ChatComponentFormatted)LocalizationManager.get("constitution.format.user.long.hover",
						userHeader, 						
						modifiedUUID,
						user.getPrefix(),
						user.getSuffix(),
						user.getLocationAsString(),
						user.getGroups().toChatMessage(),
						user.getIP(),
						user.getNodes())).applyDelimiter("\n");
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

				for (User receivers : channel.users) {
					if (receivers.hasPermission("constitution.perm.hover.event")) {
						channel.sendMessage(finalComponentWithHover);
					} else {
						channel.sendMessage(finalComponentWithoutHover);
					}
				}
			}
		}
	}
}