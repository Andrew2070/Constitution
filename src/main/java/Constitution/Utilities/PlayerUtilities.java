package Constitution.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import Constitution.Constitution;
import Constitution.Permissions.ConstitutionBridge;
import Constitution.Permissions.Group;
import Constitution.Permissions.PermissionProxy;
import Constitution.Permissions.User;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class PlayerUtilities {
	
	public static ConstitutionBridge getManager() {
		return (ConstitutionBridge) PermissionProxy.getPermissionManager();
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
			if (!VanillaUtilities.getMinecraftServer().isSinglePlayer()) {
				EntityPlayer player = VanillaUtilities.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid);
				if (player instanceof EntityPlayer) {
					if (player != null) {
						GameProfile profile = player.getGameProfile();
						Integer permissionLevelOP = VanillaUtilities.getMinecraftServer().getOpPermissionLevel();
						Constitution.logger.info("Multiplayer detected: Player: " + player.getDisplayNameString() + " isOP: " + VanillaUtilities.getMinecraftServer().getPlayerList().canSendCommands(profile));
						return VanillaUtilities.getMinecraftServer().getPlayerList().canSendCommands(profile);
					}
				}
			} else {
			Constitution.logger.info("Singleplayer Detected: Op Status True");
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
}
