package dk.sdu.mmmi.cbse.beans;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
import org.springframework.stereotype.Service;

@Service
public class EntityProcessingService implements IEntityProcessingService {
	@Override
	public void process(GameData gameData, World world) {
		for (IEntityProcessingService entityProcessingService : SPILocator.locateAll(IEntityProcessingService.class)) {
			entityProcessingService.process(gameData, world);
		}
	}
}
