/**
 * Code by Lambda Innovation, 2013.
 */
package cn.lambdacraft.deathmatch.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @author WeAthFolD
 * 
 */
public class ModelEgonBackpack extends ModelBase {

	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8;

	/**
	 * 
	 */
	public ModelEgonBackpack() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 37, 10);
		Shape1.addBox(0F, 0F, 0F, 2, 7, 2);
		Shape1.setRotationPoint(-2.5F, 1.5F, 1F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0.7853982F, 0F);
		Shape2 = new ModelRenderer(this, 37, 10);
		Shape2.addBox(0F, 0F, 0F, 2, 7, 2);
		Shape2.setRotationPoint(0.5F, 1.5F, 1F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0.7853982F, 0F);
		Shape3 = new ModelRenderer(this, 20, 10);
		Shape3.addBox(0F, 0F, 0F, 6, 7, 2);
		Shape3.setRotationPoint(-2.5F, 1.2F, -2F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0.0174533F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 23, 1);
		Shape4.addBox(0F, 0F, 0F, 3, 6, 2);
		Shape4.setRotationPoint(-1F, 2F, -1F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0.2268928F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 15, 7);
		Shape5.addBox(0F, 0F, 0F, 1, 2, 1);
		Shape5.setRotationPoint(-2F, 2F, -1F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, -0.5410521F, 0.0174533F, 0.8901179F);
		Shape6 = new ModelRenderer(this, 15, 7);
		Shape6.addBox(0F, 0F, 0F, 1, 2, 1);
		Shape6.setRotationPoint(-3.8F, 3.2F, -1.5F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, -0.6981317F, 0.4363323F, -0.2268928F);
		Shape7 = new ModelRenderer(this, 13, 11);
		Shape7.addBox(0F, 0F, 0F, 1, 6, 2);
		Shape7.setRotationPoint(-1.5F, 1.5F, -3.8F);
		Shape7.setTextureSize(64, 32);
		Shape7.mirror = true;
		setRotation(Shape7, 0.0523599F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 13, 11);
		Shape8.addBox(0F, 0F, 0F, 1, 6, 2);
		Shape8.setRotationPoint(1.5F, 1.5F, -3.8F);
		Shape8.setTextureSize(64, 32);
		Shape8.mirror = true;
		setRotation(Shape8, 0.0523599F, 0F, 0F);

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
		Shape8.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
