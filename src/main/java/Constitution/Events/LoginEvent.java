package constitution.events;
import constitution.ConstitutionMain;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.configuration.Config;
import constitution.permissions.ConstitutionBridge;
import constitution.permissions.Group;
import constitution.permissions.User;
import constitution.utilities.PlayerUtilities;
import constitution.utilities.VanillaUtilities;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

import java.net.SocketAddress;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
public class LoginEvent {
	
	public static final  LoginEvent instance = new  LoginEvent();
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
}
