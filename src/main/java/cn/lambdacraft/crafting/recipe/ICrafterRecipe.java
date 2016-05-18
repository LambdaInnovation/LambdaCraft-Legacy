/**
 * 
 */
package cn.lambdacraft.crafting.recipe;

import net.minecraft.item.ItemStack;

/**
 * 武器合成机的合成表接口
 * @author WeathFolD
 */
public interface ICrafterRecipe {
    
    int SLOT_OUTPUT = 0;
    int SLOT_BURN = 1;
    int INV_BEGIN = 2;
    int INV_END = 20; //Off the end!!
    
    /**
     * 获取该合成表一次合成消耗的热量
     */
    int getHeatConsumed();
    
    /**
     * 进行合成操作。注意如果是模拟操作，返回null代表不能合成，否则代表可以合成。
     * @param inv
     * @param isVirtual 是否模拟（不消耗物品栏）
     * @return 结果的stack
     */
    ItemStack doCrafting(ItemStack[] inv, boolean isVirtual);
    
    ItemStack[] getInputForDisplay();
    ItemStack getOutputForDisplay();
    
}
