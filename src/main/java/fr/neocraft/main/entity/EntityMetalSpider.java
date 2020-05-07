package fr.neocraft.main.entity;

import fr.neocraft.main.Server.Zone.Zone;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityMetalSpider extends EntityMobZone{

	public EntityMetalSpider(World p_i1745_1_, Zone z, double baseX, double baseY,
			double baseZ) {
		super(p_i1745_1_, true, true, 3, z, baseX, baseY, baseZ);
		this.setSize(3.7F, 1.7F);
	}

	
	public EntityMetalSpider(World p_i1745_1_) {
		super(p_i1745_1_);
		this.setSize(3.2F, 1.5F);
	 
	    this.isMob  =true;
	}
	
	@Override
	public void applyEntityAttributes()
    {
        super.applyEntityAttributes();
       // this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.13000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1);
    }
	

	@Override
	public float getEyeHeight()
    {
        return 0.85F;
    }
}
