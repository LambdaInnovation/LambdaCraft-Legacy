package cn.weaponmod.api.weapon;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cn.liutils.api.entity.EntityBullet;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.api.information.InformationWeapon;
import cn.weaponmod.events.ItemHelper;
import cn.weaponmod.proxy.WMClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 一般的子弹武器类，支持R键上弹，用鼠标移上的方式查看弹药。
 * @author WeAthFolD
 */
public abstract class WeaponGeneralBullet extends WeaponGeneral implements IReloaddable {

	public int reloadTime; //上弹所需时间
	public int jamTime; //卡弹声音播放时间间隔
	public boolean isAutomatic = true; //是否自动武器（影响射击方式）

	public WeaponGeneralBullet(int par1, int par2ammoID) {
		super(par1, par2ammoID);
		setMaxStackSize(1);
		upLiftRadius = 5.5F;
		recoverRadius = 0.6F;
		type = 0;
	}

	// -------------------Attributes-------------------
	/**
	 * 获取射击声音路径。
	 */
	public String getSoundShoot(boolean left) {
		return "";
	}

	/**
	 * 获取卡弹声音路径。
	 */
	public String getSoundJam(boolean left) {
		return "";
	}

	/**
	 * 获取开始上弹时播放声音的路径。
	 */
	public String getSoundReload() {
		return "";
	}

	/**
	 * 获取上弹完成时播放声音的路径。
	 */
	public String getSoundReloadFinish() {
		return "";
	}

	/**
	 * 设置枪械射击所需时间。
	 */
	public int getShootTime(boolean left) {
		return 10;
	}

	/**
	 * 设置枪械上弹所需时间。
	 */
	public final void setReloadTime(int par1) {
		reloadTime = par1;
	}

	/**
	 * 设置枪械的卡弹时间间隔。
	 */
	public final void setJamTime(int par1) {
		jamTime = par1;
	}
	
	/**
	 * 获取射击动作所用的发射实体。【参考EntityBullet】
	 */
	protected Entity getBulletEntity(ItemStack is, World world,
			EntityPlayer player, boolean left) {
		return new EntityBullet(world, player, this.getWeaponDamage(left));
	}
	
	//------------------Utilities---------------------
	public int getWpnStackDamage(ItemStack stack) {
		NBTTagCompound nbt = loadNBT(stack);
		return nbt.getInteger("ammo");
	}

	public void setWpnStackDamage(ItemStack stack, int damage) {
		loadNBT(stack).setInteger("ammo", damage < 0 ? 0 : 
			damage > getMaxDamage() ? getMaxDamage() : damage);
	}
	
	/**
	 * 消耗武器弹药。
	 * @param stack 对应物品
	 * @param damage 要消耗多少？
	 */
	public void damageWeapon(ItemStack stack, int damage) {
		setWpnStackDamage(stack, getWpnStackDamage(stack) + damage);
	}
	
	/**
	 * 用来决定当前武器是否可以射击【弹药充足】。
	 */
	public boolean canShoot(EntityPlayer player, ItemStack is, boolean side) {
		return (is.getMaxDamage() - this.getWpnStackDamage(is) - 1 > 0)
				|| player.capabilities.isCreativeMode;
	}
	
	/**
	 * 用来决定本tick是否进行一次射击动作。
	 */
	public boolean doesShoot(InformationBullet inf, EntityPlayer player,
			ItemStack itemStack, boolean side) {
		boolean canUse;
		canUse = canShoot(player, itemStack, side);
		return getShootTime(side) != 0 && canUse
				&& inf.getDeltaTick(side) >= getShootTime(side)
				&& !inf.isReloading;
	}

	/**
	 * 用来决定本tick是否进行一次上弹动作。
	 */
	public boolean doesReload(InformationBullet inf, ItemStack itemStack) {
		return (inf.isReloading && inf.getDeltaTick(false) >= this.reloadTime);
	}

	/**
	 * 用来决定本tick是否调用onJam()播放卡弹声音。
	 */
	public boolean doesJam(InformationBullet inf, EntityPlayer player,
			ItemStack itemStack, boolean left) {
		Boolean canUse;
		canUse = canShoot(player, itemStack, left);
		return (jamTime != 0 && !canUse && inf.getDeltaTick(left) > jamTime);
	}
	
