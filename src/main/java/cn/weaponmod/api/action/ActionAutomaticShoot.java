package cn.weaponmod.api.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InfWeapon;
import cn.weaponmod.api.weapon.WeaponGenericBase;

/**
 * 自动射击动作，底层实现依赖ActionShoot。
 * @author WeathFolD
 */
public class ActionAutomaticShoot extends Action {

    int rate;
    public  ActionShoot shooter;
    
    public ActionAutomaticShoot copy() {
        return new ActionAutomaticShoot(shooter, rate, this.maxTick);
    }
    
    public ActionAutomaticShoot(int ticks, int shootRate, int damage, String snd) {
        super(ticks, "shoot_auto");
        rate = shootRate;
        shooter = new ActionShoot(damage, snd).setShootRate(shootRate - 1);
    }
    
    public ActionAutomaticShoot(int ticks, int shootRate, int damage, float scatter, String snd) {
        super(ticks, "shoot_auto");
        rate = shootRate;
        shooter = new ActionShoot(damage, scatter, snd).setShootRate(shootRate - 1);
    }
    
    public ActionAutomaticShoot(ActionShoot act, int shootRate, int ticks) {
        super(ticks, "shoot_auto");
        shooter = act;
        rate = shootRate;
        shooter.setShootRate(shootRate - 1);
    }
    
    public ActionAutomaticShoot setConsume(boolean does, int amt) {
        shooter.setConsume(does, amt);
        return this;
    }
    
    public ActionAutomaticShoot setLeft(boolean b ) {
        shooter.setLeft(b);
        return this;
    }
    
    public ActionAutomaticShoot setShooter(ActionShoot s) {
        shooter = s;
        return this;
    }
    
    public int getShootRate() {
        return rate;
    }
    
    public int getDamage() {
        return shooter.damage;
    }
    
    public String getSound() {
        return shooter.sound;
    }

    @Override
    public boolean onActionBegin(World world, EntityPlayer player, InfWeapon information) {
        information.executeAction(player, shooter);
        return true;
    }

    @Override
    public boolean onActionEnd(World world, EntityPlayer player, InfWeapon information) {
        return false;
    }
    
    @Override
    public boolean onActionTick(World world, EntityPlayer player, InfWeapon inf) {
        int te = maxTick - inf.getTickLeft(this);
        if(te % rate == 0) {
            inf.executeAction(player, shooter);
            if(!stackFine(player.getCurrentEquippedItem(), player)) {
                return false;
            }
        }
        return true;
    }
    
    protected boolean stackFine(ItemStack stack, EntityPlayer player) {
        if(stack.getItem() instanceof WeaponGenericBase) {
            
            WeaponGenericBase wpn = (WeaponGenericBase) stack.getItem();
            return player.capabilities.isCreativeMode || 
                    (stack.getMaxDamage() == 0 ? WeaponHelper.hasAmmo(wpn, player) : wpn.getAmmo(stack) > 0);
        
        }
        return true;
    }
    
    public ActionAutomaticShoot setMuzOffset(double x, double y, double z) {
        this.shooter.setMuzzleOffset(x, y, z);
        return this;
    }
    
    public ActionAutomaticShoot setMuzScale(float f) {
        shooter.setMuzzleScale(f);
        return this;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean doesConcurrent(Action other) {
        return true;
    }

    @Override
    public int getRenderPriority() {
        return -1; //ActionShoot's Job
    }
    
    public ActionAutomaticShoot setMuzzleflash(ResourceLocation... r) {
        shooter.setMuzzle(r);
        return this;
    }

}
