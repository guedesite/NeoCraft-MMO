package fr.neocraft.main.entity.ai;

import fr.neocraft.main.Server.Zone.Zone;
import fr.neocraft.main.Server.Zone.ZoneManager;
import fr.neocraft.main.entity.EntityMobZone;
import fr.neocraft.main.entity.EntityNeoCreature;
import fr.neocraft.main.util.PosVec3DHelper;
import fr.neocraft.main.util.Vector3d;
import fr.neocraft.main.util.Vector4d;
import net.minecraft.util.MathHelper;

public class NeoAIWander implements NeoAITask{

	private Vector4d pos = new Vector4d(0,0,0,-1);
	
	@Override
	public boolean Try(EntityNeoCreature entity) {
		if(pos.custom == 0) {
			return true;
		}
		else if(entity.getRNG().nextInt(30) != 0) {
			return false;
		}
		else if(entity instanceof EntityMobZone)
		{
			if(!((EntityMobZone)entity).canWander) {
				return false;
			}
			Vector3d vec3 = PosVec3DHelper.findRandomTarget(entity, 30, 7, null);
			if (vec3 != null)
			{
				Zone z = ZoneManager.getZoneAtPos(vec3.x, vec3.z);
				if(z != null &&  ((EntityMobZone)entity).base.id == z.id ) {
					pos.x = vec3.x;
					pos.y = vec3.y;
					pos.z = vec3.z;
					pos.custom = 0;
					return true;
				}
			}
		} else {
			Vector3d vec3 = PosVec3DHelper.findRandomTarget(entity, 30, 7, null);
			if (vec3 != null)
			{
				Zone z = ZoneManager.getZoneAtPos(vec3.x, vec3.z);
				if(z != null &&  ((EntityMobZone)entity).base.id == z.id ) {
					pos.x = vec3.x;
					pos.y = vec3.y;
					pos.z = vec3.z;
					pos.custom = 0;
					return true;
				}
			}
		}
		return false;
		
	}

	@Override
	public void Execute(EntityNeoCreature entity) {
		entity.getMoveHelper().setMoveTo(pos.x,pos.y, pos.z, 1);
		if( entity.getNavigator().noPath() ||( MathHelper.floor_double(pos.x) ==  MathHelper.floor_double(entity.posX) || MathHelper.floor_double(pos.y) ==  MathHelper.floor_double(entity.posY) || MathHelper.floor_double(pos.z) ==  MathHelper.floor_double(entity.posZ)))
		{
			pos.custom = -1;
		}
	}

}
