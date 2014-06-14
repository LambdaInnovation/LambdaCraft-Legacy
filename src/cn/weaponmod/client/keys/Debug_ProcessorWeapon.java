/**
 * 
 */
package cn.weaponmod.client.keys;

import net.minecraftforge.client.IItemRenderer;
import cn.liutils.api.debug.Debug_ProcessorModel;
import cn.liutils.api.debug.KeyMoving.EnumKey;
import cn.weaponmod.api.client.render.RenderModelBulletWeapon;

/**
 * @author WeAthFolD
 *
 */
public class Debug_ProcessorWeapon extends Debug_ProcessorModel<RenderModelBulletWeapon> {

	@Override
	public boolean isProcessAvailable(IItemRenderer render, int mode) {
		return render instanceof RenderModelBulletWeapon;
	}
	
	@Override
	public String doProcess(RenderModelBulletWeapon render, EnumKey key, int mode,
			int ticks) {
		String s = super.doProcess(render, key, mode, ticks);
		float amtToAdd = ticks * ticks * 0.002F;
		if(mode == 10) {
			s = onSetMuzzleOffset(render, key, amtToAdd);
			System.out.println(s);
		}
		return s;
	}
	
	private String onSetMuzzleOffset(RenderModelBulletWeapon render, EnumKey key, float factor) {
		switch (key) {
		case UP:
			render.ty += factor;
			break;
		case DOWN:
			render.ty -= factor;
			break;
		case LEFT:
			render.tx -= factor;
			break;
		case RIGHT:
			render.tx += factor;
			break;
		case FORWARD:
			render.tz += factor;
			break;
		case BACK:
			render.tz -= factor;
			break;
		}
		return "[MUZZLE OFFSET : (" + render.tx + ", " + render.ty + ", " + render.tz + ") ]";
	}
	
	@Override
	public String getDescription(int mode) {
		if(mode < 10)
			return super.getDescription(mode);
		else if(mode == 10)
			return "Muzzflash offset XYZ";
		else return null;
	}

}
