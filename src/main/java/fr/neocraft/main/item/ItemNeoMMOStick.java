package fr.neocraft.main.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemNeoMMOStick extends Item {

	public ItemNeoMMOStick()
	{
		this.setMaxStackSize(1);
		this.setFull3D();
		this.setCreativeTab(main.neocraft);
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister p_94581_1_)
    {
		
        this.itemIcon = p_94581_1_.registerIcon("stick");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(ItemStack s, int meta)
    {
    	return getIconFromDamageForRenderPass(getIconIndexMMO(s), meta);
    }
    
    
    private int getIconIndexMMO(ItemStack s)
    {
    	if(s.stackTagCompound != null && s.stackTagCompound.hasKey("display"))
    	{
    		return s.stackTagCompound.getCompoundTag("display").getInteger("iconIndex");
    	}
    	return 0;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int nb)
    {
    	if( p_77618_1_ == 0)
    	{
    		return this.itemIcon;
	    
    	} else {
    		return null;
    	}
    }
    
    public int getItemEnchantability()
    {
        return 1;
    }
    @Override
    public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_)
    {

    		/*int j = this.getMaxItemUseDuration(p_77615_1_) - p_77615_4_;
        

            float f = (float)j / 10.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(p_77615_2_, p_77615_3_, f * 2.0F);

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, p_77615_1_);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
            }



            p_77615_2_.playSoundAtEntity(p_77615_3_, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            entityarrow.canBePickedUp = 2;

            if (!p_77615_2_.isRemote)
            {
                p_77615_2_.spawnEntityInWorld(entityarrow);
            } */
    }
    @Override
    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
        return p_77654_1_;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
    	
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
    
        return EnumAction.bow;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
     
        return p_77659_1_;
    }
	
}
