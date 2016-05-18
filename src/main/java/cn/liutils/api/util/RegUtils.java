package cn.liutils.api.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Elegant and fast way to register specific kinds of items.
 * @author WeathFolD
 */
public class RegUtils {

    /**
     * Register a item instance of itemClass which have an empty constructor with key id and return its instance.
     */
    public static <T extends Item> T reg(Class<? extends T> itemClass, String id) {
        try {
            Item it = itemClass.getConstructor().newInstance();
            GameRegistry.registerItem(it, id);
            return (T) it; //Hah, casting done here
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Register n item instances of itemClass which should have a single-int-par Ctor with key id.
     * We do it by creating an array of size n and initialized the array each by Ctor(0),...,Ctor(n-1)
     * The registering result is returned.
     */
    public static <T extends Item> T[] reg(Class<? extends T> itemClass, int n, String id) {
        T[] res = (T[]) Array.newInstance(itemClass, n); //这个也是醉了
        try {
            for(int i = 0; i < n; ++i) {
                Item it = itemClass.getConstructor(Integer.TYPE).newInstance(i);
                GameRegistry.registerItem(it, id + i);
                res[i] = (T) it;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public static Field getObfField(Class cl, String normName, String obfName) throws NoSuchFieldException, SecurityException {
        Field f = null;
        try {
            f = cl.getDeclaredField(normName);
        } catch(Exception e) {}
        if(f == null)
            f = cl.getDeclaredField(obfName);
        f.setAccessible(true);
        return f;
    }

}