	//-------------------Functiuons--------------
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationBullet information, boolean left) {
		Entity entity = this.getBulletEntity(par1ItemStack, par2World, player, left);
		if(entity != null)
			par2World.spawnEntityInWorld(entity);
		if (!player.capabilities.isCreativeMode)
			if(!onConsumeAmmo(par1ItemStack, par2World, player, information, left))
				this.damageWeapon(par1ItemStack, 1);
		//System.out.println("OnShoot in " + par2World.isRemote);
		par2World.playSoundAtEntity(player, this.getSoundShoot(left), 0.5F, 1.0F);
		information.setLastTick_Shoot(left);
		information.setMuzzleTick(left);
		if(par2World.isRemote)
			WMClientProxy.cth.setUplift();
	}
	
	public boolean onConsumeAmmo(ItemStack stack, World world, EntityPlayer player, InformationBullet inf, boolean left) {
		return false;
	}

	public void onBulletWpnJam(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information, boolean side) {
		par2World.playSoundAtEntity(par3Entity, getSoundJam(side), 0.5F, 1.0F);
		information.setLastTick(side);
	}

	@Override
	public boolean onSetReload(ItemStack itemStack, EntityPlayer player) {
		InformationBullet inf = (InformationBullet) loadInformation(itemStack, player);
		if (getWpnStackDamage(itemStack) <= 0)
			return false;

		if (!inf.isReloading) {
			if (WeaponHelper.hasAmmo(this, player))
				player.worldObj.playSoundAtEntity(player, getSoundReload(),
						0.5F, 1.0F);
			ItemHelper.stopUsingItem(player, true);
			ItemHelper.stopUsingItem(player, false);
			inf.isReloading = true;
			inf.setLastTick(false);
			inf.setLastTick(true);
		}
		return true;
	}

	public void onBulletWpnReload(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information) {

		EntityPlayer player = par3Entity;
		int dmg = getWpnStackDamage(par1ItemStack);
		if (dmg <= 0) {
			information.setLastTick(false);
			information.isReloading = false;
			return;
		}
		this.setWpnStackDamage(par1ItemStack, WeaponHelper.consumeAmmo(player, this, dmg));
		par3Entity.playSound(this.getSoundReloadFinish(), 0.5F, 1.0F);
		information.isReloading = false;
		information.setLastTick(false);

		return;
	}
	
	@Override
	/**
	 * 处理武器左键右键按下的行为。
	 */
	public void onItemClick(World world, EntityPlayer player, ItemStack stack,
			boolean left) {
		InformationBullet information = (InformationBullet) loadInformation(stack, player);
		Boolean canUse = canShoot(player, stack, left);
		if (canUse) {
			if (this.doesShoot(information, player, stack, left)) 
				this.onBulletWpnShoot(stack, world, player, information, left);
			//information.isReloading = false;
			if (isAutomatic)
				ItemHelper.setItemInUse(player, stack, 500, left);
		} else 
			onSetReload(stack, player); //要自动上弹么？
		return;
	}

	@Override
	public void onItemUsingTick(World world, EntityPlayer player,
			ItemStack stack, boolean type, int tickLeft) {
		InformationBullet information = (InformationBullet) loadInformation(stack, player);

		if (doesShoot(information, player, stack, type))
			this.onBulletWpnShoot(stack, world, player, information, type);

		else if (doesJam(information, player, stack, type))
			this.onBulletWpnJam(stack, world, player, information, type);
	}

	@Override
	public InformationWeapon onWpnUpdate(ItemStack par1ItemStack,
			World par2World, Entity par3Entity, int par4, boolean par5) {

		InformationBullet information = (InformationBullet) super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);

		if (information == null) {
			information = (InformationBullet) getInformation(par1ItemStack, par2World);
			if (information == null)
				return null;
			information.isReloading = false;
			return null;
		}
		
		//System.out.println(par2World.isRemote + " : " + this.getWpnStackDamage(par1ItemStack));

		EntityPlayer player = (EntityPlayer) par3Entity;
		if (doesReload(information, par1ItemStack))
			this.onBulletWpnReload(par1ItemStack, par2World, player,
					information);

		return information;
	}
	
	public InformationWeapon superOnWpnUpdate(ItemStack par1ItemStack,
			World par2World, Entity par3Entity, int par4, boolean par5) {
		return super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) { }

	@Override
	public InformationWeapon getInformation(ItemStack itemStack, World world) {
		return WMInformation.getInformation(itemStack, world);
	}

	@Override
	public InformationWeapon loadInformation(ItemStack par1ItemStack, EntityPlayer player) {
		InformationWeapon inf = getInformation(par1ItemStack, player.worldObj);
		if (inf != null)
			return inf;
		inf = new InformationBullet(par1ItemStack);
		WMInformation.register(par1ItemStack, inf, player.worldObj);
		return inf;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 0;
	}
	
	protected NBTTagCompound loadNBT(ItemStack s) {
		if (s.stackTagCompound == null)
			s.stackTagCompound = new NBTTagCompound();
		return s.stackTagCompound;
	}
	
	// ------------------Client-----------------------
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(StatCollector.translateToLocal("ammocap.name")
				+ ": "
				+ (par1ItemStack.getMaxDamage()
						- this.getWpnStackDamage(par1ItemStack) - 1) + "/"
				+ (par1ItemStack.getMaxDamage() - 1));
	}
	
	@SideOnly(Side.CLIENT)
	/**
	 * 获取上弹时武器的旋转角度（0.0F~1.0F)
	 * @param itemStack
	 * @return
	 */
	public float getRotationForReload(ItemStack itemStack) {
		InformationBullet inf = (InformationBullet) WMInformation.getInformation(itemStack, true);
		int dt = inf.getDeltaTick(false);
		float changeTime = reloadTime / 5F;
		float rotation = 1.0F;
		if (dt < changeTime) {
			rotation = MathHelper.sin(dt / changeTime * (float) Math.PI * 0.5F);
		} else if (dt > reloadTime - changeTime) {
			rotation = MathHelper.sin((reloadTime - dt) / changeTime
					* (float) Math.PI * 0.5F);
		}
		return rotation;
	}
	
}
