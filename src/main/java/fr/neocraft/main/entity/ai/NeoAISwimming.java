package fr.neocraft.main.entity.ai;

import fr.neocraft.main.entity.EntityNeoCreature;

public class NeoAISwimming implements NeoAITask {

	@Override
	public boolean Try(EntityNeoCreature entity) {
		return entity.getRNG().nextFloat() < 0.8F & (entity.isInWater() || entity.handleLavaMovement());
		
	}

	@Override
	public void Execute(EntityNeoCreature entity) {
		entity.getJumpHelper().setJumping();
	}

}
