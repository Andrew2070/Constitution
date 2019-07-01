package Constitution.Utilities;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class VanillaUtilities {

	public static MinecraftServer getMinecraftServer() {
	return FMLCommonHandler.instance().getMinecraftServerInstance();
	}
}
