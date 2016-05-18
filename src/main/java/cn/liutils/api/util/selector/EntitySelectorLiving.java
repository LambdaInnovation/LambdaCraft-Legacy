package cn.liutils.api.util.selector;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class EntitySelectorLiving implements IEntitySelector {

    @Override
    public boolean isEntityApplicable(Entity entity) {
        boolean b = IEntitySelector.selectAnything.isEntityApplicable(entity)
                && entity instanceof EntityLivingBase && !entity.isEntityInvulnerable();
        if(entity instanceof EntityPlayer) {
            b &= !((EntityPlayer)entity).capabilities.isCreativeMode;
        }
        return b;
    }

}
