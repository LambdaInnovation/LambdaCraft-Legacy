package cn.weaponmod.core.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cn.weaponmod.api.weapon.IZoomable;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * ClientTickHandler. Handles FOV uplifting action.
 */
public class UpliftHandler {
    
    public static final float DEFAULT_UPLIFT_RADIUS = 2.2F,
            DEFAULT_UPLIFT_SPEED = 1.2F, 
            DEFAULT_RECOVER_SPEED = .5F;
    
    EntityPlayer player;
    ItemStack lastStack;
    
    private float 
        angleToLift;

    public float totalAngle;
    
    public float 
            uplift_angle = DEFAULT_UPLIFT_RADIUS * .5F,
            uplift_speed = DEFAULT_UPLIFT_SPEED * .5F,
            recover_speed = DEFAULT_RECOVER_SPEED * .5F,
            max_uplift_angle = 4.3F * DEFAULT_UPLIFT_RADIUS;
    
    private boolean lastIsZooming = false, isZooming = false;
    
    public void doUplift() {
        doUplift(uplift_angle);
    }
    
    public void setProperties(float uplift_rad, float uplift_spd, float recover_spd, float max_uplift) {
        uplift_speed = uplift_spd * .5F;
        recover_speed = recover_spd * .5F;
        
        uplift_angle = uplift_rad;
        max_uplift_angle = max_uplift;
    }
    
    public void doUplift(float upliftRad) {
        if(angleToLift < 0)
            angleToLift = 0;
        angleToLift += upliftRad;
    }
    
     @SubscribeEvent
     @SideOnly(Side.CLIENT)
     public void onClientTick(ClientTickEvent event) {
         if(event.phase == Phase.START) {
              tickStart();
          } else {
              tickEnd();
          }
     }

    @SideOnly(Side.CLIENT)
    public void tickStart() {
        
        player = Minecraft.getMinecraft().thePlayer;
        if(player == null) return;
        
        if(lastStack != player.getCurrentEquippedItem()) {
            lastStack = player.getCurrentEquippedItem();
            clear();
            return;
        }
        
        processUplift(player);
        
        ItemStack is = lastStack;
        if(is != null) {
            Item item = is.getItem();
            if(item instanceof IZoomable && ((IZoomable) item).isItemZooming(is, player.worldObj, player))
                setZooming(true);
            else setZooming(false);
        } else setZooming(false);
        
        if(isZooming) {
            player.capabilities.setPlayerWalkSpeed(10000F);
        } else if(lastIsZooming) {
            player.capabilities.setPlayerWalkSpeed(.1F);
            lastIsZooming = false;
        }
    }
    
    public void clear() {
        angleToLift = totalAngle = 0F;
    }
    
    public void tickEnd() {
        if(player != null)
            processUplift(player);
    }
    
    private void processUplift(EntityPlayer player) {
        
        if(angleToLift > 0) {
            angleToLift -= uplift_speed;
            if(totalAngle < max_uplift_angle) {
                player.rotationPitch -= uplift_speed;
                totalAngle += uplift_speed;
            }
        } else if(totalAngle > 0) {
            player.rotationPitch += recover_speed;
            totalAngle -= recover_speed;
        } else totalAngle = 0F;
    }
    
    private void setZooming(boolean b) {
        lastIsZooming = isZooming;
        isZooming = b;
    }

}
