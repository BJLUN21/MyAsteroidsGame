package dk.sdu.mmmi.cbse.beans;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
import org.springframework.stereotype.Service;

@Service
public class PostEntityProcessingService implements IPostEntityProcessingService {
	@Override
	public void process(GameData gameData, World world) {
		for (IPostEntityProcessingService postEntityProcessingService : SPILocator.locateAll(IPostEntityProcessingService.class)) {
			postEntityProcessingService.process(gameData, world);
		}
	}
}
