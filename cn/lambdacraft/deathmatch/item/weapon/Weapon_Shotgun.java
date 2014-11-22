package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.liutils.core.client.register.LIKeyProcess;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionBuckshot;
import cn.weaponmod.api.action.ActionMultipleReload;
import cn.weaponmod.api.information.InfWeapon;
import cn.weaponmod.core.proxy.WMClientProxy;

public class Weapon_Shotgun extends WeaponGenericLC {

	public Weapon_Shotgun() {

		super(CBCItems.ammo_shotgun);

		setIAndU("weapon_shotgun");
		setMaxDamage(8);
		setCreativeTab(CBCMod.cct);

		actionShoot = new ActionBuckshot(2, 5, "lambdacraft:weapons.sbarrela").setShootRate(20).setMuzzle(ClientProps.MUZZLEFLASH2).setMuzzleOffset(0, 0, 0.1);
		actionReload = new ActionMultipleReload(8, 300).setSound("lambdacraft:weapons.reloadc").setSoundFinish("lambdacraft:weapons.scocka");
	}

	private Action actionDoubleBolt = new ActionBuckshot(2, 13, "lambdacraft:weapons.sbarrelb")
		.setBucks(12).setShootRate(30).setConsume(true, 2);
	
	@Override
	public void onItemRelease(World world, EntityPlayer pl, ItemStack stack,
			int keyid) {
		super.onItemRelease(world, pl, stack, keyid);
		if(keyid == 1) {
			InfWeapon inf = loadInformation(pl);
			inf.executeAction(actionDoubleBolt);
			LIKeyProcess.getBindingByName(WMClientProxy.KEY_ID_RELOAD);
		}
	}

}
