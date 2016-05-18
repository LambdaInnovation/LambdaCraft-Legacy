package cn.lambdacraft.deathmatch.entity;

import java.util.List;

import cn.weaponmod.api.WeaponHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDisplacerCannon extends EntityThrowable {

    private final int TELEPORT_RANGE = 5;
    private final int TELEPORT_OFFSET = 5;
    private EntityPlayer playerObj;
    private boolean Mode;
    
    public EntityDisplacerCannon(World par1World, EntityPlayer par2EntityPlayer, boolean Mode) {
        super(par1World, par2EntityPlayer);
        this.Mode = Mode; this.playerObj = par2EntityPlayer;
        System.out.print("New instance created with MODE ");
        System.out.println(this.Mode);
    }
    
    public EntityDisplacerCannon(World par1World, EntityPlayer par2EntityPlayer) {
        super(par1World, par2EntityPlayer);
    }

    @Override
    protected void onImpact(MovingObjectPosition var1) {
        /* If the entity itself attacked the player... */
        if(!Mode) {
            WeaponHelper.Explode(worldObj, this, 1.0F, 3.0F, posX, posY, posZ, 60);
            this.setDead();
        } else {
            teleportFromAndTo(this.playerObj, this);
        }
    }

    private void teleportFromAndTo(Entity From, Entity To) {
        int fromX = (int) From.posX; int fromY = (int) From.posY; int fromZ = (int) From.posZ;
        int toX = (int) To.posX; int toY = (int) To.posY; int toZ = (int) To.posZ;
        List<EntityLiving> lsFrom = worldObj.getEntitiesWithinAABB(Entity.class, 
                AxisAlignedBB.getBoundingBox(fromX - (TELEPORT_RANGE), 
                                             fromY - (TELEPORT_RANGE), 
                                             fromZ - (TELEPORT_RANGE), 
                                             fromX + (TELEPORT_RANGE), 
                                             fromY + (TELEPORT_RANGE), 
                                             fromZ + (TELEPORT_RANGE)).
                                             expand(TELEPORT_RANGE, TELEPORT_RANGE, TELEPORT_RANGE));

        for(int i=0; i<lsFrom.size(); i++) {
            try {
                int ti = 0;
                // Bad coding style, I admit.
                while(ti<=500) {
                    ti++;
                    EntityLivingBase toTeleport = lsFrom.get(i);
                    toX = (int) (posX + rand.nextInt() % TELEPORT_OFFSET);
                    toY = (int) (posY + rand.nextInt() % TELEPORT_OFFSET);
                    toZ = (int) (posZ + rand.nextInt() % TELEPORT_OFFSET);
                    if(!worldObj.blockExists(toX, toY, toZ)) {
                        toTeleport.setPosition(toX, toY, toZ); break;}
                }
            } catch(Exception ex) {
                // Do nothing; Just in debugging
            }
        }
    }

}
