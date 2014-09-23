package cn.lambdacraft.mob.item;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.item.CBCGenericItem;
import cn.lambdacraft.mob.entity.EntitySnark;
import cn.lambdacraft.mob.util.MobHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LCMobSpawner extends CBCGenericItem {

	protected Class<? extends Entity> entClass = EntitySnark.class;
	
	public LCMobSpawner() {
		super();
		this.setCreativeTab(CBCMod.cct);
		setIAndU("snark");
	}
	
	public LCMobSpawner(Class<? extends Entity> entityClass, String name) {
		super();
		setCreativeTab(CBCMod.cct);
		entClass = entityClass;
		setIAndU(name);
	}

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer par3EntityPlayer)
    {
    	if(!world.isRemote) {
			MobHelper.spawnCreature(world, entClass, par3EntityPlayer, true);
			if (!(par3EntityPlayer.capabilities.isCreativeMode)) {
				par1ItemStack.stackSize--;
			}
		}
        return par1ItemStack;
    }
    
    public Class<? extends Entity> getEntityClass() {
    	return entClass;
    }
	
	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 10;
	}
}
