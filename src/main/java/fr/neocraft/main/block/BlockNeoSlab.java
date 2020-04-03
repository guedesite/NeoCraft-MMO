package fr.neocraft.main.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.main;
import fr.neocraft.main.Init.BlockMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockNeoSlab extends Block
{
	
		private Block n;
		private int meta;

	    public BlockNeoSlab(Block b, int m)
	    {
	        super(Material.wood);
	        this.setCreativeTab(main.neocraftdecoSlab);
	        n=b;
	        meta=m;
	        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	        this.setBlockName(b.getUnlocalizedName().substring(5)+"_slab"+m);
	        this.setLightOpacity(255);
	    }


	    public boolean renderAsNormalBlock()
	    {
	        return false;
	    }

	    
	    public boolean isOpaqueCube()
	    {
	        return false;
	    }

	    @SideOnly(Side.CLIENT)
	    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
	    {
	    	int m = p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_);
	    	if (p_149646_5_ != 1 && p_149646_5_ != 0 )
	        {
	            return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
	        }
	        else if(p_149646_5_ == 0 && m == 0)
	        {
	        	return !p_149646_1_.getBlock(p_149646_2_, p_149646_3_-1, p_149646_4_).isSideSolid(p_149646_1_, p_149646_2_-1, p_149646_3_, p_149646_4_, ForgeDirection.DOWN);
	        
	        } else if(p_149646_5_ == 1 && m == 1)
	        {
	        	return !p_149646_1_.getBlock(p_149646_2_, p_149646_3_+1, p_149646_4_).isSideSolid(p_149646_1_, p_149646_2_+1, p_149646_3_, p_149646_4_, ForgeDirection.UP);
	        }
	        else {
	        	return true;
	        }
	    }
	    
	    
	    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
	    {

	            boolean flag = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) == 0;

	            if (flag)
	            {
	                this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
	            }
	            else
	            {
	                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	            }
	        }


	    /**
	     * Sets the block's bounds for rendering it as an item
	     */
	    public void setBlockBoundsForItemRender()
	    {
	          this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	    }

	    /**
	     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	     */
	    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
	    {
	        this.setBlockBoundsBasedOnState(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);
	        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
	    }

	    @SideOnly(Side.CLIENT)
	    public void registerBlockIcons(IIconRegister p_149651_1_) {}

	    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	    {
	        return Item.getItemFromBlock(this);
	    }
	    
	    public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
	    {
	        return  (p_149660_5_ != 0 && (p_149660_5_ == 1 || (double)p_149660_7_ <= 0.5D) ? 0 : 1);
	    }
	    public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_)
	    {
	        this.n.onBlockClicked(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
	    }

	    /**
	     * A randomly called display update to be able to add particles or other items for display
	     */
	    @SideOnly(Side.CLIENT)
	    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
	    {
	        this.n.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
	    }

	    /**
	     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
	     */
	    public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_)
	    {
	        this.n.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
	    }

	    /**
	     * Returns how much this block can resist explosions from the passed in entity.
	     */
	    @Override
	    public int damageDropped(int meta) {
	        return meta;
	    }
	    
	    public float getExplosionResistance(Entity p_149638_1_)
	    {
	        return this.n.getExplosionResistance(p_149638_1_);
	    }

	    /**
	     * How many world ticks before ticking
	     */
	    public int tickRate(World p_149738_1_)
	    {
	        return this.n.tickRate(p_149738_1_);
	    }

	    /**
	     * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
	     */
	    public void velocityToAddToEntity(World p_149640_1_, int p_149640_2_, int p_149640_3_, int p_149640_4_, Entity p_149640_5_, Vec3 p_149640_6_)
	    {
	        this.n.velocityToAddToEntity(p_149640_1_, p_149640_2_, p_149640_3_, p_149640_4_, p_149640_5_, p_149640_6_);
	    }

	    /**
	     * How bright to render this block based on the light its receiving. Args: iBlockAccess, x, y, z
	     */
	    @SideOnly(Side.CLIENT)
	    public int getMixedBrightnessForBlock(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_)
	    {
	        return this.n.getMixedBrightnessForBlock(p_149677_1_, p_149677_2_, p_149677_3_, p_149677_4_);
	    }

	    /**
	     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	     */
	    @SideOnly(Side.CLIENT)
	    public int getRenderBlockPass()
	    {
	        return this.n.getRenderBlockPass();
	    }

	    /**
	     * Gets the block's texture. Args: side, meta
	     */
	    @SideOnly(Side.CLIENT)
	    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	    {
	        return this.n.getIcon(p_149691_1_, this.meta);
	    }

	    /**
	     * Returns the bounding box of the wired rectangular prism to render.
	     */
	    @SideOnly(Side.CLIENT)
	    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
	    {
	        return this.n.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
	    }

	    /**
	     * Returns if this block is collidable (only used by Fire). Args: x, y, z
	     */
	    public boolean isCollidable()
	    {
	        return this.n.isCollidable();
	    }

	    /**
	     * Returns whether this block is collideable based on the arguments passed in 
	     * @param par1 block metaData 
	     * @param par2 whether the player right-clicked while holding a boat
	     */
	    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
	    {
	        return this.n.canCollideCheck(p_149678_1_, p_149678_2_);
	    }

	    /**
	     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	     */
	    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
	    {
	        return this.n.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
	    }

	    /**
	     * Called whenever the block is added into the world. Args: world, x, y, z
	     */
	    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
	    {
	        this.onNeighborBlockChange(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, Blocks.air);
	        this.n.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
	    }

	    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
	    {
	        this.n.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
	    }

	    /**
	     * Called whenever an entity is walking on top of this block. Args: world, x, y, z, entity
	     */
	    public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity p_149724_5_)
	    {
	        this.n.onEntityWalking(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_, p_149724_5_);
	    }

	    /**
	     * Ticks the block if it's been scheduled
	     */
	    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
	    {
	        this.n.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
	    }

	    /**
	     * Called upon block activation (right click on the block.)
	     */
	    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	    {
	        return this.n.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, 0, 0.0F, 0.0F, 0.0F);
	    }

	    /**
	     * Called upon the block being destroyed by an explosion
	     */
	    public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_)
	    {
	        this.n.onBlockDestroyedByExplosion(p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_, p_149723_5_);
	    }

	    public MapColor getMapColor(int p_149728_1_)
	    {
	        return this.n.getMapColor(this.meta);
	    }
}