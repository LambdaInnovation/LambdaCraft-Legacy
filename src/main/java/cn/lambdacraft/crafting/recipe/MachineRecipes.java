/**
 * 
 */
package cn.lambdacraft.crafting.recipe;

import java.util.ArrayList;
import java.util.List;

import cn.liutils.api.util.GenericUtils;

/**
 * 一类武器合成机的合成表列表。支持通过pageid+nid的快速查询以及插入。
 * @author WeathFolD
 */
public class MachineRecipes {

    List<ICrafterRecipe> recipeLists[];
    String pageDescriptions[];

    public MachineRecipes(int length, String[] pd) {
        recipeLists = new ArrayList[length];
        for(int i = 0; i < length; ++i)
            recipeLists[i] = new ArrayList<ICrafterRecipe>();
        
        pageDescriptions = pd;
    }
    
    public int getRecipeSize(int pid) {
        return recipeLists[pid].size();
    }
    
    public int getPageSize() {
        return recipeLists.length;
    }
    
    public int getMaxScrollFactor(int pageID) {
        return Math.max(0, getRecipeSize(pageID) - 3);
    }
    
    public void insertRecipe(int pid, ICrafterRecipe... recipes) {
        List<ICrafterRecipe> list = recipeLists[pid];
        for(ICrafterRecipe icr : recipes) {
            list.add(icr);
        }
    }
    
    public ICrafterRecipe queryRecipe(int pid, int nid) {
        if(pid >= recipeLists.length)
            return null;
        return GenericUtils.safeFetchFrom(recipeLists[pid], nid);
    }

}
