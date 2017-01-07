package me.momocow.storagebank.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * StorageBank_ATM - MomoCow
 * Created using Tabula 4.1.1
 */
public class ModelATM extends ModelBase {
    public ModelRenderer HorizonBase;
    public ModelRenderer VerticalBase;
    public ModelRenderer screen;
    public ModelRenderer shape5;
    public ModelRenderer shape4;
    public ModelRenderer shape6;

    public ModelATM() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.shape4 = new ModelRenderer(this, 0, 56);
        this.shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape4.addBox(-8.0F, -1.0F, -6.9F, 16, 1, 1, 0.0F);
        this.HorizonBase = new ModelRenderer(this, 0, 0);
        this.HorizonBase.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.HorizonBase.addBox(-8.0F, 16.0F, -8.0F, 16, 6, 16, 0.0F);
        this.VerticalBase = new ModelRenderer(this, 0, 38);
        this.VerticalBase.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.VerticalBase.addBox(-8.0F, 6.0F, 0.0F, 16, 10, 8, 0.0F);
        this.shape6 = new ModelRenderer(this, 40, 46);
        this.shape6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape6.addBox(7.0F, -1.0F, -16.9F, 1, 1, 10, 0.0F);
        this.screen = new ModelRenderer(this, 0, 22);
        this.screen.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.screen.addBox(-8.0F, 0.0F, -16.9F, 16, 5, 11, 0.0F);
        this.setRotateAngle(screen, 1.4486232791552935F, 0.0F, 0.0F);
        this.shape5 = new ModelRenderer(this, 40, 46);
        this.shape5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape5.addBox(-8.0F, -1.0F, -16.9F, 1, 1, 10, 0.0F);
        this.screen.addChild(this.shape4);
        this.HorizonBase.addChild(this.VerticalBase);
        this.screen.addChild(this.shape6);
        this.VerticalBase.addChild(this.screen);
        this.screen.addChild(this.shape5);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.HorizonBase.render(f5);
    }
    
    public void render(float f5) { 
        this.HorizonBase.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
