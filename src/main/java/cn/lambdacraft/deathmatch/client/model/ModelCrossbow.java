/**
 * 
 */
package cn.lambdacraft.deathmatch.client.model;

import cn.liutils.api.client.model.IItemModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * @author WeAthFolD, 擎兽
 * 
 */
public class ModelCrossbow extends ModelBase implements IItemModel {

    // fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
    ModelRenderer Shape10;
    ModelRenderer Shape11;
    ModelRenderer Shape12;
    ModelRenderer Shape13;
    ModelRenderer Shape14;
    ModelRenderer Shape15;

    public ModelCrossbow() {
        textureWidth = 64;
        textureHeight = 32;

        Shape1 = new ModelRenderer(this, 8, 16);
        Shape1.addBox(0F, 0F, 0F, 2, 2, 7);
        Shape1.setRotationPoint(0F, 0F, 0F);
        Shape1.setTextureSize(64, 32);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 0F, 0.7853982F);
        Shape2 = new ModelRenderer(this, 20, 10);
        Shape2.addBox(0F, 0F, 0F, 1, 2, 10);
        Shape2.setRotationPoint(-0.5F, 0F, -1.5F);
        Shape2.setTextureSize(64, 32);
        Shape2.mirror = true;
        setRotation(Shape2, 0F, 0F, 0F);
        Shape3 = new ModelRenderer(this, 0, 29);
        Shape3.addBox(0F, 0F, 0F, 4, 1, 0);
        Shape3.setRotationPoint(0F, 0.5F, -1F);
        Shape3.setTextureSize(64, 32);
        Shape3.mirror = true;
        setRotation(Shape3, 0F, -0.2617994F, 0F);
        Shape4 = new ModelRenderer(this, 0, 29);
        Shape4.addBox(-4F, 0F, 0F, 4, 1, 0);
        Shape4.setRotationPoint(0F, 0.5F, -1F);
        Shape4.setTextureSize(64, 32);
        Shape4.mirror = true;
        setRotation(Shape4, 0F, 0.2617994F, 0F);
        
        Shape5 = new ModelRenderer(this, 0, 30);
        Shape5.addBox(0F, 0F, 0F, 4, 1, 0);
        Shape5.setRotationPoint(3.8F, 0.5F, 0F);
        Shape5.setTextureSize(64, 32);
        Shape5.mirror = true;
        setRotation(Shape5, 0F, -0.5235988F, 0F);
        Shape5.mirror = false;
        
        Shape6 = new ModelRenderer(this, 0, 30);
        Shape6.addBox(-5F, 0F, 0F, 4, 1, 0);
        Shape6.setRotationPoint(-2.9F, 0.5F, -0.5F);
        Shape6.setTextureSize(64, 32);
        Shape6.mirror = true;
        setRotation(Shape6, 0F, 0.5235988F, 0F);
        
        Shape7 = new ModelRenderer(this, 34, 14);
        Shape7.addBox(0F, 0F, -3F, 1, 1, 9);
        Shape7.setRotationPoint(0F, 0.5F, 8F);
        Shape7.setTextureSize(64, 32);
        Shape7.mirror = true;
        setRotation(Shape7, -0.6283185F, 0.4363323F, 0.6981317F);
        
        Shape8 = new ModelRenderer(this, 46, 14);
        Shape8.addBox(0F, 0F, 0F, 1, 2, 6);
        Shape8.setRotationPoint(-0.5F, 2F, 9.5F);
        Shape8.setTextureSize(64, 32);
        Shape8.mirror = true;
        setRotation(Shape8, 0.1396263F, 0F, 0F);
        
        Shape9 = new ModelRenderer(this, 43, 8);
        Shape9.addBox(0F, 0F, 0F, 1, 1, 4);
        Shape9.setRotationPoint(0F, -1F, 4F);
        Shape9.setTextureSize(64, 32);
        Shape9.mirror = true;
        setRotation(Shape9, 0F, 0F, 0.7853982F);
        
        Shape10 = new ModelRenderer(this, 27, 23);
        Shape10.addBox(0F, 0F, 0F, 1, 5, 2);
        Shape10.setRotationPoint(-0.5F, 1F, 3.5F);
        Shape10.setTextureSize(64, 32);
        Shape10.mirror = true;
        setRotation(Shape10, -0.2617994F, 0F, 0F);
        
        Shape11 = new ModelRenderer(this, 0, 31);
        Shape11.addBox(-0.5F, 0F, 0F, 9, 0, 1);
        Shape11.setRotationPoint(7F, 1F, 1.3F);
        Shape11.setTextureSize(64, 32);
        Shape11.mirror = true;
        setRotation(Shape11, 3.141593F, 3.141593F, -0.0872665F);
        
