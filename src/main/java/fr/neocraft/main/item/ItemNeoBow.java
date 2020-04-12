package fr.neocraft.main.item;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;


public class ItemNeoBow extends ItemBow {


	
	    public ItemNeoBow()
	    {
	        this.maxStackSize = 1;
	        this.setCreativeTab(main.neocraft);
	    }
	    
	    @SideOnly(Side.CLIENT)
	    private IIcon Arrow;
	

	    @SideOnly(Side.CLIENT)
	    @Override
	    public void registerIcons(IIconRegister p_94581_1_)
	    {
	        this.itemIcon = p_94581_1_.registerIcon(Reference.MOD_ID+":bow");
	        this.Arrow = p_94581_1_.registerIcon(Reference.MOD_ID+":Arrow");
	      
	    }
	    
	    @SideOnly(Side.CLIENT)
	    @Override
	    public IIcon getIcon(ItemStack s, int meta)
	    {
	    	return s.getItemUseAction() == EnumAction.bow ? getIconFromDamageForRenderPass(s.getItemDamage(), meta) : this.itemIcon;
	    }
	    
	    @Override
	    @SideOnly(Side.CLIENT)
	    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int nb)
	    {
	        return nb > 0 ? this.Arrow : this.itemIcon ;
	    }
	    
	    public int getItemEnchantability()
	    {
	        return 1;
	    }
	    @Override
	    public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_)
	    {

	    	
	        int j = this.getMaxItemUseDuration(p_77615_1_) - p_77615_4_;


	            float f = (float)j / 20.0F;
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
	            }
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
	    
	    
	    @SideOnly(Side.CLIENT)
	    @Override
	    public boolean requiresMultipleRenderPasses()
	    {
	        return Minecraft.getMinecraft().thePlayer.getItemInUseCount() > 0;
	    }
	    @SideOnly(Side.CLIENT)
	    @Override
	    public int getRenderPasses(int metadata)
	    {
	        return 2;
	    }
	 

}
