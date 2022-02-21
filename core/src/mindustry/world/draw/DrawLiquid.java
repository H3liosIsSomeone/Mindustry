package mindustry.world.draw;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.production.*;

public class DrawLiquid extends DrawBlock{
    public @Nullable Liquid liquidDrawn;
    public TextureRegion inLiquid, liquid, top;
    public boolean useOutputSprite = false;

    public DrawLiquid(){
    }

    public DrawLiquid(boolean useOutputSprite){
        this.useOutputSprite = useOutputSprite;
    }

    @Override
    public void draw(Building build){
        Draw.rect(build.block.region, build.x, build.y);
        GenericCrafter type = (GenericCrafter)build.block;

        if((inLiquid.found() || useOutputSprite) && liquidDrawn != null){
            Drawf.liquid(useOutputSprite ? liquid : inLiquid, build.x, build.y,
                build.liquids.get(liquidDrawn) / type.liquidCapacity,
                liquidDrawn.color
            );
        }

        if(type.outputLiquid != null && build.liquids.get(type.outputLiquid.liquid) > 0){
            Drawf.liquid(liquid, build.x, build.y,
                build.liquids.get(type.outputLiquid.liquid) / type.liquidCapacity,
                type.outputLiquid.liquid.color
            );
        }

        if(top.found()) Draw.rect(top, build.x, build.y);
    }

    @Override
    public void load(Block block){
        expectCrafter(block);

        top = Core.atlas.find(block.name + "-top");
        liquid = Core.atlas.find(block.name + "-liquid");
        inLiquid = Core.atlas.find(block.name + "-input-liquid");
    }

    @Override
    public TextureRegion[] icons(Block block){
        return top.found() ? new TextureRegion[]{block.region, top} : new TextureRegion[]{block.region};
    }
}
