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
								User newUser = new User(player, System.currentTimeMillis(), System.currentTimeMillis());
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
									Channel defaultChannel = manager.channels.get(Config.instance.defaultChatChannel.get());
									//Case 3: UUID exists but DisplayName is different (Alternative Account or Player Changed Their Username):
									//Do a bunch of stuff for returning User:
									if (Config.instance.setLastGameMode.get() == true) {
										if (existingUser.getCreative() == true) {
											player.setGameType(GameType.CREATIVE);
										} else {
											player.setGameType(GameType.SURVIVAL);
										}
									}
									if (Config.instance.forceDefaultChannelLogin.get() == true) {
										existingUser.setChannel(Config.instance.defaultChatChannel.get());
									}
									if(!manager.users.contains(displayName)) {
										existingUser.setLastPlayerName(existingUser.getUserName());
										existingUser.setUserName(displayName);
										ConstitutionMain.logger.info("Existing User Modified For Alt: " + displayName);
									}
									existingUser.setLastOnline(new Date());
									existingUser.setIP(player.getPlayerIP());
									manager.saveUsers();
									defaultChannel.setUser(existingUser);
									manager.saveChannels();
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
					User newUser = new User(player, System.currentTimeMillis(), System.currentTimeMillis());
					Channel defaultChannel = manager.channels.get(Config.instance.defaultChatChannel.get());
					Group defaultGroup = manager.groups.get(Config.instance.defaultGroupName.get());

					newUser.setOP(true);
					newUser.setChannel(Config.instance.defaultChatChannel.get());
					newUser.setDominantGroup();
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
					Channel defaultChannel = manager.channels.get(Config.instance.defaultChatChannel.get());
					if (Config.instance.forceDefaultChannelLogin.get() == true) {
						existingUser.setChannel(Config.instance.defaultChatChannel.get());
					}
					if (Config.instance.setLastGameMode.get() == true) {
						if (existingUser.getCreative() == true) {
							player.setGameType(GameType.CREATIVE);
						} else {
							player.setGameType(GameType.SURVIVAL);
						}
					}
					existingUser.setChannel(Config.instance.defaultChatChannel.get());
					existingUser.setLastOnline(new Date());
					existingUser.setIP(player.getPlayerIP());
					manager.saveUsers();
					defaultChannel.setUser(existingUser);
					manager.saveChannels();
				}
			}
		}
	}
	public Boolean isPlayerBanned(EntityPlayerMP player) {
		PermissionManager manager = ServerUtilities.getManager();
		UUID playerUUID = player.getPersistentID();
		GameProfile profile = player.getGameProfile();
		SocketAddress socketAddress = player.connection.getNetworkManager().getRemoteAddress();
		Boolean banned = false;
		if (manager.users.get(playerUUID) != null) {
			User existingUser = manager.users.get(playerUUID);
			if (ServerUtilities.getMinecraftServer().getPlayerList().getBannedIPs().isBanned(socketAddress)) {
				existingUser.setIPBanned(true);
				banned = true;
			}
			if (ServerUtilities.getMinecraftServer().getPlayerList().getBannedPlayers().isBanned(profile)) {
				existingUser.setBanned(true);
				banned = true;
			}

			if (existingUser.getBanned() || existingUser.getIPBanned()) {
				banned = true;
			}
		}
		return banned;
	}
}
