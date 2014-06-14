package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.ISpecialCrosshair;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entity.EntityBulletEgon;
import cn.lambdacraft.deathmatch.entity.fx.EntityEgonRay;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.events.ItemHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Egon energy weapon.
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_Egon extends WeaponGeneralEnergy_LC implements ISpecialCrosshair {

	public static String SND_WINDUP = "lambdacraft:weapons.egon_windup",
			SND_RUN = "lambdacraft:weapons.egon_run", SND_OFF = "lambdacraft:weapons.egon_off";

	public Icon iconEquipped;

	public Weapon_Egon(int par1) {
		super(par1, CBCItems.ammo_uranium.itemID);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("weapon_egon");
		setIAndU("weapon_egon");
		setJamTime(20);
		setLiftProps(1, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister reg) {
		super.registerIcons(reg);
		iconEquipped = reg.registerIcon("lambdacraft:weapon_egon0");
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		if (canShoot(par3EntityPlayer, par1ItemStack, true))
			par2World.playSoundAtEntity(par3EntityPlayer, SND_OFF, 0.5F, 1.0F);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
	public void onItemUsingTick(World world, EntityPlayer player, ItemStack stack, boolean type, int tickLeft)
    {
    	InformationEnergy inf = loadInformation(stack, player);
    	int dTick = inf.getDeltaTick(type);
    	super.onItemUsingTick(world, player, stack, type, tickLeft);
    	
    	if (inf.ticksExisted > 79 && (dTick - 79) % 42 == 0)
    		world.playSoundAtEntity(player, SND_RUN, 0.5F, 1.0F);

		if (dTick % 3 == 0 && !world.isRemote) {
			if(!player.capabilities.isCreativeMode)
				WeaponHelper.consumeAmmo(player, this, 1);
			if (!canShoot(player, stack, type)) {
				world.playSoundAtEntity(player, SND_OFF, 0.5F, 1.0F);
			}
		}
    }
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, boolean left) {
		super.onItemClick(world, player, stack, left);
		InformationEnergy inf = loadInformation(stack, player);
		if (ItemHelper.getUsingTickLeft(player, left) > 0 && canShoot(player, stack, left)) {
			if (world.isRemote)
				world.spawnEntityInWorld(new EntityEgonRay(world,
						player, stack));
			else {
				world.playSoundAtEntity(player, SND_WINDUP, 0.5F, 1.0F);
			}
		} else world.playSoundAtEntity(player, SND_OFF, 0.5F, 1.0F);

		inf.ticksExisted = inf.lastTick_left = 0;
	}

	@Override
	public void onEnergyWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationEnergy information, boolean left) {
		if(!par2World.isRemote)
			par2World.spawnEntityInWorld(new EntityBulletEgon(par2World, player));
		return;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 300;
	}

	@Override
	public int getWeaponDamage(boolean lefte) {
		return 10;
	}

	@Override
	public int getOffset(boolean left) {
		return 2;
	}

	@Override
	public int getShootTime(boolean left) {
		return left ? 4 : 0;
	}

	@Override
	public String getSoundShoot(boolean left) {
		return "";
	}

	@Override
	public String getSoundJam(boolean left) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public int getHalfWidth() {
		return 8;
	}

	@Override
	public int getCrosshairID(ItemStack is) {
		return -1;
	}

}
