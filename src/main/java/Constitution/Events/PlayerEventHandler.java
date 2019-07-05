package constitution.events;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import constitution.ConstitutionMain;
import constitution.chat.component.ChatComponentBorders;
import constitution.chat.component.ChatComponentFormatted;
import constitution.configuration.Config;
import constitution.localization.LocalizationManager;
import constitution.permissions.ConstitutionBridge;
import constitution.permissions.Group;
import constitution.permissions.User;
import constitution.utilities.PlayerUtilities;
import constitution.utilities.VanillaUtilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

/**
 * @author Andrew2070
 * Handles All Constitution/Player Related Minecraft Events:
 *
 */
public class PlayerEventHandler {

	public static final PlayerEventHandler instance = new PlayerEventHandler();


	public Boolean isPlayerBanned(EntityPlayerMP player) {
		ConstitutionBridge manager = PlayerUtilities.getManager();
		String displayName = player.getDisplayNameString();
		UUID playerUUID = player.getPersistentID();
		GameProfile profile = player.getGameProfile();
		SocketAddress socketAddress = player.connection.getNetworkManager().getRemoteAddress();
		User existingUser = manager.users.get(playerUUID);
		Boolean banned = false;
		if (VanillaUtilities.getMinecraftServer().getPlayerList().getBannedIPs().isBanned(socketAddress)) {
			existingUser.setIPBanned(true);
			banned = true;
		}
		if (VanillaUtilities.getMinecraftServer().getPlayerList().getBannedPlayers().isBanned(profile)) {
			existingUser.setBanned(true);
			banned = true;
		}

		if (existingUser.isBanned() || existingUser.isIPBanned()) {
			banned = true;
		}
		return banned;
	}

	/**
	 * @param AuthenticatePlayerLogIn
	 * Check For Non Existing User Status:
	 * If User List Does Not Contain Player UUID or DisplayName:
	 * Create New User with Default Group and Add To List.
	 * Otherwise, check for Alternative Account User by UUID or DisplayName:
	 * If an Alternative Account User exists then change it's data to include new Player Account data.
	 */
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void AuthenticatePlayerLogIn(PlayerLoggedInEvent event) {
		ConstitutionBridge manager = PlayerUtilities.getManager();
		if (event.player instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.player;
			String displayName = player.getDisplayNameString();
			UUID playerUUID = player.getPersistentID();
			GameProfile profile = player.getGameProfile();
			SocketAddress socketAddress = player.connection.getNetworkManager().getRemoteAddress();
			if (!VanillaUtilities.getMinecraftServer().isSinglePlayer()) {
				ITextComponent crackedMessage = new TextComponentString("Cracked Clients Not Supported By Constitution Mod");
				if (VanillaUtilities.getMinecraftServer().isServerInOnlineMode()) {
					ITextComponent banMessage = new TextComponentString("You Are Currently Banned From This Server");
					if (manager.users != null) {
						if (!isPlayerBanned(player)) {
							//Case 1: UUID and DisplayName don't exist in array:
							if (!manager.users.contains(playerUUID) || !manager.users.contains(displayName)) {
								User newUser = new User(player, new Date(System.currentTimeMillis()), System.currentTimeMillis());
								manager.users.add(newUser);
								manager.saveUsers();

								Group defaultGroup = manager.groups.get(Config.instance.defaultGroupName.get());
								defaultGroup.setUser(playerUUID);
								manager.saveGroups();
								ConstitutionMain.logger.info("New User Created For: " + displayName);
							} else {
								//Case 2: UUID exists (Existing User Returning):
								if(manager.users.contains(playerUUID)) {
									User existingUser = manager.users.get(playerUUID);
									//Case 3: UUID exists but DisplayName is different (Alternative Account or Player Changed Their Username):
									if(!manager.users.contains(displayName)) {
										existingUser.setLastPlayerName(existingUser.getUserName());
										existingUser.setUserName(displayName);
										ConstitutionMain.logger.info("Existing User Modified For Alt: " + displayName);
									}
									//Do a bunch of stuff for returning User:
									if (Config.instance.setLastGameMode.get() == true) {
										if (existingUser.isCreative() == true) {
											player.setGameType(GameType.CREATIVE);
										} else {
											player.setGameType(GameType.SURVIVAL);
										}
									}
									manager.saveUsers();
								}
								//Case 4: DisplayName exists but UUID is different (Cracked Player Attempting Connection):
								if(!manager.users.contains(playerUUID) && manager.users.contains(displayName)) {
									player.connection.disconnect(crackedMessage);
									event.setCanceled(true);
								}	
							}	
						} else {
							//Case 5: Banned Player Connecting:
							User user = manager.users.get(playerUUID);
							if (VanillaUtilities.getMinecraftServer().getPlayerList().getBannedPlayers().isBanned(profile)) {
								user.setBanned(true);
							}
							if (VanillaUtilities.getMinecraftServer().getPlayerList().getBannedIPs().isBanned(socketAddress)) {
								user.setIPBanned(true);
							}
							manager.saveUsers();
							player.connection.disconnect(banMessage);
							event.setCanceled(true);
						}
					}
				} else {
					//Case 4: Offline Mode Enabled: Disconnect All Connecting Players:
					player.connection.disconnect(crackedMessage);
					event.setCanceled(true);
				}
			} else {
				//Case 5: First Join on SinglePlayer or Developer Environment With This UUID (Mod Testing Purposes):
				if (!manager.users.contains(playerUUID)) {
					User newUser = new User(player, new Date(System.currentTimeMillis()), System.currentTimeMillis());
					newUser.setOP(true);
					manager.users.add(newUser);
					manager.saveUsers();

					Group defaultGroup = manager.groups.get(Config.instance.defaultGroupName.get());
					defaultGroup.setUser(playerUUID);
					manager.saveGroups();
					ConstitutionMain.logger.info("New User Created For: " + displayName);
				} 
				//Case 6: Returning Join on SinglePlayer or Developer Environment With This UUID (Mod Testing Purposes):
				else {
					//Do stuff for returning player:
					User existingUser = manager.users.get(playerUUID);
					if (Config.instance.setLastGameMode.get() == true) {
						if (existingUser.isCreative() == true) {
							player.setGameType(GameType.CREATIVE);
						} else {
							player.setGameType(GameType.SURVIVAL);
						}
					}
					manager.saveUsers();
				}
			}
		}
	}

