/**
 * Code by Lambda Innovation, 2013.
 */
package cn.lambdacraft.deathmatch.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cn.liutils.api.util.GenericUtils;
import cn.liutils.api.util.Motion3D;

/**
 * 藤壶枪的辅助实体。发出触手、移动玩家、冲撞判断等行为在此进行。
 * TODO:测试和生命期控制
 * @author WeAthFolD
 *
 */
public class EntityTentacleGun extends Entity {
	
	public static final float MOVE_SPEED = 0.075F; //1.5Block/s
	
	private EntityPlayer player;
	
	//true：移动玩家 false：移动目标物体
	public boolean attachMode;
	
	//触手伸长的ticker
	public boolean isExtending = true;
	public int extendTick = 0;
	
	private double targX, targY, targZ;
	
	//CLIENT ONLY
	private int ticksUntilUpdate = 10;
	
	//SERVER ONLY
	private MovingObjectPosition target;

	public EntityTentacleGun(World world, EntityPlayer player, boolean mode) {
		this(world);
		this.player = player;
		attachMode = mode;
	}

	public EntityTentacleGun(World par1World) {
		super(par1World);
	}
	
	private boolean canAttach(MovingObjectPosition pos) {
		if(pos.typeOfHit == MovingObjectType.BLOCK)
			return attachMode;
		else {
			Entity e = pos.entityHit;
			if(attachMode) {
				return true;
			} else {
				return GenericUtils.getEntitySize(e) < 1.0F;
			}
		}
	}
	
	@Override
	public void onUpdate() {
		if(worldObj.isRemote) {
			//sync
			if(--ticksUntilUpdate <= 0) {
				ticksUntilUpdate = 10;
				isExtending = dataWatcher.getWatchableObjectByte(9) != 0;
				extendTick = dataWatcher.getWatchableObjectByte(10);
				
				if(!isExtending) {
					targX = dataWatcher.getWatchableObjectInt(11);
					targY = dataWatcher.getWatchableObjectInt(12);
					targZ = dataWatcher.getWatchableObjectInt(13);
				}
			}
			
		} else {
			
			//sync
			dataWatcher.updateObject(9, (byte) (isExtending ? 0 : 1));
			dataWatcher.updateObject(10, extendTick);
			
			
			this.setPosition(player.posX, player.posY, player.posZ);
			final float var3 = 1.0F;
			
			//motion is crucial for rendering
			this.motionX = -MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
					* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * var3;
			this.motionZ = MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * 
					MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * var3;
			this.motionY = -MathHelper.sin((player.rotationPitch) / 180.0F * (float) Math.PI) * var3;
			
			if(isExtending) {
				++extendTick;
				Motion3D motion = new Motion3D(player, true);
				Vec3 vec0 = motion.asVec3(worldObj), vec1 = motion.move(1.5D).asVec3(worldObj);
				MovingObjectPosition pos = worldObj.rayTraceBlocks(vec0, vec1);
				if(pos != null) {
					isExtending = false;
					if(canAttach(pos)) {
						target = pos;
					}
				}
			} else {
				if(target == null) {
					this.setDead();
					return;
				}
				
				//move entity
				Entity movedEntity = null;
				Vec3 dir = null;
				double tx, ty, tz;
				if(attachMode) {
					movedEntity = player;
					if(target.typeOfHit == MovingObjectType.BLOCK) {
						tx = target.blockX;
						ty = target.blockY;
						tz = target.blockZ;
					} else {
						tx = target.entityHit.posX;
						ty = target.entityHit.posY;
						tz = target.entityHit.posZ;
					}
				} else {
					movedEntity = target.entityHit;
					tx = player.posX;
					ty = player.posY;
					tz = player.posZ;
				}
				dir = worldObj.getWorldVec3Pool().getVecFromPool(
						tx - movedEntity.posX, ty - movedEntity.posY, tz - movedEntity.posZ);
				move(movedEntity, dir, MOVE_SPEED);
				
				if(target.typeOfHit == MovingObjectType.BLOCK) {
					targX = target.blockX;
					targY = target.blockY;
					targZ = target.blockZ;
				} else {
					targX = target.entityHit.posX;
					targY = target.entityHit.posY;
					targZ = target.entityHit.posZ;
				}
				
				dataWatcher.updateObject(11, targX);
				dataWatcher.updateObject(12, targY);
				dataWatcher.updateObject(13, targZ);
			}
		}
	}
	
	/**
	 * 将entity往dir方向移动dist距离。
	 * @param entity
	 * @param dir
	 * @param dist
	 */
	private void move(Entity entity, Vec3 dir, float dist) {
		double c = dir.xCoord * dir.xCoord + dir.yCoord * dir.yCoord + dir.zCoord * dir.zCoord;
		double dx = dist * (dir.xCoord / c), dy = dist * (dir.yCoord / c), dz = dist * (dir.zCoord / c);
		entity.posX += dx;
		entity.posY += dy;
		entity.posZ += dz;
		entity.isAirBorne = true;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(9, Byte.valueOf((byte) 0)); //sync isExtending
		this.dataWatcher.addObject(10, Byte.valueOf((byte)0)); //sync TentacleLength
		//target x.y.z
		dataWatcher.addObject(11, Integer.valueOf(0));
		dataWatcher.addObject(12, Integer.valueOf(0));
		dataWatcher.addObject(13, Integer.valueOf(0));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {}


	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) { }

}
