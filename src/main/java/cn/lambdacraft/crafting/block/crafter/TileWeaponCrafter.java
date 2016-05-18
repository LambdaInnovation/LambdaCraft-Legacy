/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.crafting.block.crafter;

import net.minecraft.tileentity.TileEntityFurnace;
import cn.lambdacraft.crafting.recipe.RecipeWeapons;
import cn.lambdacraft.crafting.register.CBCBlocks;

/**
 * 武器合成机和高级武器合成机的TileEntity类。
 * @author WeAthFolD
 */
public class TileWeaponCrafter extends TileCrafterBase {
    
    public static final int 
        MAX_HEAT_NORMAL = 4000,
        MAX_HEAT_ADVANCED = 7000;

    public int burnTimeLeft,
            maxBurnTime;
    protected boolean isBurning;
    protected boolean isAdvanced;

    /**
     * inventory: 1-18：材料存储 19:燃料槽 20:合成结果槽
     * craftingStacks: 4*3 的合成表显示槽。
     */
    public TileWeaponCrafter() {}
    
    /**
     * 尝试消耗燃料，FFFFFFFFFFFFFFFF
     */
    public void tryBurn() {
        if (inventory[1] != null
                && TileEntityFurnace.getItemBurnTime(inventory[1]) > 0) {
            this.burnTimeLeft = TileEntityFurnace.getItemBurnTime(inventory[1]) / 2;
            this.maxBurnTime = this.burnTimeLeft;
            inventory[1].splitStack(1);
            if (inventory[1].stackSize <= 1)
                inventory[1] = null;
            isBurning = true;
            blockType.setLightLevel(0.4F);
        }
    }
    
    //------------------TICKUPDATE-------------------------
    @Override
    public void updateEntity() {
        super.updateEntity();

        //热量下降
        if (heat > 0)
            heat--;
        
        if(!worldObj.isRemote && isCrafting && !isBurning)
            tryBurn();

        //燃烧状态检查
        if (isBurning) {
            burnTimeLeft--;
            heat = Math.min(maxHeat, heat + 3);
            if (burnTimeLeft <= 0) {
                isBurning = false;
            }
        }
        
    }
    
    //---------------------MISC---------------------
    @Override
    public String getInventoryName() {
        return "lambdacraft:weaponcrafter";
    }
    
    @Override
    protected boolean onCrafterLoad() {
        if (this.getBlockType() == null)
            return false;
        isAdvanced = !(this.blockType == CBCBlocks.weaponCrafter);
        recipes = RecipeWeapons.getMachineRecipes(isAdvanced ? 1 : 0);
        this.maxHeat = isAdvanced ? MAX_HEAT_ADVANCED : MAX_HEAT_NORMAL;
        this.writeRecipeInfoToSlot();
        return true;
    }

}