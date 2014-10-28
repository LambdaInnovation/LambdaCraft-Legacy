package cn.lambdacraft.crafting.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.item.CBCGenericItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Bullet_steelbow extends CBCGenericItem {

	public Bullet_steelbow() {
		super();
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("bullet_steelbow");
		setTextureName("lambdacraft:steelbow");
	}
}
