package cn.lambdacraft.api.hud;

import net.minecraft.item.ItemStack;

/**
 * 特殊的自定义准星接口，允许自定义高度。（应用在Item上）
 * @author WeAthFolD
 */
public interface ISpecialCrosshair {
	
	/**
	 * 获取准星贴图的size的一半
	 * @return
	 */
	public int getHalfWidth();
	
	/**
	 * 获取准星id
	 * @see cn.lambdacraft.core.prop.ClientProps
	 * @param itemStack
	 * @return
	 */
	public int getCrosshairID(ItemStack itemStack);
}
