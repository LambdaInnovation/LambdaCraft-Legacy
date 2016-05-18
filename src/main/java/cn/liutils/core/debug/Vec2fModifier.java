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

import org.lwjgl.util.vector.Vector2f;

/**
 * @author WeAthFolD
 *
 */
public abstract class Vec2fModifier extends FieldModifier {

    /**
     * @param field
     */
    public Vec2fModifier(Field field) {
        super(field);
    }

    /* (non-Javadoc)
     * @see cn.liutils.core.debug.FieldModifier#getRequiredDimensions()
     */
    @Override
    public int getRequiredDimensions() {
        return 2;
    }
    
    @Override
    public void directSet(Object instance, String value) {
        String[] v1 = value.split(",");
        try {
        Vector2f vec = (Vector2f) theField.get(instance);

        vec.x = Float.valueOf(v1[0]);
        vec.y = Float.valueOf(v1[1]);
        } catch(Exception e) {}
    }

    /* (non-Javadoc)
     * @see cn.liutils.core.debug.FieldModifier#applyModification(java.lang.Object, boolean, int, int)
     */
    @Override
    public void applyModification(Object instance, boolean dirFlag,
            int pressTick, int dimension) throws Exception {
        Vector2f vec = (Vector2f) theField.get(instance);
        if(dimension == 0) {
            vec.x = (float) this.getDeltaDoubleLinear(dirFlag, pressTick);
        } else vec.y = (float) this.getDeltaDoubleLinear(dirFlag, pressTick);
    }

}
