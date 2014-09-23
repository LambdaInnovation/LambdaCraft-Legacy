package cn.lambdacraft.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import cn.lambdacraft.core.item.ElectricArmor;
import cn.lambdacraft.deathmatch.item.ArmorHEV;
import cn.lambdacraft.deathmatch.item.ArmorHEV.EnumAttachment;
import cn.lambdacraft.deathmatch.register.DMItems;
//import cn.lambdacraft.terrain.ModuleTerrain;
import cn.liutils.api.util.GenericUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LCClientPlayer {

	public static final float LJ_VEL_RADIUS = 1.5F, BHOP_VEL_SCALE = 0.003F, SPEED_REDUCE_SCALE = 0.0005F;
	private float lastTickRotationYaw;
	private GameSettings gameSettings;
	
	@SideOnly(Side.CLIENT)
	private Minecraft mc = Minecraft.getMinecraft();
	private EntityPlayer player = mc.thePlayer;
	private int fallingTick;
	private int tickSinceLastSound = 0;
	private float lastHealth = 0;
	private boolean lastAirBorne = false;
	public static boolean drawArmorTip = false;
	public static boolean armorStat[] = new boolean[4];
	
	ArmorHEV hevHead = DMItems.armorHEVHelmet, hevChest = DMItems.armorHEVChestplate,
			hevBoots = DMItems.armorHEVBoot, hevLeggings = DMItems.armorHEVLeggings;
	
	public LCClientPlayer() {
		gameSettings = Minecraft.getMinecraft().gameSettings;
	}


	//---------------通用支持部分------------------
	public void beforeOnUpdate() {
		
		boolean preOnHEV = armorStat[2] &&  armorStat[3];
		
		//Update Armor Status
		for(int i = 0; i < 4; i ++) {
			boolean b = player.inventory.armorInventory[i] != null && player.inventory.armorInventory[i].getItem() instanceof ArmorHEV;
			if(b) {
				if(ElectricArmor.getItemCharge(player.inventory.armorInventory[i]) <= 0) 
					b = false;
			}
			armorStat[i] = b;
		}
		
		if(armorStat[3]) {
			drawArmorTip = ArmorHEV.hasAttach(player.inventory.armorInventory[3], EnumAttachment.WPNMANAGE);
		} else drawArmorTip = false;
		
		//putting HEV on at this tick
		if(!preOnHEV && (armorStat[2] && armorStat[3])) {
			//TODO：客户端声音播放需要修复
			//mc.getSoundHandler().playSoundFX("lambdacraft:hev.hev_logon", 0.5F, 1.0F);
		} else if(preOnHEV && !(armorStat[2] && armorStat[3])) { //HEV 'Broke down' because player action or energy critical
			if(player.inventory.armorInventory[2] != null && player.inventory.armorInventory[3] != null) {
				//mc.sndManager.playSoundFX("lambdacraft:hev.hev_shutdown", 0.5F, 1.0F);
			}
		}
		
		/*
		if(player.worldObj.provider.dimensionId == ModuleTerrain.xenIslandDimensionID 
				|| player.worldObj.provider.dimensionId == ModuleTerrain.xenContinentDimensionID) {
			if(!player.onGround && !player.capabilities.isFlying && !(player.isOnLadder() || player.isInWater())) {
				player.motionY += 0.036;
			}
		}*/
	}
	
	public void afterOnUpdate() {
		++tickSinceLastSound;
		if((armorStat[2] &&  armorStat[3])) {
			if(player.getHealth() - lastHealth < 0 && player.getHealth() <= 5) {
				if(tickSinceLastSound > 30) {
					//mc.sndManager.playSoundFX("lambdacraft:hev.health_critical", 0.5F, 1.0F);
					tickSinceLastSound = 0;
				}
			}
		}
		lastTickRotationYaw = player.rotationYaw;
		lastHealth = player.getHealth();
	}
	
	
	//---------------LongJump-----------------------
	/**
	 * 自建跳跃，长跳包支持。
	 */
	public void jump() {
		
		ItemStack slotChestplate = player.inventory.armorInventory[2];
		if (slotChestplate != null && player.isSneaking()) {
			Item item = slotChestplate.getItem();
			if (item instanceof ArmorHEV) {
				ArmorHEV hev = (ArmorHEV) item;
				if (ArmorHEV.hasAttach(slotChestplate, EnumAttachment.LONGJUMP))
					if (hev.getManager(slotChestplate).discharge(slotChestplate, 200, 2, true, false) == 200) {
						player.motionX = -MathHelper.sin(player.rotationYaw / 180.0F
								* (float) Math.PI)
								* MathHelper.cos(player.rotationPitch / 180.0F
										* (float) Math.PI) * LJ_VEL_RADIUS;
						player.motionZ = MathHelper.cos(player.rotationYaw / 180.0F
								* (float) Math.PI)
								* MathHelper.cos(player.rotationPitch / 180.0F
										* (float) Math.PI) * LJ_VEL_RADIUS;
					}
			}
		}
	}
	
	//--------------------Bhop-------------------
	/**
	 * Update函数，连跳支持，Xen支持。
	 */
	@SideOnly(Side.CLIENT)
	public void onLivingUpdate() {
		
		if(!useBhop()) {
			return;
		}
		
		//calculate and strafe!
		EntityPlayerSP player = (EntityPlayerSP) this.player;
		double velToAdd = player.movementInput.moveStrafe * (1.0 - Math.abs(player.movementInput.moveForward));
		float changedYaw = player.rotationYaw - lastTickRotationYaw;
		if(Math.abs(changedYaw) > 10) {
			changedYaw = changedYaw > 0 ? 10 : -10;
		}
		velToAdd *= -changedYaw;
		if(!player.onGround) {
			fallingTick = -1;
		} else ++fallingTick;
		
		if(velToAdd == 0.0 || velToAdd == -0.0) {
			return;
		}
		//加速
		player.motionX += -MathHelper.sin(player.rotationYaw/ 180.0F
					* (float) Math.PI)
					* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI)
					* velToAdd * BHOP_VEL_SCALE;
		player.motionZ += MathHelper.cos(player.rotationYaw/ 180.0F
					* (float) Math.PI)
					* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI)
					* velToAdd * BHOP_VEL_SCALE;
		//变向
		double vel = Math.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ);
		float motionYaw = getMotionRotationYaw();
		changedYaw = GenericUtils.wrapYawAngle(player.rotationYaw - lastTickRotationYaw);
		if(changedYaw > 20)
			changedYaw = changedYaw > 0 ? 20 : -20;
		motionYaw -= changedYaw;
		vel -= changedYaw * SPEED_REDUCE_SCALE;
		motionYaw = GenericUtils.wrapYawAngle(motionYaw);
		player.motionX = MathHelper.sin(motionYaw * (float) Math.PI / 180.0F) * vel;
		player.motionZ = MathHelper.cos(motionYaw / 180.0F * (float) Math.PI) * vel;
	}
	
	/*
	@Override
    public void moveEntityWithHeading(float var1, float var2)
    {
		double preMotionZ = player.motionZ, preMotionX = player.motionX;
		player.localMoveEntityWithHeading(var1, var2);
    	if(useBhop() && fallingTick < 2 && !player.isCollidedHorizontally) {
    		double speedReduction = player.onGround? 0.51 : 0.98;
    		player.motionX = preMotionX * speedReduction;
    		player.motionZ = preMotionZ * speedReduction;
    	}
    }
	*/
	
	//-----------------------效用函数----------------------------
	
	private boolean useBhop() {
		return armorStat[1] && ArmorHEV.hasAttach(player.getCurrentArmor(1), EnumAttachment.BHOP) && 
				!(player.handleLavaMovement() || player.handleWaterMovement() || player.isOnLadder() || player.capabilities.isCreativeMode);
	}
	
	private float getMotionRotationYaw() {
		double par1 = player.motionX, par3 = player.motionY, par5 = player.motionZ;
		float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5
				* par5);
		par1 /= f2;
		par3 /= f2;
		par5 /= f2;
		return (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
	}
	
	public void tickStart() {
		player = Minecraft.getMinecraft().thePlayer;
		if(player != null) {
			beforeOnUpdate();
			if(player.worldObj.isRemote)
				onLivingUpdate();
			if(!lastAirBorne && !player.onGround) {
				jump();
			}
			lastAirBorne = !player.onGround;
		}
	}

	public void tickEnd() {
		if(player != null)
			afterOnUpdate();
	}
	
	//----------------Sounds-----------------
	/*
	@Override
	public void beforeAttackEntityFrom(DamageSource var1, int var2) {
		String s = null;
		if(var1 == DamageSource.fall) {
			if(var2 >= 13)
			s = "cbc.hev.major_fracture";
		} else if(var1 == DamageSource.inFire) {
			s = "cbc.hev.heat_damage";
		} else if(var1.getEntity() != null && var2 > 5) {
			s = "cbc.hev.blood_loss";
		}
		if(s != null && player.ticksExisted - lastSoundTick > 50 ) {
			mc.sndManager.playSoundFX(s, 0.5F, 1.0F);
		}
		System.out.println("Me getting called! " + var1 + " " + var2);
		lastHealth = player.getHealth();
	}
	
	@Override
	public void afterAttackEntityFrom(DamageSource var1, int var2) {
		if(player.getHealth() <= 5 && lastHealth > 5) {
			mc.sndManager.playSoundFX("cbc.hev.health_critical", 0.5F, 1.0F);
		}
	}
	*/

}
