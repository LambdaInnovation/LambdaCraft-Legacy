package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.ISpecialCrosshair;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entity.EntityCrossbowArrow;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.feature.IModdable;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.api.weapon.IZoomable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Crossbow in HLDM Style. Mode I : Non-delay, sniper rifle style. Mode II :
 * Explosive arrow.
 * 
 * @author WeAthFolD
 */
public class Weapon_Crossbow extends WeaponGeneralBullet_LC implements
		IModdable, IZoomable, ISpecialCrosshair {

	public IIcon[] sideIcons = new IIcon[6];

	public Weapon_Crossbow() {
		super(CBCItems.ammo_bow);

		setUnlocalizedName("weapon_crossbow");
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(1);
		setMaxDamage(6);
		setNoRepair();
		
		this.reloadTime = 55;
		iconName = "weapon_crossbow";

		setLiftProps(10, .75F);
		setReloadTime(40);

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		for (int i = 0; i < 6; i++) {
			sideIcons[i] = reg.registerIcon("lambdacraft:crossbow_side" + i);
		}
	}

	@Override
	public void onUpdate(ItemStack is, World par2World,
			Entity entity, int par4, boolean par5) {
		super.onWpnUpdate(is, par2World, entity, par4, par5);
		if(!par5) {
			NBTTagCompound nbt = loadCompound(is);
			if(nbt.getInteger("mode") == 1)
				nbt.setInteger("mode", 0);
		}
		// TODO:Left to be finished
		/*
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			int mode = getMode(par1ItemStack);
			if (par5) {
				if (mode == 1)
					player.capabilities.setPlayerWalkSpeed(0.005F);
				else
					player.capabilities.setPlayerWalkSpeed(0.1F);

			} else {
				ItemStack stack = player.getCurrentEquippedItem();
				if (stack == null || stack.itemID != itemID) 
					player.capabilities.setPlayerWalkSpeed(0.1F);
			}
		}
		
		
		if (!par5) {
			NBTTagCompound nbt = loadCompound(par1ItemStack);
			nbt.setInteger("mode", 0);
		}
		 */
	}
	
	@Override
	protected Entity getBulletEntity(ItemStack is, World world, EntityPlayer player, boolean left) {
		return world.isRemote ? null : new EntityCrossbowArrow(world, player, getMode(is) == 0);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer,
				par4);
	}

	@Override
	public String getSoundShoot(boolean left) {
		return "lambdacraft:weapons.xbow_fire";
	}

	@Override
	public String getSoundJam(boolean left) {
		return "lambdacraft:weapons.gunjam_a";
	}

	@Override
	public String getSoundReload() {
		return "lambdacraft:weapons.xbow_reload";
	}

	@Override
	public int getShootTime(boolean left) {
		return left ? 30 : 0;
	}

	@Override
	public int getWeaponDamage(boolean left) {
		return 20;
	}

	@Override
	public int getOffset(boolean left) {
		return 0;
	}

	public static boolean isBowPulling(ItemStack item) {
		InformationBullet information = (InformationBullet) WMInformation.getInformation(item, true);
		if (information == null)
			return false;
		return !(information.getDeltaTick(true) < 17);
	}

	@Override
	public void onModeChange(ItemStack item, EntityPlayer player, int newMode) {
		NBTTagCompound nbt = loadCompound(item);
		nbt.setInteger("mode", newMode);
	}

	@Override
	public int getMode(ItemStack item) {
		return loadCompound(item).getInteger("mode");
	}

	@Override
	public int getMaxModes() {
		return 2;
	}

	@Override
	public String getModeDescription(int mode) {
		return null;
	}

	private NBTTagCompound loadCompound(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null)
			itemStack.stackTagCompound = new NBTTagCompound();
		return itemStack.stackTagCompound;
	}

	@Override
	public boolean isItemZooming(ItemStack stack, World world,
			EntityPlayer player) {
		return getMode(stack) == 1;
	}

	@Override
	public int getHalfWidth() {
		return 12;
	}

	@Override
	public int getCrosshairID(ItemStack is) {
		return getMode(is) == 1 ? 15 : 0;
	}

	@Override
	public boolean doesSlowdown(ItemStack stack, World world,
			EntityPlayer player) {
		return false;
	}
}
