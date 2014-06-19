package cn.weaponmod.client.keys;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cn.liutils.api.client.register.IKeyProcess;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.api.weapon.WeaponGeneralBullet;
import cn.weaponmod.network.NetDeathmatch;

public class KeyReload implements IKeyProcess {

	@Override
	public void onKeyDown(int keyCode, boolean isEnd) {
		if(isEnd)
			return;
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		if (player == null || mc.currentScreen != null)
			return;
		ItemStack currentItem = player.inventory.getCurrentItem();
		if (currentItem != null && currentItem.getItem() instanceof WeaponGeneralBullet) {
			WeaponGeneralBullet wpn = (WeaponGeneralBullet) currentItem
					.getItem();
			InformationBullet inf = (InformationBullet) wpn.loadInformation(currentItem, player);
			onReload(currentItem, inf, player);
		}

	}

	@Override
	public void onKeyUp(int keyCode, boolean isEnd) {
	}

	private void onReload(ItemStack is, InformationBullet inf,
			EntityPlayer player) {
		WeaponGeneralBullet wpn = (WeaponGeneralBullet) is.getItem();
		int stackInSlot = -1;
		for (int i = 0; i < player.inventory.mainInventory.length; i++) {
			if (player.inventory.mainInventory[i] == is) {
				stackInSlot = i;
				break;
			}
		}
		if (stackInSlot == -1)
			return;

		if (wpn.onSetReload(is, player))
			NetDeathmatch.sendModePacket((byte) stackInSlot, (byte) 1, (byte) 0);
	}
}
