/**
 * Copyright (C) Lambda-Innovation, 2013-2014
 * This code is open-source. Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 */
package cn.liutils.core.debug;

import java.lang.reflect.Field;

import net.minecraft.util.Vec3;

/**
 * @author WeAthFolD
 *
 */
public abstract class Vec3Modifier extends FieldModifier {

    /**
     * @param field
     */
    public Vec3Modifier(Field field) {
        super(field);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see cn.liutils.core.debug.NumberFieldModifier#getRequiredDimensions()
     */
    @Override
    public int getRequiredDimensions() {
        return 3;
    }
    
    @Override
    public void directSet(Object instance, String value) {
        String[] v1 = value.split(",");
        try {
        Vec3 vec = (Vec3) theField.get(instance);

        vec.xCoord = Double.valueOf(v1[0]);
        vec.yCoord = Double.valueOf(v1[1]);
        vec.xCoord = Double.valueOf(v1[2]);
        } catch(Exception e) {}
    }

    /* (non-Javadoc)
     * @see cn.liutils.core.debug.NumberFieldModifier#applyModification(boolean, int)
     */
    @Override
    public void applyModification(Object instance, boolean dirFlag, int pressTick, int dimension)
            throws Exception {
        Vec3 vec = (Vec3) theField.get(instance);
        double amount = this.getDeltaDoubleLinear(dirFlag, pressTick);
        if(dimension == 0) {
            vec.xCoord += amount;
        } else if(dimension == 1) {
            vec.yCoord += amount;
        } else vec.zCoord += amount;
    }

}
