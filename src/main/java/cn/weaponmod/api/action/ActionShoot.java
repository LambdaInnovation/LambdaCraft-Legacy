package cn.weaponmod.api.action;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cn.liutils.api.entity.EntityBullet;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.client.render.RendererBulletWeapon;
import cn.weaponmod.api.information.InfUtils;
import cn.weaponmod.api.information.InfWeapon;
import cn.weaponmod.api.weapon.WeaponGenericBase;
import cn.weaponmod.api.weapon.WeaponGeneric;

/**
 * **available only for WeaponGeneral subclasses**
 * Shoot action. The damage and scatter radius are modifiable.
 * @author WeAthFolD
 */
public class ActionShoot extends Action {

    int damage = 2;
    float scatter = 0;
    int amountConsume = 1;
    boolean consumeAmmo = true;
    String sound = "";
    float soundVolume = 0.5F;
    int shootRate = 10;
    public boolean left = false;
    
    public boolean testTicker = true;
    public String ticker_channel = DEFAULT_TICKER_CHANNEL;
    
    protected ResourceLocation muzzleflash[] = null;
    
    public static final String DEFAULT_TICKER_CHANNEL = "shoot";
    protected Vec3 muzOffset = Vec3.createVectorHelper(0.0, 0.0, 0.0);
    protected float muzScale = 1.0F;
    
    public ActionShoot copyFrom(ActionShoot res) {
        sound = res.sound;
        setSoundVolume(res.soundVolume);
        setShootRate(res.shootRate);
        setConsume(res.consumeAmmo, res.amountConsume);
        setMuzzleOffset(res.muzOffset.xCoord, res.muzOffset.yCoord, res.muzOffset.zCoord);
        setMuzzleScale(res.muzScale);
        muzzleflash = res.muzzleflash;
        left = res.left;
        damage = res.damage;
        return this;
    }
    
    public ActionShoot copy() {
        ActionShoot ac = new ActionShoot(0, 0, "");
        ac.copyFrom(this);
        return ac;
    }
    
    public ActionShoot(int dmg, String snd) {
        super(3, "shoot");
        this.damage = dmg;
        sound = snd;
    }
    
    public ActionShoot(int dmg, float scat, String snd) {
        super(3, "shoot");
        this.damage = dmg;
        this.scatter = scat;
        sound = snd;
    }
    
    public ActionShoot setLeft(boolean b) {
        left = b;
        return this;
    }
    
    public ActionShoot setMuzzleScale(float f) {
        muzScale = f;
        return this;
    }
    
    public ActionShoot setMuzzle(ResourceLocation[] muz) {
        muzzleflash = muz;
        return this;
    }
    
    public ActionShoot setShootRate(int i) {
        shootRate = i;
        return this;
    }
    
    public ActionShoot setSoundVolume(float volume) {
        soundVolume = volume;
        return this;
    }
    
    public ActionShoot setConsume(boolean doesConsume, int amount) {
        this.consumeAmmo = doesConsume;
        amountConsume = amount;
        return this;
    }
    
    /**
     * Easy wrapper. Return the projectile entity that this action uses. Can be used to create ARgrenades and other stuffs.
     * @param world
     * @param player
     * @return
     */
    protected Entity getProjectileEntity(World world, EntityPlayer player) {
        EntityBullet bullet = new EntityBullet(world, player, damage, scatter);
        bullet.renderFromLeft = left;
        return bullet;
    }

    @Override
    public boolean onActionEnd(World world, EntityPlayer player,
            InfWeapon information) {
        return false;
    }

    @Override
    public int getPriority() {
        return 1;
    }
    
    @Override
    public boolean doesConcurrent(Action other) {
        return true;
    }
    
    @Override
    public boolean isAvailable(World world, EntityPlayer player, InfWeapon inf) {
        return InfUtils.getDeltaTick(inf, ticker_channel) >= shootRate;
    }
    
    protected void doSpawnEntity(World world, EntityPlayer player) {
        Entity bullet = getProjectileEntity(world, player);
        if(bullet != null) //Minecraft doesn't do spawn checks
            world.spawnEntityInWorld(bullet);
    }

    @Override
    public boolean onActionBegin(World world, EntityPlayer player, InfWeapon inf) {
        if(InfUtils.getDeltaTick(inf, ticker_channel) < shootRate) {
            return false;
        }
        ItemStack curItem = player.getCurrentEquippedItem();
        if(curItem == null) {
            return false;
        }
        
        WeaponGenericBase wpn = (WeaponGenericBase) curItem.getItem();
        if(wpn instanceof WeaponGeneric) {
            Action act_uplift = getActionUplift(inf);
            
            if(consumeAmmo && !player.capabilities.isCreativeMode) {
                if(wpn.consumeInvWhenShoot()) {
                    if(!consumeAmmo(player, curItem, amountConsume)) {
                        return false;
                    }
                } else {
                    if(!wpn.consumeAmmo(curItem, amountConsume))
                        return false;
                }
            }
            doSpawnEntity(world, player);
            if(world.isRemote) { //Only client
                if(act_uplift != null) act_uplift.onActionBegin(world, player, inf);
            }
            //TODO:Check out why we have to play it in client
            if(sound != null && sound != "")
                player.playSound(sound, soundVolume, 1.0F);
            //System.out.println("Action executed in " + world.isRemote);
            inf.updateTicker(ticker_channel);
        }
        return true;
    }
    
    
    @Override
    public boolean onActionTick(World world, EntityPlayer player, InfWeapon inf) {
        return true;
    }
    
    protected boolean consumeAmmo(EntityPlayer player, ItemStack stack, int amount) {
        WeaponGenericBase wpn = (WeaponGenericBase)stack.getItem();
        if(wpn.getMaxDamage() == 0) { //Consume inventory
            return WeaponHelper.consumeAmmo(player, wpn, amount) == 0;
        }
        return wpn.consumeAmmo(stack, amount);
    }
    
    protected Action getActionUplift(InfWeapon inf) {
        ItemStack cs = inf.owner.getCurrentEquippedItem();
        if(cs == null) return null;
        
        Item it = cs.getItem();
        if(it instanceof WeaponGeneric) {
            return ((WeaponGeneric) it).actionUplift;
        }
        return null;
    }
    
    public ActionShoot setMuzzleOffset(double x, double y, double z) {
        this.muzOffset.xCoord = x;
        this.muzOffset.yCoord = y;
        this.muzOffset.zCoord = z;
        return this;
    }

    @Override
    public int getRenderPriority() {
        return 1;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    /**
     * 进行渲染器的叠加渲染。和物品渲染一样，实际绘制的范围在（0, 0, 0）到（1, 1, 1）
     * 注意所有的变换行为都会影响到整个物品的渲染。
     */
    public void applyRenderEffect(World world, EntityPlayer player, InfWeapon inf, boolean firstPerson) {
        GL11.glPushMatrix(); {
            if(muzzleflash != null) {
                int id = InfUtils.getDeltaTick(inf, ticker_channel);
                if(id >= muzzleflash.length) id = muzzleflash.length  - 1;
                ResourceLocation tex = muzzleflash[id];
                
                GL11.glTranslated(.35 + muzOffset.xCoord, 0.1 + muzOffset.yCoord, 0.0 + muzOffset.zCoord);
                GL11.glColor4f(1F, 1F, 1F, .8F);
                RendererBulletWeapon.renderMuzzleflashIn2d(Tessellator.instance, tex, 0, 0, 0, muzScale);
            }
        } GL11.glPopMatrix();
    }

}
