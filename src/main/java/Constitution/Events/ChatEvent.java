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
public class ChatEvent {

	public static final ChatEvent instance = new ChatEvent();
	
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void serverChatEvent(ServerChatEvent event) {
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
				
				if (!user.getPrefix().equals("")) {
					//Apply user prefix color to username:
				
				}
			} else {
				if (!user.getPrefix().equals("")) {
					//Apply user prefix color to username:

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
