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

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.server.permission.context.ContextKey;
import net.minecraftforge.server.permission.context.ContextKeys;
import net.minecraftforge.server.permission.context.IContext;

public class PermissionContext implements IContext {

	private EntityPlayer player;

	private ICommandSender sender;

	private ICommand command;

	private int dimension;

	private Vec3d sourceLocationStart;

	private Vec3d sourceLocationEnd;

	private Vec3d targetLocationStart;

	private Vec3d targetLocationEnd;

	private Entity sourceEntity;

	private Entity targetEntity;

	private World world;

	public PermissionContext() {
	}

	public PermissionContext(ICommandSender sender) {
		this.sender = sender;
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			this.player = player;
			this.dimension = player.dimension;
			this.sourceLocationStart = new Vec3d(player.posX, player.posY, player.posZ);
		}
	}

	public PermissionContext(ICommandSender sender, ICommand command) {
		this(sender);
		this.command = command;
	}

	public ICommandSender getSender() {
		return sender;
	}

	@Override
	public EntityPlayer getPlayer() {
		return player;
	}

	public ICommand getCommand() {
		return command;
	}

	public int getDimension() {
		return dimension;
	}

	public Vec3d getSourceLocationStart() {
		return sourceLocationStart;
	}

	public Vec3d getSourceLocationEnd() {
		return sourceLocationEnd;
	}

	public Vec3d getTargetLocationStart() {
		return targetLocationStart;
	}

	public Vec3d getTargetLocationEnd() {
		return targetLocationEnd;
	}

	public Entity getSourceEntity() {
		return sourceEntity;
	}

	public Entity getTargetEntity() {
		return targetEntity;
	}

	public PermissionContext setSender(ICommandSender sender) {
		if (sender instanceof EntityPlayer)
			return setPlayer((EntityPlayer) sender);
		this.sender = sender;
		return this;
	}

	public PermissionContext setPlayer(EntityPlayer player) {
		this.sender = this.player = player;
		return this;
	}

	public PermissionContext setCommand(ICommand command) {
		this.command = command;
		return this;
	}

	public PermissionContext setDimension(int dimension) {
		this.dimension = dimension;
		return this;
	}

	public PermissionContext setSourceStart(Vec3d location) {
		this.sourceLocationStart = location;
		return this;
	}

	public PermissionContext setSourceEnd(Vec3d location) {
		this.sourceLocationEnd = location;
		return this;
	}

	public PermissionContext setTargetStart(Vec3d location) {
		this.targetLocationStart = location;
		return this;
	}

	public PermissionContext setTargetEnd(Vec3d location) {
		this.targetLocationEnd = location;
		return this;
	}

	public PermissionContext setSource(Entity entity) {
		this.sourceEntity = entity;
		return this;
	}

	public PermissionContext setTarget(Entity entity) {
		this.targetEntity = entity;
		return this;
	}

	public boolean isConsole() {
		return player == null && (sender == null || sender instanceof MinecraftServer);
	}

	public boolean isPlayer() {
		return player instanceof EntityPlayer;
	}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public <T> T get(ContextKey<T> key) {
		if (key.equals(ContextKeys.TARGET)) {
			return (T) targetEntity;
		}
		if (key.equals(ContextKeys.POS)) {
			return (T) sourceEntity.getPosition();
		}
		String unknownKey = "invalid key";
		return (T) unknownKey;
	}

	@Override
	public boolean has(ContextKey<?> key) {
		if (key.equals(ContextKeys.TARGET)) {
			if (targetEntity!=null) {
				return true;
			}
		}

		if (key.equals(ContextKeys.POS)) {
			if (sourceEntity.getPosition()!=null) {
				return false;
			}
		}
		return false;
	}

}
