package cn.lambdacraft.deathmatch.item.weapon;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.deathmatch.entity.EntityRPGDot;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.feature.IModdable;
import cn.weaponmod.api.information.InfWeapon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Weapon_RPG extends Weapon_RPG_Raw implements IModdable {


	public Weapon_RPG() {
		super();
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {

		InfWeapon inf = super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		if (par2World.isRemote || inf == null)
			return;
		int mode = getMode(par1ItemStack);
		if (mode == 0)
			return;
		EntityPlayer player = (EntityPlayer) par3Entity;
		EntityRPGDot dot = getRPGDot(par1ItemStack, par2World, player);
		if (dot == null || dot.isDead) {
			dot = new EntityRPGDot(par2World, player);
			par2World.spawnEntityInWorld(dot);
			this.setRPGDot(inf, dot);
		}
	}

	public EntityRPGDot getRPGDot(ItemStack is, World world, EntityPlayer player) {
		if(getMode(is) == 0) return null;
		InfWeapon inf = this.loadInformation(is, player);
		Entity ent = world.getEntityByID(inf.infData.getInteger("dot"));
		if(ent != null && ent instanceof EntityRPGDot)
			return (EntityRPGDot) ent;
		return null;
	}

	public void setRPGDot(InfWeapon inf, EntityRPGDot dot) {
		inf.infData.setInteger("dot", dot.getEntityId());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IHudTip[] getHudTip(ItemStack itemStack, EntityPlayer player) {
		IHudTip[] tips = new IHudTip[1];
		tips[0] = new IHudTip() {

			@Override
			public IIcon getRenderingIcon(ItemStack itemStack,
					EntityPlayer player) {
				if(ammoItem != null){
					return ammoItem.getIconIndex(itemStack);
				}
				return null;
			}

			@Override
			public String getTip(ItemStack itemStack, EntityPlayer player) {
				return String.valueOf(WeaponHelper.getAmmoCapacity(ammoItem, player.inventory));
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
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, int keyid) {
		super.onItemClick(world, player, stack, keyid);
		if(!world.isRemote && keyid == 1) {
			onModeChange(stack, player, (getMode(stack) + 1) % getMaxModes());
		}
	}

}
