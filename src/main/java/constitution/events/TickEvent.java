package constitution.events;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class TickEvent {

	public static final TickEvent instance = new TickEvent();
	
	@SubscribeEvent
	public void tickEvent(ServerTickEvent event) {
		
		
		
	}
}
