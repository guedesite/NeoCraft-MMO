package fr.neocraft.main.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import fr.neocraft.main.Init.BlockMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;


public class NeoBlockIce extends Block {

	@SideOnly(Side.CLIENT)
	public IIcon[] icon;
	
	
	 public NeoBlockIce()
	   {
	        super(Material.ice);
	        this.slipperiness = 0.98F;
	        this.setTickRandomly(true);
	        this.setCreativeTab(main.neocraftdeco);
	   }
	 
	 @Override
	 public int damageDropped(int meta) {
	     return meta;
	 }
	 
	    @SideOnly(Side.CLIENT)
	    public int getRenderBlockPass()
	    {
	        return 0;
	    }
	 
	 @SideOnly(Side.CLIENT)
	    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
	    {
	        Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);

	        if (this == BlockMod.BlockIce)
	        {
	            if (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) != p_149646_1_.getBlockMetadata(p_149646_2_ - Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - Facing.offsetsZForSide[p_149646_5_]))
	            {
	                return true;
	            }

	            if (block == this)
	            {
	                return false;
	            }
	        }

	        return !false && block == this ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
	    }
	 
	 	@SideOnly(Side.CLIENT)
	    public IIcon getIcon(int s, int m)
	    {
	 		
	 		if(m > 3)
	 		{
	 			return this.icon[3];
	 		}
	 		return this.icon[m];
	    }

	 	@SideOnly(Side.CLIENT)
	    public void registerBlockIcons(IIconRegister p_149651_1_)
	    {
	 		icon = new IIcon[4];
	 		icon[0] = p_149651_1_.registerIcon(Reference.MOD_ID+":frosted_ice_0");
	 		icon[1] = p_149651_1_.registerIcon(Reference.MOD_ID+":frosted_ice_1");
	 		icon[2] = p_149651_1_.registerIcon(Reference.MOD_ID+":frosted_ice_2");
	 		icon[3] = p_149651_1_.registerIcon(Reference.MOD_ID+":frosted_ice_3");
	    }
	 
	 @SideOnly(Side.CLIENT)
	    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
	    {
		  	p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
	        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
	        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
	        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
	    }




	    /**
	     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
	     * block and l is the block's subtype/damage.
	     */
	    public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
	    {
	        p_149636_2_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
	        p_149636_2_.addExhaustion(0.025F);

	        if (this.canSilkHarvest(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_) && EnchantmentHelper.getSilkTouchModifier(p_149636_2_))
	        {
	            ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	            ItemStack itemstack = this.createStackedBlock(p_149636_6_);

	            if (itemstack != null) items.add(itemstack);

	            ForgeEventFactory.fireBlockHarvesting(items, p_149636_1_, this, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, 0, 1.0f, true, p_149636_2_);
	            for (ItemStack is : items)
	                this.dropBlockAsItem(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, is);
	        }
	        else
	        {
	            if (p_149636_1_.provider.isHellWorld)
	            {
	                p_149636_1_.setBlockToAir(p_149636_3_, p_149636_4_, p_149636_5_);
	                return;
	            }

	            int i1 = EnchantmentHelper.getFortuneModifier(p_149636_2_);
	            harvesters.set(p_149636_2_);
	            this.dropBlockAsItem(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, i1);
	            harvesters.set(null);
	            Material material = p_149636_1_.getBlock(p_149636_3_, p_149636_4_ - 1, p_149636_5_).getMaterial();

	            if (material.blocksMovement() || material.isLiquid())
	            {
	                p_149636_1_.setBlock(p_149636_3_, p_149636_4_, p_149636_5_, Blocks.flowing_water);
	            }
	        }
	    }

	    /**
	     * Returns the quantity of items to drop on block destruction.
	     */
	    public int quantityDropped(Random p_149745_1_)
	    {
	        return 0;
	    }

	    /**
	     * Ticks the block if it's been scheduled
	     */
	    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
	    {
	        if (p_149674_1_.getSavedLightValue(EnumSkyBlock.Block, p_149674_2_, p_149674_3_, p_149674_4_) > 11 - this.getLightOpacity())
	        {
	            if (p_149674_1_.provider.isHellWorld)
	            {
	                p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
	                return;
	            }

	            this.dropBlockAsItem(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), 0);
	            p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.water);
	        }
	    }

	    /**
	     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
	     * and stop pistons
	     */
	    public int getMobilityFlag()
	    {
	        return 0;
	    }
}
