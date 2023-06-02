import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Core {
	requires Common;
	requires java.desktop;
	requires com.badlogic.gdx;
	requires spring.context;
	requires spring.beans;

	opens dk.sdu.mmmi.cbse.main to spring.core;

	uses IGamePluginService;
	uses IEntityProcessingService;
	uses IPostEntityProcessingService;

	exports dk.sdu.mmmi.cbse.beans to spring.beans;
	exports dk.sdu.mmmi.cbse.main;
}