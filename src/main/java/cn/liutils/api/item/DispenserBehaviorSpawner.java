/**
 * Code by Lambda Innovation, 2013.
 */
package cn.liutils.api.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import cn.liutils.api.util.MobHelper;


/**
 * The dispenser behavior for a LIMobSpawner. Remember to register it for your MobSpawner! >w>
 * @author WeAthFolD
 */
public class DispenserBehaviorSpawner extends BehaviorDefaultDispenseItem {
    
    public static DispenserBehaviorSpawner INSTANCE = new DispenserBehaviorSpawner();
    
    /**
     * Prevents instanting
     */
    private DispenserBehaviorSpawner() {}
    
    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    @Override
    public ItemStack dispenseStack(IBlockSource blockSource, ItemStack stack)
    {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
        double d0 = blockSource.getX() + enumfacing.getFrontOffsetX();
        double d1 = blockSource.getYInt() + 0.2F;
        double d2 = blockSource.getZ() + enumfacing.getFrontOffsetZ();
        Entity entity = MobHelper.spawnCreature(blockSource.getWorld(), null, ((LIMobSpawner)stack.getItem()).getEntityClass()
                , d0, d1, d2);

        if (entity instanceof EntityLivingBase && stack.hasDisplayName())
        {
            ((EntityLiving)entity).setCustomNameTag(stack.getDisplayName());
        }

        stack.splitStack(1);
        return stack;
    }
    
}