        Shape12 = new ModelRenderer(this, 0, 31);
        Shape12.addBox(0F, 0F, 0F, 9, 0, 1);
        Shape12.setRotationPoint(-7.5F, 1F, 1.3F);
        Shape12.setTextureSize(64, 32);
        Shape12.mirror = true;
        setRotation(Shape12, 0F, 0.0174533F, -0.0872665F);
        
        Shape13 = new ModelRenderer(this, 13, 6);
        Shape13.addBox(0F, 0F, 0F, 0, 1, 8);
        Shape13.setRotationPoint(0F, -0.2F, -3F);
        Shape13.setTextureSize(64, 32);
        Shape13.mirror = true;
        setRotation(Shape13, 0F, 0F, 0F);
        
        Shape14 = new ModelRenderer(this, 0, 24);
        Shape14.addBox(0F, 0F, 0F, 0, 2, 2);
        Shape14.setRotationPoint(0F, 1.5F, 7F);
        Shape14.setTextureSize(64, 32);
        Shape14.mirror = true;
        setRotation(Shape14, -0.2617994F, 0F, 0F);
        
        Shape15 = new ModelRenderer(this, 13, 6);
        Shape15.addBox(0F, 0F, 0F, 0, 1, 8);
        Shape15.setRotationPoint(0.5F, 0.3F, -3F);
        Shape15.setTextureSize(64, 32);
        Shape15.mirror = true;
        setRotation(Shape15, 0F, 0F, 1.570796F);
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
        Shape9.render(f5);
        Shape10.render(f5);
        Shape11.render(f5);
        Shape12.render(f5);
        Shape13.render(f5);
        Shape14.render(f5);
        Shape15.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    //f : 指示当前状态。1.0F：射击后。 2.0F：准备好射击。 0.0F：上弹中desu~
    public void render(ItemStack is, float f5, float f) {
        setRotationAngles(is, 0, 0, 0, f);
        Shape1.render(f5);
        Shape2.render(f5);
        Shape3.render(f5);
        Shape4.render(f5);
        Shape5.render(f5);
        Shape6.render(f5);
        Shape7.render(f5);
        Shape8.render(f5);
        Shape9.render(f5);
        if(f > 0F)
            Shape10.render(f5);
        Shape11.render(f5);
        Shape12.render(f5);
        
        Shape14.render(f5);
        
        if(f == 2.0F) {
            Shape13.render(f5);
            Shape15.render(f5);
        }
    }
    
    //只渲染弹夹。
    public void renderMagazine(ItemStack is, float f5) {
        Shape10.render(f5);
    }

    @Override
    public void setRotationAngles(ItemStack is, double posX, double posY,
            double posZ, float f) {
        if(f == 0.0F || f == 1.0F) {
            Shape3.setRotationPoint(0F, 0.5F, -1F);
            setRotation(Shape3, 0F, -0.2617994F, 0F);
            
            Shape4.setRotationPoint(0F, 0.5F, -1F);
            setRotation(Shape4, 0F, 0.2617994F, 0F);

            Shape5.setRotationPoint(3.8F, 0.5F, 0F);
            setRotation(Shape5, 0F, -0.5235988F, 0F);
            
            Shape6.setRotationPoint(-2.9F, 0.5F, -0.5F);
            setRotation(Shape6, 0F, 0.5235988F, 0F);

            Shape11.setRotationPoint(7F, 1F, 1.3F);
            setRotation(Shape11, 3.141593F, 3.141593F, -0.0872665F);
            
            Shape12.setRotationPoint(-7.5F, 1F, 1.3F);
            setRotation(Shape12, 0F, 0.0174533F, -0.0872665F);
        } else {
              Shape3.setRotationPoint(0F, 0.5F, -1F);
              setRotation(Shape3, 0F, -0.4014257F, 0F);
              
              Shape4.setRotationPoint(0F, 0.5F, -1F);
              setRotation(Shape4, 0F, 0.4014257F, 0F);

              Shape5.setRotationPoint(3.5F, 0.5F, 0.4F);
              setRotation(Shape5, 0F, -0.7330383F, 0F);
              
              Shape6.setRotationPoint(-3.4F, 0.5F, 0.4F);
              setRotation(Shape6, 0F, 0.7330383F, 0F);
              
              Shape11.setRotationPoint(6F, 1F, 2.1F);
              setRotation(Shape11, 3.141593F, -2.426008F, -0.0872665F);
              
              Shape12.setRotationPoint(-6.5F, 1F, 2F);
              setRotation(Shape12, 0F, -0.6981317F, -0.0872665F);
        }
    }

}
