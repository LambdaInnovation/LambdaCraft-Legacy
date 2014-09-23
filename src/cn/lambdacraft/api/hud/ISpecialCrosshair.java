package cn.lambdacraft.api.hud;

import net.minecraft.item.ItemStack;

/**
 * 特殊的自定义准星类，允许自定义高度。
 * @author WeAthFolD
 */
public interface ISpecialCrosshair {
	public int getHalfWidth();
	public int getCrosshairID(ItemStack itemStack);
}
