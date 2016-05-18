/**
 * 
 */
package cn.weaponmod.api.weapon;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cn.liutils.api.util.GenericUtils;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import cn.weaponmod.api.action.ActionReload;
import cn.weaponmod.api.action.ActionShoot;
import cn.weaponmod.api.information.InfWeapon;
import cn.weaponmod.core.client.UpliftHandler;
import cn.weaponmod.core.proxy.WMClientProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Create a dual-weapon weapon instance of a weapon. That is, the
 * action of weapon left-click(usually shoot) will be handled seperately for left and right click.
 * The model will be rendered at both left and right, both in firstperson and thirdperson.
 * @author WeathFolD
 *
 */
public class WeaponDualWield extends WeaponGeneric {

    private WeaponGeneric template;
    public static WMInformation fakePool = new WMInformation();
    
    public static UpliftHandler fakeUpl = new UpliftHandler();
    
    private Action leftShoot, rightShoot, leftReload, rightReload;
    
    private class DualShoot extends ActionShoot {
        
        public DualShoot(ActionShoot s, boolean b) {
            super(0, 0, null);
            this.copyFrom(s);
            this.setLeft(b);
        }
        
        @Override
        protected boolean consumeAmmo(EntityPlayer player, ItemStack stack, int amount) {
            //Handles only NBT-ammoized condition
            if(left) {
                int num = getAmmoLeft(stack);
                num -= amount;
                if(num < 0) {
                    return false;
                }
                setAmmoLeft(stack, num);
                return true;
            } else {
                int num = getAmmoRight(stack);
                num -= amount;
                if(num < 0) {
                    return false;
                }
                setAmmoRight(stack, num);
                return true;
            }
        }
        
    }
    
    private class DualReload extends ActionReload {
        boolean left;

        public DualReload(ActionReload reload, boolean left) {
            super(0, null, null);
            this.maxTick = reload.maxTick;
            this.sound = reload.sound;
            this.soundFinish = reload.soundFinish;
            this.left = left;
        }

        @Override
        public boolean onActionEnd(World world, EntityPlayer player, InfWeapon inf) {
            ItemStack stack = player.getCurrentEquippedItem();
            if(stack == null || stack.getItem() != WeaponDualWield.this)
                return false;
            int amt = getMaxDamage() - getAmmo(stack, left);
            amt -= WeaponHelper.consumeAmmo(player, WeaponDualWield.this, amt);
            setAmmo(stack, left, amt + getAmmo(stack, left));
            player.playSound(soundFinish, 0.5F, 1.0F);
            return true;
        }
        
        @Override
        protected boolean canReload(EntityPlayer player, ItemStack stack) {
            int ammo = getAmmo(stack, left);
            return ammo < stack.getMaxDamage() && WeaponHelper.getAmmoCapacity(WeaponDualWield.this, player.inventory) > 0;
        }
        
    }

    public WeaponDualWield(WeaponGeneric template) {
        super(template.ammoItem);
        this.template = template;
        this.actionShoot = template.actionShoot;
        this.leftReload = new DualReload((ActionReload) template.actionReload, true);
        this.rightReload = new DualReload((ActionReload) template.actionReload, false);
        this.actionUplift = template.actionUplift;
        this.actionJam = template.actionJam;
        this.setMaxDamage(template.getMaxDamage());
        if(actionShoot instanceof ActionShoot) {
            leftShoot = new DualShoot((ActionShoot) actionShoot, true);
            rightShoot = new DualShoot((ActionShoot) actionShoot, false);
        } else if(actionShoot instanceof ActionAutomaticShoot) {
            leftShoot = ((ActionAutomaticShoot)actionShoot).copy();
            ((ActionAutomaticShoot)leftShoot).shooter = (new DualShoot(((ActionAutomaticShoot)actionShoot).shooter, true));
            rightShoot = ((ActionAutomaticShoot)actionShoot).copy();
            ((ActionAutomaticShoot)rightShoot).shooter = (new DualShoot(((ActionAutomaticShoot)actionShoot).shooter, false));
        } else { throw new RuntimeException(); }
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world,
            Entity entity, int par4, boolean par5) {
        if(super.onWpnUpdate(stack, world, entity, par4, par5) != null) 
            fakePool.getInformation((EntityPlayer) entity).updateTick();
    }

    @Override
    public synchronized void onItemClick(World world, EntityPlayer player, ItemStack stack,
            int keyid) {
        //WMClientProxy.switchUpl(fakeUpl);
        if(keyid == 1) {
            WMInformation.switchPool(fakePool);
            if(world.isRemote)
                WMClientProxy.switchUpl(fakeUpl);
            actionShoot = rightShoot;
        } else if(keyid == 0) {
            actionShoot = leftShoot;
        }
        switch(keyid) {
        case 1:
        case 0:
            super.onItemClick(world, player, stack, 0);
            break;
        case 2:
            WMInformation.instance.getInformation(player).executeAction(leftReload);
            fakePool.getInformation(player).executeAction(rightReload);
            break;
        default:
        }
        //WMClientProxy.restoreUpl();
        if(keyid == 1) {
            WMInformation.restorePool();
            if(world.isRemote)
                WMClientProxy.restoreUpl();
        } else if(keyid == 0) {}
    }

    @Override
    public void onItemRelease(World world, EntityPlayer pl, ItemStack stack,
            int keyid) {
        switch(keyid) {
        case 1:
            WMInformation.switchPool(fakePool);
        case 0:
            super.onItemRelease(world, pl, stack, 0);
            break;
        case 2:
            super.onItemRelease(world, pl, stack, 2);
            break;
        default:
        }
        if(keyid == 1)
            WMInformation.restorePool();
    }
    
    @Override
    public boolean canShoot(EntityPlayer player, ItemStack is) {
        if(getMaxDamage() == 0)
            return WeaponHelper.hasAmmo(this, player) || player.capabilities.isCreativeMode;
        return (isLeft() ? this.getAmmoLeft(is) > 0 : this.getAmmoRight(is) > 0) || player.capabilities.isCreativeMode;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs ct, List list)
    {
        ItemStack is = new ItemStack(item);
        setAmmoLeft(is, this.getMaxDamage());
        setAmmoRight(is, this.getMaxDamage());
        list.add(is);
    }

    @Override
    public void onItemUsingTick(World world, EntityPlayer player,
            ItemStack stack, int keyid, int ticks) {}
    
    public int getAmmo(ItemStack is, boolean lr) {
        return lr ? getAmmoLeft(is) : getAmmoRight(is);
    }
    
    public void setAmmo(ItemStack is, boolean lr, int i) {
        if(lr) setAmmoLeft(is, i);
        else setAmmoRight(is, i);
    }
    
    public int getAmmoLeft(ItemStack is) {
        return GenericUtils.loadCompound(is).getInteger("ammo_l");
    }
    
    public void setAmmoLeft(ItemStack is, int i) {
        GenericUtils.loadCompound(is).setInteger("ammo_l", i);
    }
    
    public int getAmmoRight(ItemStack is) {
        return GenericUtils.loadCompound(is).getInteger("ammo_r");
    }
    
    public void setAmmoRight(ItemStack is, int i) {
        GenericUtils.loadCompound(is).setInteger("ammo_r", i);
    }
    
    private boolean isLeft() {
        if(actionShoot instanceof ActionShoot) {
            return ((ActionShoot)actionShoot).left;
        } else return((ActionAutomaticShoot)actionShoot).shooter.left;
    }

}
