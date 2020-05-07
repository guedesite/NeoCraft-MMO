package fr.neocraft.main.entity.ai;

import fr.neocraft.main.Server.Zone.Zone;
import fr.neocraft.main.Server.Zone.ZoneManager;
import fr.neocraft.main.entity.EntityMobZone;
import fr.neocraft.main.entity.EntityNeoCreature;
import fr.neocraft.main.util.PosVec3DHelper;
import fr.neocraft.main.util.Vector3d;

public class NeoAIEntityEvasive implements NeoAITask {

	private boolean isOnAttack;
	private int timer = 0;
	private Vector3d pos = new Vector3d(0,0,0);
	public NeoAIEntityEvasive(boolean isOnAttack) {
		this.isOnAttack = isOnAttack;
	}
	
	@Override
	public boolean Try(EntityNeoCreature entity) {
		return entity.wasAttacked();

	}

	@Override
	public void Execute(EntityNeoCreature entity) {
		if(timer  <= 0) {
			timer = entity.getMaxMemory();
			Vector3d vec3 = PosVec3DHelper.findRandomTarget(entity, 30, 7, null);
			if (vec3 != null)
			{
				if(entity instanceof EntityMobZone) {
					Zone z = ZoneManager.getZoneAtPos(vec3.x, vec3.z);
					if(z != null &&  ((EntityMobZone)entity).base.id == z.id ) {
						pos.x = vec3.x;
						pos.y = vec3.y;
						pos.z = vec3.z;
					}
				} else {
					pos.x = vec3.x;
					pos.y = vec3.y;
					pos.z = vec3.z;
				}
				entity.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, 1);
			}
		}
		else {
			entity.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, 1);
			timer--;
		}

	}

}
