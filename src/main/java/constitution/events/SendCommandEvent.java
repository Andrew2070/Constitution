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

import constitution.ConstitutionMain;
import constitution.commands.engine.CommandManager;
import constitution.permissions.PermissionContext;
import constitution.permissions.PermissionManager;
import constitution.utilities.ServerUtilities;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.server.command.TextComponentHelper;

public class SendCommandEvent {

	public static SendCommandEvent instance = new SendCommandEvent();

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public Boolean onPlayerSendCommand(CommandEvent event) {
		PermissionManager manager = ServerUtilities.getManager();
		PermissionContext context = new PermissionContext(event.getSender());
		if (!manager.checkPermission(event.getSender(), event.getCommand())) {
			ConstitutionMain.logger.info("Command Canceled For:" + event.getSender().toString() + ": Command: " + event.getCommand().getName());
			event.setCanceled(true);
			ITextComponent msg = TextComponentHelper.createComponentTranslation(event.getSender(), "commands.generic.permission", new Object[0]);
			msg.getStyle().setColor(TextFormatting.RED);
			event.getSender().sendMessage(msg);
			return false;
		} else {
			if (context.isConsole() == true) {
				event.setCanceled(false);
				return true;
			}
			if (manager.users.get(event.getSender().getCommandSenderEntity().getUniqueID()).isOp() == true) {
				ConstitutionMain.logger.info("OP Firing Command:");
				event.setCanceled(false);
				return true;
			}
		}
		ConstitutionMain.logger.info("Command Succesfully Fired By: " + context.getPlayer().getDisplayNameString());
		return true;
	}
}
