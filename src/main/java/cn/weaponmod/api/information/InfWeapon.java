package cn.weaponmod.api.information;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cn.liutils.api.util.Pair;
import cn.weaponmod.api.action.Action;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Runtime-maintained EntityPlayer weapon information class.
 * Now also used for Item action executing.
 * Now player-based, operating only on EntityPlayer.
 * @author WeAthFolD
 */
public final class InfWeapon {
    
    /**
     * Additional data compound used to store multiple ticking data and others, automatically loaded.
     */
    public NBTTagCompound infData = new NBTTagCompound();
    
    /**
     * Currently active actions waiting to be exetuted in sequence.
     */
    private List<Pair<Action, Integer>> activeActions = new ArrayList();
    
    /**
     * Actions who always run, regardless of ticker and parallel judgements.
     */
    private List<Action> constantActions = new ArrayList<Action>();
    
    /**
     * Does the stack need to abort swing action now? Judged by current actions.
     */
    public boolean swingAbortion = true;
    
    /**
     * The ticker tracking last action executed.
     */
    public int lastActionTick = 0;
    
    /**
     * Main ticker.
     */
    public int ticksExisted = 0;
    
    /**
     * Currently active ticker channel. Usually is the ticker for last executed action.
     */
    public String activeTickChannel = "";

    public final EntityPlayer owner;
    
    private ItemStack lastStack;
    private int lastSlot;

    public InfWeapon(EntityPlayer player) {
        owner = player;
    }
    
    public ItemStack getLastStack() {
        return lastStack;
    }

    public void updateTick() {
        ++ticksExisted;
        //System.out.println("UpdateTick");
//        System.out.println(lastStack);
        ItemStack cs = owner.getCurrentEquippedItem();
        if(!unchanged()) {
            this.resetState();
        }
        if(cs == null)
            return;
        
        swingAbortion = true;
        for(int i = 0; i < activeActions.size(); i++) {
            Pair<Action, Integer> data = activeActions.get(i);
            Action act = data.first;
            
            if(act.needSwing()) swingAbortion = false; 
            
            if(--data.second > 0) {
                if(!act.onActionTick(owner.worldObj, owner, this)) { 
                    activeActions.remove(search(act));
                    --i;
                }
            } else {
                if(act.onActionEnd(owner.worldObj, owner, this, false)) setLastActionTick();
                activeActions.remove(search(act));
                --i;
            }
        }
        
        for(Action act : constantActions)
            act.onActionTick(owner.worldObj, owner, this);
    }
    
    /**
     * Attempt to execute a specified action.
     * @param player
     * @param act
     * @return whether the action has been executed successfully
     */
    @Deprecated
    public boolean executeAction(EntityPlayer player, Action act) {
        return executeAction(act);
    }
    
    public boolean executeAction(Action act) {
        if(act == null)
            return false;
        if(isActionPresent(act.name))
            return false;//this.removeAction(act.name);
        
        if(!act.isAvailable(owner.worldObj, owner, this))
            return false;
        
        if(act.maxTick == 0) {
            act.onActionBegin(owner.worldObj, owner, this);
            return true;
        }
        
        Iterator<Pair<Action, Integer>> it = activeActions.iterator();
        while(it.hasNext()) {
            Action act2 = it.next().first;
            boolean b = act2.getPriority() >= act.getPriority();
            
            if(b) {
                if(act2.doesConcurrent(act))
                    continue;
                return false;
            } else {
                if(act.doesConcurrent(act2))
                    continue;
                it.remove();
            }
        }
        if(act.onActionBegin(owner.worldObj, owner, this)) {
            setLastActionTick();
            activeActions.add(new Pair(act, act.maxTick));
            return true;
        }
        return false;
    }
    
    public boolean isActionPresent(String name) {
        for(Pair<Action, Integer> data : activeActions) {
            Action act = data.first;
            
            if(act.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public Action getAction(String name) {
        for(Pair<Action, Integer> data : activeActions) {
            Action act = data.first;
            
            if(act.name.equals(name)) {
                return act;
            }
        }
        return null;
    }
    
    public void removeAction(EntityPlayer player, Action act) {
        //O(n)
        activeActions.remove(search(act));
    }
    
    public boolean removeAction(String name) {
        Iterator< Pair<Action, Integer> > it = activeActions.iterator();
        boolean b = false;
        while(it.hasNext()) {
            Pair<Action, Integer> data = it.next();
            Action a = data.first;
            if(a.name.equals(name)) {
                b = true;
                a.onActionEnd(owner.worldObj, owner, this, true);
                it.remove();
            }
        }
        return b;
    }
    
    public int getTickLeft(Action act) {
        Pair<Action, Integer> data = search(act);
        if(data == null) {
            return -1;
        }
        return data.second;
    }
    
    @SideOnly(Side.CLIENT)
    public List<Action> getRenderActionList() {
        //TODO:Low efficiency, maybe need to fix?
        List<Action> list = new ArrayList(this.constantActions);
        
        for(Pair<Action, Integer> data : activeActions) {
            list.add(data.first);
        }
        
        //Remove the needless actions
        Iterator<Action> it = list.iterator();
        while(it.hasNext()) {
            Action a = it.next();
            if(a.getRenderPriority() == -1) 
                it.remove();
        }
        //System.out.println(list);
        
        Collections.sort(list, 
                new Comparator<Action>() {
                    //Compare by renderPriority
                    @Override
                    public int compare(Action a1, Action a2) {
                        int i1 = a1.getRenderPriority(),
                                i2 = a2.getRenderPriority();
                        return i1 > i2 ? 1 : 
                            i1 == i2 ? 0 : -1;
                    }
            
                }
        );
        
        return list;
    }
    
    private boolean unchanged() {
        ItemStack cs = owner.getCurrentEquippedItem();
        if(lastStack == null) return cs == null;
        if(cs == null) return false;
        return owner.inventory.currentItem == lastSlot && cs.getItem() == lastStack.getItem();
    }
    
    private Pair<Action, Integer> search(Action act) {
        for(Pair<Action, Integer> data : activeActions) {
            if(data.first == act)
                return data;
        }
        return null;
    }

    public void resetState() {
//        System.out.println("ResetState " + owner.worldObj.isRemote);
        infData = new NBTTagCompound();
        activeActions.clear();
        lastStack = owner.getCurrentEquippedItem();
        lastSlot = owner.inventory.currentItem;
        lastActionTick = ticksExisted = 0;
    }
    
    public void setLastActionTick() {
        lastActionTick = ticksExisted;
    }
    
    public int getLastActionTick() {
        return lastActionTick;
    }
    
    public int getTicksExisted() {
        return ticksExisted;
    }
    
    public void updateTicker(String channel) {
        infData.setInteger("tick_" + channel, ticksExisted);
    }
    
    public int getTicker(String channel) {
        return infData.getInteger("tick_" + channel);
    }

}