	@SubscribeEvent
	public void AuthenticatePlayerLogout(PlayerLoggedOutEvent event) {
		ConstitutionBridge manager = PlayerUtilities.getManager();
		if (event.player instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.player;
			UUID uuid = player.getUniqueID();
			User user = manager.users.get(uuid);
			user.setCanFly(player.capabilities.isFlying);
			user.setCreative(player.capabilities.isCreativeMode);
			user.setDimension(player.dimension);
			user.setGodMode(player.getIsInvulnerable());
			user.setHealth(player.getHealth());
			user.setLocation(player.getPosition());
			user.setXPTotal(player.experienceTotal);	 
			user.setIP(player.getPlayerIP());
		}
	}

	//@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void serverChatEvent(ServerChatEvent event) {
		ConstitutionBridge manager = PlayerUtilities.getManager();
		if (event.getPlayer() instanceof EntityPlayerMP) {
			if (manager.users != null) {
				EntityPlayerMP player = event.getPlayer();
				String playerName = player.getDisplayNameString();
				User user = manager.users.get(player.getUniqueID());
				Group group = user.getDominantGroup();
				String originalComponent = event.getComponent().getFormattedText();
				Integer playerNameIndex = originalComponent.indexOf(playerName);
				String modifiedComponent = originalComponent.substring(0, playerNameIndex-3);
				TextFormatting color = TextFormatting.AQUA;
				ITextComponent username = user.toChatMessage();
				username.getStyle().setColor(color);

				if (!group.getPrefix().equals("")) {
					if (!user.getPrefix().equals("")) {
					}
				}

				ITextComponent header = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{9|%s}", ChatComponentBorders.borderEditorHover((group.getName()))));
				ITextComponent hoverComponent = ((ChatComponentFormatted)LocalizationManager.get("constitution.format.group.long.hover",
						header,
						group.getDesc(),
						group.getRank(),
						group.getPrefix(),
						group.getSuffix(),
						group.getNodes())).applyDelimiter("\n");
				ITextComponent hc = LocalizationManager.get("constitution.format.short", group.getPrefix(), hoverComponent);

				ITextComponent finalComponent = new TextComponentString("")
						.appendSibling(new TextComponentString(modifiedComponent))
						.appendSibling(hc)
						.appendSibling(new TextComponentString(user.getPrefix()))
						.appendSibling(new TextComponentString(" "))
						.appendSibling(username)
						.appendSibling(new TextComponentString(user.getSuffix()))
						.appendSibling(new TextComponentString(group.getSuffix()))
						.appendSibling(new TextComponentString(": "))
						.appendSibling(new TextComponentString(event.getMessage().replaceAll("\u0026([\\da-fk-or])", "\u00A7$1")));

				event.setComponent(finalComponent);

				//todo make visible only to ops, and fix colors of hover stuff.

			}
		}
	}
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void serverChatEvent2(ServerChatEvent event) {
		ConstitutionBridge manager = PlayerUtilities.getManager();
		if (manager.users != null) {
			event.setCanceled(true);
			EntityPlayerMP player = event.getPlayer();
			User user = manager.users.get(player.getUniqueID());
			Group group = user.getDominantGroup();
			
			String playerName = player.getDisplayNameString();
			String originalComponent = event.getComponent().getFormattedText();
			Integer playerNameIndex = originalComponent.indexOf(playerName);
			String modifiedComponent = originalComponent.substring(0, playerNameIndex-3);
			String optUsernameFormattingCode = "";
			
			ITextComponent modifiedTextComponent = new TextComponentString(modifiedComponent);
			ITextComponent userName = new TextComponentString(player.getDisplayNameString());
			ITextComponent groupPrefix = new TextComponentString(group.getPrefix());
			ITextComponent groupSuffix = new TextComponentString(group.getSuffix());
			ITextComponent userPrefix = new TextComponentString(user.getPrefix());
			ITextComponent userSuffix = new TextComponentString(user.getSuffix());
			ITextComponent singleSpace = new TextComponentString(" ");
			ITextComponent Colon = new TextComponentString(": ");
			ITextComponent message = new TextComponentString(event.getMessage().replaceAll("\u0026([\\da-fk-or])", "\u00A7$1"));
			
			ITextComponent header = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{9|%s}", ChatComponentBorders.borderEditorHover((group.getName()))));
			ITextComponent hoverComponent = ((ChatComponentFormatted)LocalizationManager.get("constitution.format.group.long.hover",
					header,
					group.getDesc(),
					group.getRank(),
					group.getPrefix(),
					group.getSuffix(),
					group.getNodes())).applyDelimiter("\n");
			ITextComponent groupPrefixHover = LocalizationManager.get("constitution.format.short", group.getPrefix(), hoverComponent);
			ITextComponent groupSuffixHover = LocalizationManager.get("constitution.format.short", group.getSuffix(), hoverComponent);
			ITextComponent userNameHover = user.toChatMessage();
			
			if (!group.getPrefix().equals("")) {
					//Apply group prefix color to username:
				optUsernameFormattingCode = groupPrefix.getStyle().getFormattingCode();
				if (!user.getPrefix().equals("")) {
					//Apply user prefix color to username:
				optUsernameFormattingCode = userPrefix.getStyle().getFormattingCode();
				}
			} else {
				if (!user.getPrefix().equals("")) {
					//Apply user prefix color to username:
				optUsernameFormattingCode = userPrefix.getStyle().getFormattingCode();
				}
			}
			
			
			ITextComponent finalComponentWithHover = new TextComponentString("")
					.appendSibling(modifiedTextComponent)
					.appendSibling(groupPrefixHover)
					.appendSibling(userPrefix)
					.appendSibling(singleSpace)
					.appendSibling(new TextComponentString(optUsernameFormattingCode))
					.appendSibling(userNameHover)
					.appendSibling(userSuffix)
					.appendSibling(groupSuffixHover)
					.appendSibling(Colon)
					.appendSibling(message);

			ITextComponent finalComponentWithoutHover = new TextComponentString("")
					.appendSibling(modifiedTextComponent)
					.appendSibling(groupPrefix)
					.appendSibling(userPrefix)
					.appendSibling(singleSpace)
					.appendSibling(userName)
					.appendSibling(userSuffix)
					.appendSibling(groupSuffix)
					.appendSibling(Colon)
					.appendSibling(message);

			List<EntityPlayerMP> playerList = new ArrayList<EntityPlayerMP>(player.getServer().getPlayerList().getPlayers());
			for (EntityPlayerMP playerMP : playerList) {
				if (manager.users.get(playerMP.getUniqueID())!=null) {
					User playerMPUser = manager.users.get(playerMP.getUniqueID());
					if (playerMPUser.permsContainer.contains("constitution.cmd.perm.hover.event")) {
						//TODO: Swap this check with hasPermission check after testing
						playerMP.sendMessage(finalComponentWithHover);
					} else {
						playerMP.sendMessage(finalComponentWithoutHover);
					}
				}
			}
		}
	}
}
