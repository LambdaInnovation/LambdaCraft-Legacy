package cn.lambdacraft.deathmatch.item.weapon;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.deathmatch.entity.EntityRPGDot;
import cn.lambdacraft.deathmatch.util.InformationRPG;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.feature.IModdable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Weapon_RPG extends Weapon_RPG_Raw implements IModdable {


	public Weapon_RPG(int par1) {
		super(par1);
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {

		InformationRPG inf = (InformationRPG) super.onWpnUpdate(
				par1ItemStack, par2World, par3Entity, par4, par5);
		if (par2World.isRemote || inf == null)
			return;
		int mode = getMode(par1ItemStack);
		if (mode == 0)
			return;
		EntityPlayer player = (EntityPlayer) par3Entity;
		EntityRPGDot dot = getRPGDot(inf, par2World, player);
		if (dot == null || dot.isDead) {
			dot = new EntityRPGDot(par2World, player);
			par2World.spawnEntityInWorld(dot);
			this.setRPGDot(inf, dot);
		}
	}

	public static EntityRPGDot getRPGDot(InformationRPG inf, World world,
			EntityPlayer player) {
		return inf.currentDot;
	}

	public EntityRPGDot getRPGDot(ItemStack is, World world, EntityPlayer player) {
		return (loadInformation(is, player)).currentDot;
	}

	public void setRPGDot(InformationRPG inf, EntityRPGDot dot) {
		inf.currentDot = dot;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IHudTip[] getHudTip(ItemStack itemStack, EntityPlayer player) {
		IHudTip[] tips = new IHudTip[1];
		tips[0] = new IHudTip() {

			@Override
			public Icon getRenderingIcon(ItemStack itemStack,
					EntityPlayer player) {
				if(Item.itemsList[ammoID] != null){
					return Item.itemsList[ammoID].getIconIndex(itemStack);
				}
				return null;
			}

			@Override
			public String getTip(ItemStack itemStack, EntityPlayer player) {
				return String.valueOf(WeaponHelper.getAmmoCapacity(ammoID, player.inventory));
			}

			@Override
			public int getTextureSheet(ItemStack itemStack) {
				return itemStack.getItemSpriteNumber();
			}
			
		};
		return tips;
	}
	
	@Override
	public void onModeChange(ItemStack item, EntityPlayer player, int newMode) {
		item.setItemDamage(newMode);
	}
	
	@Override
	public int getMode(ItemStack item) {
		return item.getItemDamage();
	}
	@Override
	public int getMaxModes() {
		return 2;
	}
	
	@Override
	public String getModeDescription(int mode) {
		return "mode.rpg" + (mode == 0 ? 1 : 0);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("weapon.useinv.name"));
	}

}
