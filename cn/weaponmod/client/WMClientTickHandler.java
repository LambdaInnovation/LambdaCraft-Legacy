/**
 * 
 */
package cn.weaponmod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cn.weaponmod.api.weapon.IZoomable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Administrator
 *
 */
@SideOnly(Side.CLIENT)
public class WMClientTickHandler {
	
	public final float DEFAULT_UPLIFT_RADIUS = 1.0F, UPLIFT_SPEED = 1.2F, RECOVER_SPEED = .5F;
	
	Minecraft mc = Minecraft.getMinecraft();
	EntityPlayer player;
	private float angleToLift = 0F;
	private float totalUpliftAngle = 0F;
	private boolean lastIsZooming = false, isZooming = false;
	
	public void setUplift() {
		setUplift(DEFAULT_UPLIFT_RADIUS);
	}
	
	public void setUplift(float upliftRad) {
		if(angleToLift < 0)
			angleToLift = 0;
		angleToLift += upliftRad;
	}

	public void tickStart() {
		player = mc.thePlayer;
		if(player != null) {
			if(angleToLift > 0) {
				angleToLift -= UPLIFT_SPEED;
				if(totalUpliftAngle < 4.3F * DEFAULT_UPLIFT_RADIUS) {
					player.rotationPitch -= UPLIFT_SPEED;
					totalUpliftAngle += UPLIFT_SPEED;
				}
			} else if(totalUpliftAngle > 0) {
				player.rotationPitch += RECOVER_SPEED;
				totalUpliftAngle -= RECOVER_SPEED;
			} else totalUpliftAngle = 0F;
			
			ItemStack is = player.getCurrentEquippedItem();
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
	}
	
	private void setZooming(boolean b) {
		lastIsZooming = isZooming;
		isZooming = b;
	}

}
