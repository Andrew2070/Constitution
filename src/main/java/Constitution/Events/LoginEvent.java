package constitution.events;
import java.net.SocketAddress;
import java.util.Date;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import constitution.ConstitutionMain;
import constitution.chat.channels.Channel;
import constitution.configuration.Config;
import constitution.permissions.Group;
import constitution.permissions.PermissionManager;
import constitution.permissions.User;
import constitution.utilities.ServerUtilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
public class LoginEvent {
	
	public static final LoginEvent instance = new  LoginEvent();
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
		PermissionManager manager = ServerUtilities.getManager();
		if (event.player instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.player;
			String displayName = player.getDisplayNameString();
			UUID playerUUID = player.getPersistentID();
			GameProfile profile = player.getGameProfile();
			SocketAddress socketAddress = player.connection.getNetworkManager().getRemoteAddress();
			if (!ServerUtilities.getMinecraftServer().isSinglePlayer()) {
				ITextComponent crackedMessage = new TextComponentString("Cracked Clients Not Supported By Constitution Mod");
				if (ServerUtilities.getMinecraftServer().isServerInOnlineMode()) {
					ITextComponent banMessage = new TextComponentString("You Are Currently Banned From This Server");
					if (manager.users != null) {
						if (!isPlayerBanned(player)) {
							//Case 1: UUID and DisplayName don't exist in array:
							if (!manager.users.contains(playerUUID) || !manager.users.contains(displayName)) {
								User newUser = new User(player, new Date(System.currentTimeMillis()), System.currentTimeMillis());
								newUser.setChannel(Config.instance.defaultChatChannel.get());
								manager.users.add(newUser);
								manager.saveUsers();
								Channel defaultChannel = manager.channels.get(Config.instance.defaultChatChannel.get());
								defaultChannel.setUser(newUser);
								manager.saveChannels();
								Group defaultGroup = manager.groups.get(Config.instance.defaultGroupName.get());
								defaultGroup.setUser(playerUUID);
								manager.saveGroups();
								ConstitutionMain.logger.info("New User Created For: " + displayName);
							} else {
								//Case 2: UUID exists (Existing User Returning):
								if(manager.users.contains(playerUUID)) {
									User existingUser = manager.users.get(playerUUID);
									if (Config.instance.forceDefaultChannelLogin.get() == true) {
										existingUser.setChannel(Config.instance.defaultChatChannel.get());
									}
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
							if (ServerUtilities.getMinecraftServer().getPlayerList().getBannedPlayers().isBanned(profile)) {
								user.setBanned(true);
							}
							if (ServerUtilities.getMinecraftServer().getPlayerList().getBannedIPs().isBanned(socketAddress)) {
								user.setIPBanned(true);
							}
							manager.saveUsers();
							player.connection.disconnect(banMessage);
							event.setCanceled(true);
						}
					}
				} else {
					//Case 4: Offline Mode Enabled: Disconnect All Non Operator Connecting Players:
					if (!ServerUtilities.isOP(playerUUID)) {
						player.connection.disconnect(crackedMessage);
						event.setCanceled(true);
					}
				}
			} else {
				//Case 5: First Join on SinglePlayer or Developer Environment With This UUID (Mod Testing Purposes):
				if (!manager.users.contains(playerUUID)) {
					User newUser = new User(player, new Date(System.currentTimeMillis()), System.currentTimeMillis());
					Channel defaultChannel = manager.channels.get(Config.instance.defaultChatChannel.get());
					Group defaultGroup = manager.groups.get(Config.instance.defaultGroupName.get());
					
					newUser.setOP(true);
					newUser.setChannel(Config.instance.defaultChatChannel.get());
					manager.users.add(newUser);
					manager.saveUsers();
					
					defaultChannel.setUser(newUser);
					manager.saveChannels();
					
					
					defaultGroup.setUser(playerUUID);
					manager.saveGroups();
					
					ConstitutionMain.logger.info("New User Created For: " + displayName);
				} 
				//Case 6: Returning Join on SinglePlayer or Developer Environment With This UUID (Mod Testing Purposes):
				else {
					//Do stuff for returning player:
					User existingUser = manager.users.get(playerUUID);
					if (Config.instance.forceDefaultChannelLogin.get() == true) {
						existingUser.setChannel(Config.instance.defaultChatChannel.get());
					}
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
	public Boolean isPlayerBanned(EntityPlayerMP player) {
		PermissionManager manager = ServerUtilities.getManager();
		UUID playerUUID = player.getPersistentID();
		GameProfile profile = player.getGameProfile();
		SocketAddress socketAddress = player.connection.getNetworkManager().getRemoteAddress();
		User existingUser = manager.users.get(playerUUID);
		Boolean banned = false;
		if (ServerUtilities.getMinecraftServer().getPlayerList().getBannedIPs().isBanned(socketAddress)) {
			existingUser.setIPBanned(true);
			banned = true;
		}
		if (ServerUtilities.getMinecraftServer().getPlayerList().getBannedPlayers().isBanned(profile)) {
			existingUser.setBanned(true);
			banned = true;
		}

		if (existingUser.isBanned() || existingUser.isIPBanned()) {
			banned = true;
		}
		return banned;
	}
}
