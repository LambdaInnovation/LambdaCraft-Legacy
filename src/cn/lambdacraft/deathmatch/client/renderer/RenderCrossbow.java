package cn.lambdacraft.deathmatch.client.renderer;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.deathmatch.client.model.ModelCrossbow;
import cn.liutils.api.client.render.RenderModelItem;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.information.InformationBullet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class RenderCrossbow extends RenderModelItem {

	public RenderCrossbow() {
		super(new ModelCrossbow(), ClientProps.CROSSBOW_PATH);
		//教练用debugger实在是太方便了！
		setOffset(-0.004F, 0.316F, -0.454F);
		setRotation(0F, 173.95F, 0F);
		setScale(1.65F);
		this.setEquipOffset(0.906F, -0.364F, 0.058F);
		this.setInvRotation(-43.450F, -40.556F, 25.936F);
		this.setInvOffset(-2.45F, 3.8F);
		this.setInvScale(.78F);
		this.inventorySpin = false;
	}
	
	@Override
	protected float getModelAttribute(ItemStack item, EntityLivingBase entity) {
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			InformationBullet inf = (InformationBullet) WMInformation.getInformation(item, true);
			if(inf != null) {
				if(inf.isReloading)
					return 0.0F;
				if(inf.lastShooting_left && inf.getDeltaTick(true) < 15)
					return 1.0F;
			}
			return 2.0F;
		}
		return 2.0F;
	}

}
