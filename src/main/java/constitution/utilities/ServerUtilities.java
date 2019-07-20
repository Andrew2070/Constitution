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
package constitution.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import constitution.ConstitutionMain;
import constitution.exceptions.PermissionCommandException;
import constitution.localization.LocalizationManager;
import constitution.permissions.PermissionManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ServerUtilities {


	protected static UUID getUUIDFromUsername(String username) {
		UUID uuid = ServerUtilities.getUUIDFromName(username);
		if (uuid == null) {
			throw new PermissionCommandException("constitution.cmd.perm.err.player.notExist",
					LocalizationManager.get("constitution.format.user.short", username));
		}
		return uuid;
	}
	public static PermissionManager getManager() {
		return ConstitutionMain.getPermissionManager();
	}

	public static Group getGroupFromName(String name) {
		Group group = getManager().groups.get(name);
		return group;
	}

	@Nullable
	public static UUID getUUIDFromName(String name) {
		if ( FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(name).getPersistentID() != null); {
			UUID uuid = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(name).getPersistentID();
			if (uuid != null) {
				return uuid;
			}
		}
		return null;
	}

	public static Boolean isOP(UUID uuid) {

		if (uuid != null) {
			if (!ServerUtilities.getMinecraftServer().isSinglePlayer()) {
				EntityPlayer player = ServerUtilities.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid);
				if (player instanceof EntityPlayer) {
					if (player != null) {
						GameProfile profile = player.getGameProfile();
						Integer permissionLevelOP = ServerUtilities.getMinecraftServer().getOpPermissionLevel();
						return ServerUtilities.getMinecraftServer().getPlayerList().canSendCommands(profile);
					}
				}
			} else {
				return true;
			}
		}	
		return false;
	}

	public static List<String> regexMatcher(String text, String regex) {
		List<String> matchList= new ArrayList<String>();

		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			matchList.add(matcher.group(2));
			return matchList;
		}
		return matchList;
	}
	public static MinecraftServer getMinecraftServer() {
		return FMLCommonHandler.instance().getMinecraftServerInstance();
	}
}
