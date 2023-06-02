package dk.sdu.mmmi.cbse.beans;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
import org.springframework.stereotype.Service;

@Service
public class GamePluginService implements IGamePluginService {
	@Override
	public void start(GameData gameData, World world) {
		for (IGamePluginService gamePluginService : SPILocator.locateAll(IGamePluginService.class)) {
			gamePluginService.start(gameData, world);
		}
	}

	@Override
	public void stop(GameData gameData, World world) {
		for (IGamePluginService gamePluginService : SPILocator.locateAll(IGamePluginService.class)) {
			gamePluginService.stop(gameData, world);
		}
	}
}
