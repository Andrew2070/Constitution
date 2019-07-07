package constitution.events;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.permissions.ConstitutionBridge;
import constitution.permissions.User;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

import java.util.List;
import java.util.UUID;
public class LogoutEvent {
	
	public static final LogoutEvent instance = new LogoutEvent();
	
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
}
