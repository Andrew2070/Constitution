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
package constitution.permissions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.authlib.GameProfile;

import constitution.ConstitutionMain;
import constitution.configuration.Config;
import constitution.utilities.ServerUtilities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.IPermissionHandler;
import net.minecraftforge.server.permission.context.IContext;
public class PermissionProvider implements IPermissionHandler{

	private static final String PERM_SEED = PermissionManager.DEFAULT_COMMAND_NODE + ".seed";
	private static final String PERM_TELL = PermissionManager.DEFAULT_COMMAND_NODE + ".tell";
	private static final String PERM_HELP = PermissionManager.DEFAULT_COMMAND_NODE + ".help";
	private static final String PERM_ME = PermissionManager.DEFAULT_COMMAND_NODE + ".me";
	protected static final Map<String, DefaultPermissionLevel> permissions = new HashMap<String, DefaultPermissionLevel>();
	protected static final Map<String, String> permissionDescriptions = new HashMap<String, String>();

	@Override
	public void registerNode(String node, DefaultPermissionLevel level, String desc) {
		permissions.put(node, level);
		permissionDescriptions.put(node, desc);
	}
	@Override
	public Collection<String> getRegisteredNodes() {
		List<String>registeredNodes = new ArrayList<String>();
		for (String node : permissions.keySet()) {
			if (!registeredNodes.contains(node)) {
				registeredNodes.add(node);
			}
		}
		return registeredNodes;
	}
	@Override
	public boolean hasPermission(GameProfile profile, String node, IContext context) {
		Boolean permLevel = false;
		PermissionManager manager = ServerUtilities.getManager();
		if (profile.getId() == context.getPlayer().getUniqueID()) {
			User user = manager.users.get(profile.getId());
			if (!user.getPermsContainer().isEmpty() && user.getPermsContainer()!=null) {
				if (searchNodes(user.getPermsContainer(), node)) {
					permLevel = true;
					return true;
				} else {
					if (user.getDominantGroup()!=null && manager.groups.contains(user.getDominantGroup())) {
						Group group = user.getDominantGroup();
						if (searchNodes(group.permsContainer, node)) {
							permLevel = true;
							return true;
						} else {
							for (Group parent : group.parents) {
								if (searchNodes(parent.permsContainer, node)) {
									permLevel = true;
									return true;
								}
							}
						}
					} else {
						if (manager.groups.contains(user.getGroups())) {
							Group group = user.getGroups();
							if (searchNodes(group.permsContainer, node)) {
								permLevel = true;
							} else {
								for (Group parent : group.parents) {
									if (searchNodes(parent.permsContainer, node)) {
										permLevel = true;
										return true;
									}
								}
							}
						}
					}
				}
			}
			//FailSafes For (Non-Authorized) Users with (Non-Problematic) context(s) or exceptions:
			if (PERM_SEED.equals(node) && ! ServerUtilities.getMinecraftServer().isDedicatedServer()) {
				return true;
			}
			if (PERM_TELL.equals(node) || PERM_HELP.equals(node) || PERM_ME.equals(node)) {
				return true;
			}
			if (Config.instance.fullAccessForOPS.get() && ServerUtilities.isOP(profile.getId())) {
				ConstitutionMain.logger.warning(("Operator: " + profile.getName() + " Exempted From Permission Checking!"));
				return true;
			}
		} 
	return permLevel;
	}
	@Override
	public String getNodeDescription(String node) {
		return permissionDescriptions.get(node);
	}
	protected int getOpLevel(GameProfile gameProfile) {
		MinecraftServer server = ServerUtilities.getMinecraftServer();
		if (!server.getPlayerList().canSendCommands(gameProfile)) {
			return 0;
		}
		UserListOpsEntry entry = server.getPlayerList().getOppedPlayers().getEntry(gameProfile);
		return entry != null ? entry.getPermissionLevel() : server.getServer().getOpPermissionLevel();
	}
	
	protected boolean searchNodes(PermissionsContainer container, String permission) {
		Boolean permLevel = false;
		if (container.contains(permission)) {
			permLevel = true;
			return true;
		}
		for (String p : container) {
			if (p.endsWith("*")) {
				if (permission.startsWith(p.substring(0, p.length() - 1))) {
					permLevel = true;
				} else if (p.startsWith("-") && permission.startsWith(p.substring(1, p.length() - 1))) {
					permLevel = false;
				}
			} else {
				if (permission.equals(p)) {
					permLevel = true;
				} else if (p.startsWith("-") && permission.equals(p.substring(1))) {
					permLevel = false;
				}
			}
		}
		return permLevel;
	}
}	
