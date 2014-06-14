package cn.lambdacraft.deathmatch.flashlight;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.world.EnumSkyBlock;

public class LightValue
{
    private static Set list = new HashSet();
    private static Set list2 = new HashSet();

    public static void addData(int var0, int var1, int var2)
    {
        list.add(new LightValuePoint(var0, var1, var2));
        
    }

    public static void addData2(int var0, int var1, int var2)
    {
        list2.add(new LightValuePoint(var0, var1, var2));
    }

    public static void resetAll()
    {
        if (list.size() != 0)
        {
            Iterator var0 = list.iterator();

            while (var0.hasNext())
            {
                LightValuePoint var1 = (LightValuePoint)var0.next();
                Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var1.x, var1.y, var1.z);
            }
        }
    }

    public static void resetAll2()
    {
        if (list2.size() != 0)
        {
            Iterator var0 = list2.iterator();

            while (var0.hasNext())
            {
                LightValuePoint var1 = (LightValuePoint)var0.next();
                Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var1.x, var1.y, var1.z);
            }
        }
    }

    public static void emptyData()
    {
        list.clear();
    }

    public static void emptyData2()
    {
        list2.clear();
    }
}
