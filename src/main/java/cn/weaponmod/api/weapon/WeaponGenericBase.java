package cn.weaponmod.api.weapon;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cn.liutils.api.util.GenericUtils;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.feature.IClickHandler;
import cn.weaponmod.api.information.InfWeapon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * The most basic weapon class and the function core of MyWeaponary.
 * Implementing ItemSword in order to use MC's built-in mechanism for block-breaking.
 * @author WeAthFolD
 */
public abstract class WeaponGenericBase extends ItemSword implements IClickHandler {

    public Item ammoItem;
    public boolean useAmmoTip = true;
    
    /**
     * 
     * @param ammo Corresponding ammo item.
     */
    public WeaponGenericBase(Item ammo) {
        super(ToolMaterial.IRON);
        setMaxStackSize(1);
        this.setFull3D();
        ammoItem = ammo;
    }
    
    //-----------------INTERFACE---------------------

    /**
     * Handles information update. call it in your onUpdate function.
     */
    public InfWeapon onWpnUpdate(ItemStack stack,
            World world, Entity entity, int par4, boolean par5) {
        if (!(entity instanceof EntityPlayer) || !par5)
            return null;

        InfWeapon information = loadInformation((EntityPlayer) entity);
        
        information.updateTick();
        if(information.swingAbortion) {
            ((EntityLivingBase)entity).isSwingInProgress = false;
        }
        
        return information;
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        onWpnUpdate(stack, world, entity, par4, par5);
    }
    
    @Override
    public abstract void onItemClick(World world, EntityPlayer player, ItemStack stack,
            int keyid);

    @Override
    public abstract void onItemRelease(World world, EntityPlayer pl, ItemStack stack,
            int keyid);
    
    @Override
    public abstract void onItemUsingTick(World world, EntityPlayer player,
            ItemStack stack, int keyid, int ticks);
    
    /**
     * Do we need to activate inventory consumption?
     * @return true if consumeInventory, else consume weapon ammo
     */
    public boolean consumeInvWhenShoot() {
        return this.maxStackSize == 1;
    }
    
    //--------------------UTILITIES--------------------------
    public final int getAmmo(ItemStack stack) {
        NBTTagCompound nbt = GenericUtils.loadCompound(stack);
        return nbt.getInteger("ammo");
    }

    public final void setAmmo(ItemStack stack, int ammo) {
        GenericUtils.loadCompound(stack).setInteger("ammo", ammo < 0 ? 0 : 
            ammo > getMaxDamage() ? getMaxDamage() : ammo);
    }
    
    /**
     * Consume [damage] amount of ammo in a specific stack.
     * @param stack the stack
     * @param damage how many to consume?
     * @return are we successful? (not changed if unsuccessful)
     */
    public boolean consumeAmmo(ItemStack stack, int damage) {
        int orig = getAmmo(stack);
        if(orig >= damage) {
            setAmmo(stack, orig - damage);
            return true;
        } else {
            return false;
        }
    }
    
    //----------------------INTERNAL----------------------------------
    
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        InfWeapon inf = loadInformation(stack, player);
        return inf == null ? false : inf.swingAbortion;
    }
    
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
            World world, int x, int y, int z, int side, float hitX, float hitY,
            float hitZ) {
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int i, int j, int k, EntityPlayer player)
    {
        return !player.capabilities.isCreativeMode;
    }
    
    @Override
    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
    {
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs ct, List list)
    {
        ItemStack is = new ItemStack(item);
        setAmmo(is, this.getMaxDamage());
        list.add(is);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack,
            EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if(this.getMaxDamage() > 0 && useAmmoTip)
            par3List.add(StatCollector.translateToLocal("ammocap.name")
                + ": "
                + this.getAmmo(par1ItemStack) + "/"
                + par1ItemStack.getMaxDamage());
    }
    
    /**
     * Automatically loads the weapon information
     * {@link cn.weaponmod.api.WMInformation#register}
     * @return created weapon information
     */
    @Deprecated
    public final InfWeapon loadInformation(ItemStack stack, EntityPlayer player) {
        return WMInformation.instance.getInformation(player);
    }
    
    public final InfWeapon loadInformation(EntityPlayer player) {
        return WMInformation.instance.getInformation(player);
    }
    
}
