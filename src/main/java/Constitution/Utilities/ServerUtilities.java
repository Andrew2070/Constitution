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
import constitution.permissions.Group;
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
