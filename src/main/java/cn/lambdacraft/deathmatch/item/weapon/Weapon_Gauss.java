package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.ISpecialCrosshair;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entity.EntityBulletGauss;
import cn.lambdacraft.deathmatch.entity.EntityBulletGaussSec;
import cn.lambdacraft.deathmatch.entity.EntityBulletGaussSec.EnumGaussRayType;
import cn.liutils.api.util.GenericUtils;
import cn.liutils.api.util.Motion3D;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionJam;
import cn.weaponmod.api.action.ActionShoot;
import cn.weaponmod.api.information.InfWeapon;

/**
 * 喜闻乐见的Gauss！有穿墙和击飞效果喔~
 * @author WeAthFolD
 *
 */
public class Weapon_Gauss extends WeaponGenericLC implements ISpecialCrosshair {

    
    public static final String 
        SND_CHARGE_PATH = "lambdacraft:weapons.gauss_charge",
        SND_CHARGEA_PATH[] = { 
            "lambdacraft:weapons.gauss_windupa",
            "lambdacraft:weapons.gauss_windupb",
            "lambdacraft:weapons.gauss_windupc",
            "lambdacraft:weapons.gauss_windupd" },
        SND_SHOOT_PATH = "lambdacraft:weapons.gaussb";

    public Weapon_Gauss() {
        super(CBCItems.ammo_uranium);
        setCreativeTab(CBCMod.cct); 
        setUnlocalizedName("weapon_gauss");
        setTextureName("lambdacraft:weapon_gauss");
        
        actionShoot = new ActionShoot(0, Weapon_Gauss.SND_SHOOT_PATH) {
            
            {
                setShootRate(7);
            }
            
            @Override
            protected Entity getProjectileEntity(World world, EntityPlayer player) {
                return world.isRemote ? null : new EntityBulletGaussSec(EnumGaussRayType.NORMAL, world, player, player.getCurrentEquippedItem(), null, null, 8);
            }
            
            @Override
            protected void doSpawnEntity(World world, EntityPlayer player) {
                super.doSpawnEntity(world, player);
                Weapon_Gauss.setVelocity(player.getCurrentEquippedItem(), 10.0F);
            }
            
        };
        
        actionJam = new ActionJam(20, "lambdacraft:weapons.gunjam_a");
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity ent, int par4, boolean par5) {
        InfWeapon inf = this.onWpnUpdate(stack, world, ent, par4, par5);
        if(inf == null) return;
        
        final float attnRate = 3F;
        
        float vel = getVelocity(stack);
        vel = Math.max(0, vel - attnRate);
        
        if(inf.isActionPresent("charge")) {
            vel = 10F;
        }
        
        setVelocity(stack, vel);
        
        incrRotation(stack, vel);
    }
    
    private static void setVelocity(ItemStack stack, float f) {
        GenericUtils.loadCompound(stack).setFloat("rotVelocity", f);
    }
    
    private static float getVelocity(ItemStack stack) {
        return GenericUtils.loadCompound(stack).getFloat("rotVelocity");
    }
    
    public static void incrRotation(ItemStack stack, float f) {
        NBTTagCompound nbt = GenericUtils.loadCompound(stack);
        nbt.setFloat("rotation", (nbt.getFloat("rotation") + f) % 360.0F);
    }
    
    public static float getRotation(ItemStack stack) {
        return GenericUtils.loadCompound(stack).getFloat("rotation");
    }

    @Override
    public int getHalfWidth() {
        return 8;
    }

    @Override
    public int getCrosshairID(ItemStack itemStack) {
        return -1;
    }
    
    @Override
    public void onItemClick(World world, EntityPlayer player, ItemStack stack, int keyid) {
        super.onItemClick(world, player, stack, keyid);
        if(keyid == 1) {
            InfWeapon inf = loadInformation(player);
            if(this.canShoot(player, stack))
                inf.executeAction(actionCharge);
        }
    }
    
    @Override
    public void onItemRelease(World world, EntityPlayer pl, ItemStack stack, int keyid) {
        super.onItemRelease(world, pl, stack, keyid);
        if(keyid == 1) {
            InfWeapon inf = loadInformation(pl);
            inf.removeAction("charge");
        }
    }
    
    public Action actionCharge = new Action(150, "charge") {

        @Override
        public int getPriority() {
            return 3;
        }
        
        @Override
        public boolean onActionBegin(World world, EntityPlayer player, InfWeapon information) {
            //Just get ready
            player.playSound(SND_CHARGEA_PATH[0], 0.5F, 1.0F);
            return true;
        }
        
        @Override
        public boolean onActionEnd(World world, EntityPlayer player, InfWeapon inf, boolean isRemoved) {
            int ticks = maxTick - inf.getTickLeft(this);
            if(ticks < 10)
                return false;
            
            if(isRemoved) {
                //Shoot
                int strengh = getStrengh(inf);
                //System.out.println("STR " + strengh);
                if(!world.isRemote) {
                    world.spawnEntityInWorld(new EntityBulletGauss(world, player, Math.max(5, strengh)));
                }
                double d = 0.075, vel = getStrengh(inf) * d;
                Motion3D motion = new Motion3D(player, true);
                player.addVelocity(-motion.motionX * d * strengh,
                        -motion.motionY * d * strengh, -motion.motionZ * d * strengh);
                world.playSoundAtEntity(player, SND_SHOOT_PATH, 0.5F, 1.0F);
                
            } else {
                //Discharge
                player.attackEntityFrom(DamageSource.causePlayerDamage(player), getStrengh(inf) * 0.4F);
            }
            return true;
        }
        
        private int getStrengh(InfWeapon inf) {
            //Max damage 40, Charge in ~2.8 seconds.
            return Math.min((int) (0.7 * (this.maxTick - inf.getTickLeft(this))), 40);
        }
        
        @Override
        public boolean onActionTick(World world, EntityPlayer player, InfWeapon inf) {
            int ticks = maxTick - inf.getTickLeft(this);
            final int intvWindup = 10, intvCharge = 13;
            if(ticks <= intvWindup * 3) {
                if(ticks % intvWindup == 0) {
                    world.playSoundAtEntity(player, SND_CHARGEA_PATH[ticks / intvWindup], 0.5F, 1.0F);
                }
            } else {
                if((ticks - intvWindup * 4 - 1) % intvCharge == 0) {
                    world.playSoundAtEntity(player, SND_CHARGE_PATH, 0.5F, 1.0F);
                }
            }
            if(ticks < 57 && ticks % 6 == 0) {
                if(player.capabilities.isCreativeMode)
                    return true;
                int cnt = WeaponHelper.consumeAmmo(player, Weapon_Gauss.this, 1);
                if(cnt == 1) {
                    inf.removeAction(name);
                }
            }
            
            return true;
        }

        @Override
        public boolean doesConcurrent(Action other) {
            return !other.name.equals("shoot");
        }

        @Override
        public int getRenderPriority() {
            return -1;
        }
    };

}
