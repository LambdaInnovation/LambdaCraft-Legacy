/**
 * Code by Lambda Innovation, 2013.
 */
package cn.liutils.api.client.render;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * A RenderIcon which repeatly plays what's inside icons array. the framerate can be set.
 * @author WeAthFolD
 */
public class RenderIconAnimated extends RenderIcon {

    private ResourceLocation[] icons;
    private int frameRate = 1; //tick/frame
    
    public RenderIconAnimated(ResourceLocation[] ics) {
        super(ics[0]);
        icons = ics;
    }
    
    public RenderIconAnimated(ResourceLocation[] ics, int fs) {
        this(ics);
        this.frameRate = fs;
    }
    
    @Override
    public void doRender(Entity entity, double par2, double par4,
            double par6, float par8, float par9) {
        int delta = (entity.ticksExisted * frameRate) % icons.length;
        delta /= frameRate;
        this.icon = icons[delta];
        super.doRender(entity, par2, par4, par6, par8, par9);
    }

}
