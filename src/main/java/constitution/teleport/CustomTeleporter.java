package constitution.teleport;
import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * Used for Teleporting A Player To Another Dimension.
 */
public class CustomTeleporter extends Teleporter {

	public CustomTeleporter(WorldServer worldserver) {
		super(worldserver);
	}

	// Override Default BS
	@Override
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
		return false;
	}

	@Override
	public void removeStalePortalLocations(long par1) {
		// Doesn't need to create a portal
	}

	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		// Doesn't need to create a portal
	}

}
