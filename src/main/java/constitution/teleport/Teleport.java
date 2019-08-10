package constitution.teleport;

import constitution.permissions.User;
import constitution.utilities.ServerUtilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

/**
 * Retains the needed information for the action of teleporting a player to a
 * certain position.
 */
public class Teleport {
	private int dbID;
	private String key;
	private int dim;
	private String name;
	private float x, y, z, yaw, pitch;

	public Teleport(String name, int dim, float x, float y, float z, float yaw, float pitch) {
		setName(name);
		setDim(dim);
		setPosition(x, y, z);
		setRotation(yaw, pitch);
	}

	public Teleport(String name, int dim, float x, float y, float z) {
		this(name, dim, x, y, z, 0, 0);
	}

	// Used when a player is riding an entity. eg pig, horse
	public void teleport(EntityPlayer player, boolean canRide) {
		if (player.dimension != dim) {
			ServerUtilities.getMinecraftServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP) player, dim,
					new CustomTeleporter(ServerUtilities.getMinecraftServer().getWorld(dim)));
		}
		if (player.isRiding() && player.getRidingEntity() != null && player.getRidingEntity().isEntityAlive() && canRide) {
			player.getRidingEntity().setPosition(x, y, z);
			player.getRidingEntity().setPositionAndRotation(x, y, z, yaw, pitch);
		}
		player.setPositionAndUpdate(x, y, z);
		player.setPositionAndRotation(x, y, z, yaw, pitch);
	}
	
	public void teleport(User user) {
		EntityPlayerMP player =	ServerUtilities.getMinecraftServer().getPlayerList().getPlayerByUUID(user.getUUID());
		teleport(player, false);
	}

	public void teleport(EntityPlayer player) {
		teleport(player, false);
	}
	
	public Teleport setDim(int dim) {
		this.dim = dim;
		return this;
	}
	

	public Teleport setPosition(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Teleport setRotation(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
		return this;
	}
	public Teleport setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public int getDim() {
		return dim;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

}
