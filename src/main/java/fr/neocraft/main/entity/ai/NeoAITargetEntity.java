package fr.neocraft.main.entity.ai;

import java.util.Collections;
import java.util.List;

import fr.neocraft.main.Init.EntityMod;
import fr.neocraft.main.entity.EntityNeoCreature;
import fr.neocraft.main.util.Vector3d;
import net.minecraft.entity.EntityLivingBase;

public class NeoAITargetEntity implements NeoAITask {

	private Class[] Target;
	private int Timer = 0;
	private Vector3d Pos = new Vector3d(0,0,0);
	
	public NeoAITargetEntity(Class[] c) {
		Target = c;
	}
	
	@Override
	public boolean Try(EntityNeoCreature entity) {
		boolean flag = false;
		if(entity.hasAttackTarget() && !entity.isDead)
		{
			if(Timer >0)
			{
				Timer--;
				flag = true;
				Pos.x = entity.getAttackTarget().posX;
				Pos.y = entity.getAttackTarget().posY;
				Pos.z = entity.getAttackTarget().posZ;
			} else {
				for(int i = 0; Target.length > i | !flag; i++ ) {
					List<EntityLivingBase> list = entity.worldObj.getEntitiesWithinAABB(Target[i], entity.boundingBox.expand(entity.getSeeRange(),entity.getSeeRange()/2,entity.getSeeRange()));
					Collections.sort(list, EntityMod.prepareSorter(entity));
					if(!list.isEmpty())
					{
						EntityLivingBase en = list.get(0);
						Pos.x = en.posX;
						Pos.y = en.posY;
						Pos.z = en.posZ;
						entity.setAttackTarget(en);;
						flag = true;
					}
				}
			}
		}else if(entity.wasAttacked() && !entity.getEntityMemory().isDead){
			flag = true;
			Pos.x = entity.getEntityMemory().posX;
			Pos.y = entity.getEntityMemory().posY;
			Pos.z = entity.getEntityMemory().posZ;
		}
		return flag;

	}

	@Override
	public void Execute(EntityNeoCreature entity) {
		entity.getMoveHelper().setMoveTo(Pos.x,Pos.y, Pos.z, 1);

	}

}
