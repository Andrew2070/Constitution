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
import java.util.UUID;

import constitution.permissions.PermissionManager;
import constitution.permissions.User;
import constitution.utilities.ServerUtilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
public class LogoutEvent {
	
	public static final LogoutEvent instance = new LogoutEvent();
	
	@SubscribeEvent
	public void AuthenticatePlayerLogout(PlayerLoggedOutEvent event) {
		PermissionManager manager = ServerUtilities.getManager();
		if (event.player instanceof EntityPlayerMP && event.player !=null) {
			EntityPlayerMP player = (EntityPlayerMP) event.player;
			UUID uuid = player.getUniqueID();
			if (uuid !=null) {
				User user = manager.users.get(uuid);
				if (user !=null) {
					user.setCanFly(player.capabilities.isFlying);
					user.setCreative(player.capabilities.isCreativeMode);
					user.setDimension(player.dimension);
					user.setGodMode(player.getIsInvulnerable());
					user.setLocation(player.getPosition());	 
					user.setIP(player.getPlayerIP());
				}
			}
		}
	}
}
