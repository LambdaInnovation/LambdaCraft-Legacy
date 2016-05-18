package cn.lambdacraft.deathmatch.item.weapon;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.deathmatch.entity.EntityDisplacerCannon;
import cn.lambdacraft.deathmatch.item.ItemDisplacerCannon;
import cn.weaponmod.api.feature.IClickHandler;
import cn.weaponmod.api.weapon.WeaponGeneric;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Weapon_Displacer extends Item implements IClickHandler{
    
    private boolean currMode = false; // 'F' for fire, 'T' for teleport.
    
    public Weapon_Displacer() {
        super();
        setCreativeTab(CBCMod.cct);
        setUnlocalizedName("weapon_displacer");
    }
    
    @Override
    public void onItemClick(World world, EntityPlayer player, ItemStack stack, int keyid) {
        if(keyid == 1) // Change Mode
            currMode = !currMode;
        world.spawnEntityInWorld(new EntityDisplacerCannon(world, player, currMode));
    }

    @Override
    public void onItemRelease(World world, EntityPlayer pl, ItemStack stack,
            int keyid) {
        
    }

    @Override
    public void onItemUsingTick(World world, EntityPlayer player,
            ItemStack stack, int keyid, int ticks) {
        // TODO Auto-generated method stub
        
    }
    
    
    
}