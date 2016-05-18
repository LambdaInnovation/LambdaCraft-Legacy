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
package cn.lambdacraft.crafting.recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeWeapons {
    /*
     * 加入规则：
     * index大的一定拥有index小的recipe
     */
    public static final int
        ID_NORMAL_CRAFTER = 0,
        ID_ADVANCED_CRAFTER = 1,
        ID_ELECTRIC_CRAFTER = 2;
    
    private static final String
        PAGE_DESCRIPTIONS[] = {
            "crafter.weapon",
            "crafter.ammo",
            "crafter.equipments"
        };
        
    private static List<MachineRecipes> machineList = new ArrayList<MachineRecipes>();
    static {
        machineList.add(new MachineRecipes(2, PAGE_DESCRIPTIONS));
        machineList.add(new MachineRecipes(2, PAGE_DESCRIPTIONS));
        machineList.add(new MachineRecipes(3, PAGE_DESCRIPTIONS));
    }
    
    public static void addRecipe(int mid, int pid, ICrafterRecipe... recipes) {
        for(int i = mid; i <= 2; ++i) {
            MachineRecipes machine = machineList.get(i);
            machine.insertRecipe(pid, recipes);
        }
    }
    
    public static MachineRecipes getMachineRecipes(int machineID) {
        return machineList.get(machineID);
    }
    
    public static String getPageDescription(int page) {
        return PAGE_DESCRIPTIONS[page];
    }
    
}
