package fr.neocraft.main.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemNeoMMOSword extends Item{
	
	public ItemNeoMMOSword()
	{
		this.setMaxStackSize(1);
		this.setFull3D();
		this.setCreativeTab(main.neocraft);
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister p_94581_1_)
    {
        this.itemIcon = p_94581_1_.registerIcon("iron_sword");
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
    
  
  
}
