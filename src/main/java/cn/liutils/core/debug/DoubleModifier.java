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

/**
 * @author WeAthFolD
 */
public abstract class DoubleModifier extends FieldModifier {

    public DoubleModifier(Field field) {
        super(field);
    }

    @Override
    public int getRequiredDimensions() {
        return 1;
    }

    @Override
    public void applyModification(Object instance, boolean dirFlag, int pressTick, int dimension) throws Exception
    {
        theField.setAccessible(true);
        theField.set(instance, (Double)theField.get(instance) + this.getDeltaDoubleLinear(dirFlag, pressTick));
    }
    
    @Override
    public void directSet(Object instance, String value) {
        try {
        Double d = (Double) theField.get(instance);
        d = Double.valueOf(value);
        } catch(Exception e) {}
    }

}
