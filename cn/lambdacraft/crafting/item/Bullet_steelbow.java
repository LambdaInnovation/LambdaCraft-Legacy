package cn.lambdacraft.crafting.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import cn.lambdacraft.core.LCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Bullet_steelbow extends ItemBullet {

	public Bullet_steelbow() {
		super();
		setCreativeTab(LCMod.cctMisc);
		setUnlocalizedName("bullet_steelbow");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("lambdacraft:steelbow");
	}

}
