package cn.lambdacraft.crafting.item;

import cn.lambdacraft.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class IngotUranium extends Item {

	public IngotUranium(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cctMisc);
		setUnlocalizedName("ingotUranium");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("lambdacraft:uranium");
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		if (!(par3Entity instanceof EntityPlayer))
			return;
		EntityPlayer p = (EntityPlayer) par3Entity;
		ItemStack currentItem = p.inventory.getCurrentItem();
		if (currentItem != par1ItemStack)
			return;
		if (par2World.getWorldTime() % 20 == 0) {
			p.attackEntityFrom(DamageSource.starve, 1);
		}
	}
}
