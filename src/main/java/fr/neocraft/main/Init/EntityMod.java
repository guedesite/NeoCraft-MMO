package fr.neocraft.main.Init;

import java.util.Comparator;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.main;
import fr.neocraft.main.entity.EntityMetalSpider;
import fr.neocraft.main.render.Entity.Mob.RenderMetalSpider;
import net.minecraft.entity.Entity;

public class EntityMod {

	public static void init() {
		EntityRegistry.registerGlobalEntityID(EntityMetalSpider.class, "MMOMetalSpider", EntityRegistry.findGlobalUniqueEntityId(), 0XFFFFFF, 0XFFFFFF);
        EntityRegistry.registerModEntity(EntityMetalSpider.class, "MMOMetalSpider", 420, main.instance, 40, 1, true);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initRender() {
		RenderingRegistry.registerEntityRenderingHandler(EntityMetalSpider.class, new RenderMetalSpider());
	}
	
	
	private static EntityMod.Sorter sorter = new EntityMod.Sorter();
	
	public static EntityMod.Sorter prepareSorter(Entity entity) {
		sorter.theEntity = entity;
		return sorter;
	}
	
	public static class Sorter implements Comparator
    {
        public Entity theEntity;


        public int compare(Entity p_compare_1_, Entity p_compare_2_)
        {
            double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
            double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
            return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
        }

        public int compare(Object p_compare_1_, Object p_compare_2_)
        {
            return this.compare((Entity)p_compare_1_, (Entity)p_compare_2_);
        }
    }
}
