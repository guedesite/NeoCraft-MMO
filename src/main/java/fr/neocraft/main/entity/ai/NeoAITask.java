package fr.neocraft.main.entity.ai;

import fr.neocraft.main.entity.EntityNeoCreature;

public interface NeoAITask {

	
	public boolean Try(EntityNeoCreature entity);
	public void Execute(EntityNeoCreature entity);

	
}
