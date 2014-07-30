package cn.weaponmod.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.feature.ISpecialUseable;
import cn.weaponmod.api.information.InformationWeapon;

/**
 * 最基础的武器类，是MyWeaponary的功能核心。一般继承它的子类WeaponGeneralBullet以实现更多功能
 * @author WeAthFolD
 */
public abstract class WeaponGeneral extends Item implements ISpecialUseable {

	public int type;
	public int ammoID;
	public float upLiftRadius, recoverRadius;
	
	/**
	 * 
	 * @param par1
	 *            物品的ID.
	 * @param par2AmmoID
	 *            对应子弹的ID.(不一定被使用）
	 * @param par3MaxModes
	 *            最大模式数。（不一定被使用）
	 */
	public WeaponGeneral(int par1, int par2AmmoID) {
		super(par1);
		setMaxStackSize(1);
		this.setFull3D();
		ammoID = par2AmmoID;
	}
	
	//-------------------------SETTINGS---------------------

	/**
	 * 设置武器上抬的参数。
	 * 
	 * @param uplift
	 *            每次射击上抬的角度。
	 * @param recover
	 *            每tick回复角度。
	 */
	public void setLiftProps(float uplift, float recover) {
		upLiftRadius = uplift;
		recoverRadius = recover;
	}

	//---------------------ATTRIBUTES---------------------
	
	/**
	 * 获得武器对生物的伤害。
	 * 
	 * @param mode
	 *            当前武器模式
	 */
	public abstract int getWeaponDamage(boolean left);

	/**
	 * 获得武器射击的位置偏移。
	 * 
	 * @param mode
	 *            当前武器模式
	 */
	public abstract int getOffset(boolean left);
	
	/**
	 * 获取是否进行武器枪口上抬。
	 * @return
	 */
	public abstract boolean doWeaponUplift();
	
	//---------------------------WEAPON INFORMATION-----------------------
	
	/**
	 * 加载武器信息。
	 * {@link cn.weaponmod.api.WMInformation#register}
	 * @return 需要的武器信息，可能为null
	 */
	public abstract InformationWeapon loadInformation(ItemStack par1Itack, EntityPlayer entityPlayer);

	/**
	 * 获取武器信息。
	 * @return 需要的武器信息，可能为null
	 */
	public InformationWeapon getInformation(ItemStack itemStack, World world) {
		return WMInformation.getInformation(itemStack, world);
	}
	
	//-----------------------------------FUNCTIONS----------------------------------
	
	/**
	 * 进行武器的Tick相关计算和主要功能，请在子类的onUpdate(...)中调用它。
	 */
	public InformationWeapon onWpnUpdate(ItemStack par1ItemStack,
			World par2World, Entity par3Entity, int par4, boolean par5) {
		if (!(par3Entity instanceof EntityPlayer) || !par5)
			return null;

		InformationWeapon information = loadInformation(par1ItemStack, (EntityPlayer) par3Entity);
		if (information == null) 
			return null;
		
		information.updateTick();
		if(par3Entity instanceof EntityLivingBase && doesAbortAnim(par1ItemStack, (EntityLivingBase) par3Entity)) 
			((EntityLivingBase)par3Entity).isSwingInProgress = false;
		
		return information;
	}
	
	public boolean doesAbortAnim(ItemStack itemStack, EntityLivingBase player) {
		return true;
	}
	
    @Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
    	return doesAbortAnim(stack, player);
    }
    
    @Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack,
			boolean left) {
	}

	@Override
	public void onItemUsingTick(World world, EntityPlayer player,
			ItemStack stack, boolean type, int tickLeft) {
	}

    @Override
	public boolean onBlockStartBreak(ItemStack itemstack, int i, int j, int k, EntityPlayer player)
    {
    	return !player.capabilities.isCreativeMode;
    }
    
    @Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
    	return true;
    }
	
}
