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
 * @author Administrator
 *
 */
public class ModelHandgun extends ModelBase implements IItemModel {
	
	  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape4;

	
	/**
	 * 
	 */
	public ModelHandgun() {
	    textureWidth = 64;
	    textureHeight = 32;
	    
	      Shape1 = new ModelRenderer(this, 7, 10);
	      Shape1.addBox(0F, 0F, 0F, 1, 1, 3);
	      Shape1.setRotationPoint(0F, 0F, 1F);
	      Shape1.setTextureSize(64, 32);
	      Shape1.mirror = true;
	      setRotation(Shape1, 0F, 0F, 0F);
	      Shape2 = new ModelRenderer(this, 15, 12);
	      Shape2.addBox(0F, 0F, 0F, 1, 1, 1);
	      Shape2.setRotationPoint(0F, 0.3F, 4F);
	      Shape2.setTextureSize(64, 32);
	      Shape2.mirror = true;
	      setRotation(Shape2, 0F, 0F, 0F);
	      Shape3 = new ModelRenderer(this, 19, 11);
	      Shape3.addBox(0F, 0F, 0F, 1, 1, 2);
	      Shape3.setRotationPoint(0F, 0F, 5F);
	      Shape3.setTextureSize(64, 32);
	      Shape3.mirror = true;
	      setRotation(Shape3, 0F, 0F, 0F);
	      Shape5 = new ModelRenderer(this, 23, 16);
	      Shape5.addBox(0F, 0F, 0F, 1, 3, 1);
	      Shape5.setRotationPoint(0F, 0.5F, 5F);
	      Shape5.setTextureSize(64, 32);
	      Shape5.mirror = true;
	      setRotation(Shape5, 0.3490659F, 0F, -0.0174533F);
	      Shape6 = new ModelRenderer(this, 15, 14);
	      Shape6.addBox(0F, 0F, 0F, 1, 1, 3);
	      Shape6.setRotationPoint(0.5F, 0F, 3.5F);
	      Shape6.setTextureSize(64, 32);
	      Shape6.mirror = true;
	      setRotation(Shape6, 0F, 0F, 0.7853982F);
	      Shape4 = new ModelRenderer(this, 18, 19);
	      Shape4.addBox(0F, 0F, 0F, 0, 1, 2);
	      Shape4.setRotationPoint(0.5F, 1F, 3.7F);
	      Shape4.setTextureSize(64, 32);
	      Shape4.mirror = true;
	      setRotation(Shape4, 0F, 0F, 0F);

	}
	
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	  
	  @Override
	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    Shape1.render(f5);
	    Shape2.render(f5);
	    Shape3.render(f5);
	    Shape5.render(f5);
	    Shape6.render(f5);
	    Shape4.render(f5);
	  }

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.client.IItemModel#render(net.minecraft.item.ItemStack, float, float)
	 */
	@Override
	public void render(ItemStack is, float f5, float f) {
	    Shape1.render(f5);
	    Shape2.render(f5);
	    Shape3.render(f5);
	    Shape5.render(f5);
	    Shape6.render(f5);
	    Shape4.render(f5);
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.client.IItemModel#setRotationAngles(net.minecraft.item.ItemStack, double, double, double, float)
	 */
	@Override
	public void setRotationAngles(ItemStack is, double posX, double posY,
			double posZ, float f) {
		
	}

}
