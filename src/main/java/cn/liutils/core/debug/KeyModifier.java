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

import cn.liutils.api.client.key.IKeyHandler;

/**
 * @author WeAthFolD
 *
 */
public class KeyModifier implements IKeyHandler {
    
    int dimension;
    boolean dir;

    /**
     * 
     */
    public KeyModifier(int dim, boolean d) {
        dimension = dim;
        dir = d;
    }

    /* (non-Javadoc)
     * @see cn.liutils.api.client.register.IKeyHandler#onKeyDown(int, boolean)
     */
    @Override
    public void onKeyDown(int keyCode, boolean tickEnd) {
    }

    /* (non-Javadoc)
     * @see cn.liutils.api.client.register.IKeyHandler#onKeyUp(int, boolean)
     */
    @Override
    public void onKeyUp(int keyCode, boolean tickEnd) {
        if(tickEnd)return;
        FieldModifierHandler.reset(dir, dimension);
    }

    /* (non-Javadoc)
     * @see cn.liutils.api.client.register.IKeyHandler#onKeyTick(int, boolean)
     */
    @Override
    public void onKeyTick(int keyCode, boolean tickEnd) {
        if(tickEnd)return;
        FieldModifierHandler.onKeyTick(dir, dimension);
    }

}
