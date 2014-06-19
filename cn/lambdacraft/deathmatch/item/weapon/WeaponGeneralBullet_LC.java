/**
 * 
 */
package cn.lambdacraft.deathmatch.item.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.api.hud.IHudTipProvider;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.weapon.WeaponGeneral;
import cn.weaponmod.api.weapon.WeaponGeneralBullet;

/**
 * @author WeAthFolD
 *
 */
public class WeaponGeneralBullet_LC extends WeaponGeneralBullet implements IHudTipProvider {
	
	protected String iconName = "";
	
	public WeaponGeneralBullet_LC(int par1, int par2ammoID) {
		super(par1, par2ammoID);
	}
	
	public WeaponGeneral setIAndU(String name) {
		iconName = name;
		setUnlocalizedName(name);
		return this;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:" + iconName);
    }

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneralBullet#onUpdate(net.minecraft.item.ItemStack, net.minecraft.world.World, net.minecraft.entity.Entity, int, boolean)
	 */
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getDamage(boolean)
	 */
	@Override
	public int getWeaponDamage(boolean left) {
		return 10;
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getOffset(boolean)
	 */
	@Override
	public int getOffset(boolean left) {
		return 5;
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#doWeaponUplift()
	 */
	@Override
	public boolean doWeaponUplift() {
		return GeneralProps.doWeaponUplift;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IHudTip[] getHudTip(ItemStack itemStack, EntityPlayer player) {
		IHudTip[] tips = new IHudTip[1];
		tips[0] = new IHudTip() {

			@Override
			public Icon getRenderingIcon(ItemStack itemStack,
					EntityPlayer player) {
				if (Item.itemsList[ammoID] != null) {
					return Item.itemsList[ammoID].getIconIndex(itemStack);
				}
				return null;
			}

			@Override
			public String getTip(ItemStack itemStack, EntityPlayer player) {
				return (itemStack.getMaxDamage() - WeaponGeneralBullet_LC.this.getWpnStackDamage(itemStack) - 1) + "|" + WeaponHelper.getAmmoCapacity(ammoID, player.inventory);
			}

			@Override
			public int getTextureSheet(ItemStack itemStack) {
				return itemStack.getItemSpriteNumber();
			}

		};
		return tips;
	}

}
