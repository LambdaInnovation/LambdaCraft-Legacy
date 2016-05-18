package cn.lambdacraft.mob.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityBabyHeadcrab extends EntityHeadcrab {

    public EntityBabyHeadcrab(World par1World) {
        super(par1World);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        double i = this.getAttackDamage();
        if (this.onGround || this.hurtResistantTime > 0)
            return false;
        boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)i);
        this.motionX = 0.0;
        this.motionZ = 0.0;
        // Attaching on
        if (par1Entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) par1Entity;
            ItemStack armorStack = player.inventory.armorInventory[3];
            if (armorStack == null) {
                if(player.getHealth() <= 10.0F) {
                    attacher = player;
                    this.setPositionAndRotation(attacher.posX, attacher.posY + 0.05, attacher.posZ,
                        attacher.rotationYaw, attacher.rotationPitch);
                }
            } else {
                armorStack.damageItem(2, this);
            }
        } else if (par1Entity instanceof EntityVillager) {
            attacher = (EntityLivingBase) par1Entity;
            this.setPositionAndRotation(attacher.posX, attacher.posY
                + attacher.height + 0.05, attacher.posZ,
                attacher.rotationYaw, attacher.rotationPitch);
        }
        return flag;
    }

    @Override
    public Item getDropItem() {
        // 小头蟹就算了吧-w-
        return null;
    }
    
}
