/**
 * 
 */
package cn.lambdacraft.deathmatch.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import cn.liutils.api.client.model.IItemModel;

/**
 * @author WeAthFolD
 *
 */
public class ModelEgonHead extends ModelBase implements IItemModel {
	
	  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
	/**
	 * 
	 */
	public ModelEgonHead() {
		  textureWidth = 64;
		    textureHeight = 32;
		    
		      Shape1 = new ModelRenderer(this, 23, 13);
		      Shape1.addBox(0F, 0F, 0F, 2, 2, 3);
		      Shape1.setRotationPoint(0F, 0F, 0F);
		      Shape1.setTextureSize(64, 32);
		      Shape1.mirror = true;
		      setRotation(Shape1, 0F, 0F, 0.4363323F);
		      Shape2 = new ModelRenderer(this, 34, 14);
		      Shape2.addBox(0F, 0F, 0F, 1, 1, 2);
		      Shape2.setRotationPoint(0.2F, 0.6F, 2.5F);
		      Shape2.setTextureSize(64, 32);
		      Shape2.mirror = true;
		      setRotation(Shape2, 0F, 0F, 0.4363323F);
		      Shape3 = new ModelRenderer(this, 25, 20);
		      Shape3.addBox(0F, 0F, 0F, 3, 1, 0);
		      Shape3.setRotationPoint(1F, 1F, 2.5F);
		      Shape3.setTextureSize(64, 32);
		      Shape3.mirror = true;
		      setRotation(Shape3, 0F, 0F, 0F);
		      Shape4 = new ModelRenderer(this, 26, 10);
		      Shape4.addBox(0F, 0F, 0F, 1, 2, 0);
		      Shape4.setRotationPoint(0F, -1F, 2.5F);
		      Shape4.setTextureSize(64, 32);
		      Shape4.mirror = true;
		      setRotation(Shape4, 0F, 0F, 0F);
		      Shape5 = new ModelRenderer(this, 16, 11);
		      Shape5.addBox(-0.5F, 0F, 0F, 2, 1, 1);
		      Shape5.setRotationPoint(-1F, 0.2F, 1.6F);
		      Shape5.setTextureSize(64, 32);
		      Shape5.mirror = true;
		      setRotation(Shape5, 0F, 0F, 0.1745329F);
		      Shape6 = new ModelRenderer(this, 41, 14);
		      Shape6.addBox(0F, 0F, 0F, 2, 2, 1);
		      Shape6.setRotationPoint(0F, 0F, 4F);
		      Shape6.setTextureSize(64, 32);
		      Shape6.mirror = true;
		      setRotation(Shape6, -0.1745329F, 0F, 0.4363323F);
		      Shape7 = new ModelRenderer(this, 34, 20);
		      Shape7.addBox(0F, 0F, 0F, 1, 1, 2);
		      Shape7.setRotationPoint(0F, 1F, 4.7F);
		      Shape7.setTextureSize(64, 32);
		      Shape7.mirror = true;
		      setRotation(Shape7, -0.4886922F, 0F, 0.1745329F);
		      Shape8 = new ModelRenderer(this, 34, 20);
		      Shape8.addBox(0F, 0F, 0F, 1, 1, 2);
		      Shape8.setRotationPoint(0F, 1.8F, 6F);
		      Shape8.setTextureSize(64, 32);
		      Shape8.mirror = true;
		      setRotation(Shape8, -0.0174533F, 0F, 0.4363323F);
		      Shape9 = new ModelRenderer(this, 14, 14);
		      Shape9.addBox(0F, 0F, 0F, 1, 1, 3);
		      Shape9.setRotationPoint(0.2F, 0.6F, -0.2F);
		      Shape9.setTextureSize(64, 32);
		      Shape9.mirror = true;
		      setRotation(Shape9, 0F, 0F, 0.4363323F);

	}
	
	  @Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    Shape1.render(f5);
	    Shape2.render(f5);
	    Shape3.render(f5);
	    Shape4.render(f5);
	    Shape5.render(f5);
	    Shape6.render(f5);
	    Shape7.render(f5);
	    Shape8.render(f5);
	    Shape9.render(f5);
	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.weaponmod.api.client.IItemModel#render(net.minecraft.item.ItemStack,
	 * float, float)
	 */
	@Override
	public void render(ItemStack is, float f5, float f) {
		Shape1.render(f5);
	    Shape2.render(f5);
	    Shape3.render(f5);
	    Shape4.render(f5);
	    Shape5.render(f5);
	    Shape6.render(f5);
	    Shape7.render(f5);
	    Shape8.render(f5);
	    Shape9.render(f5);
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.client.IItemModel#setRotationAngles(net.minecraft.item.ItemStack, double, double, double, float)
	 */
	@Override
	public void setRotationAngles(ItemStack is, double posX, double posY,
			double posZ, float f) {

	}

}
