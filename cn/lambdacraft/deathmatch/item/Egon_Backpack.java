/**
 * 
 */
package cn.lambdacraft.deathmatch.item;

import net.minecraft.item.EnumArmorMaterial;
import cn.lambdacraft.core.item.CBCGenericArmor;

/**
 * @author WeAthFolD
 *
 */
public class Egon_Backpack extends CBCGenericArmor {

	/**
	 * @param id
	 * @param mat
	 * @param renderIndex
	 * @param armorType
	 */
	public Egon_Backpack(int id) {
		super(id, EnumArmorMaterial.IRON, 0, 2);
		setIconName("lambdacraft:weapon_egon");
	}

}
