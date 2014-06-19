package cn.lambdacraft.deathmatch.item.weapon;

import cn.lambdacraft.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public class Weapon_Crowbar extends ItemSword {

	public Weapon_Crowbar(int item_id) {
		super(item_id, EnumToolMaterial.IRON);
		setUnlocalizedName("weapon_crowbar");
		setCreativeTab(CBCMod.cct);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister
				.registerIcon("lambdacraft:weapon_crowbar");
	}

}
