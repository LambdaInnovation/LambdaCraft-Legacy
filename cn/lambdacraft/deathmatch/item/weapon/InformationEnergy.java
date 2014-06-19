package cn.lambdacraft.deathmatch.item.weapon;

import cn.weaponmod.api.information.InformationWeapon;
import net.minecraft.item.ItemStack;

/**
 * 能量武器的附加信息。
 * @author WeAthFolD
 *
 */
public class InformationEnergy extends InformationWeapon {

	public int charge, chargeTime;
	public float rotationVelocity;
	public float rotationAngle;
	
	public InformationEnergy(ItemStack par1ItemStack) {
		super(par1ItemStack);
		charge = 0;
	}

	@Override
	public void resetState() {
		super.resetState();
		charge = chargeTime = 0;
	}

}
