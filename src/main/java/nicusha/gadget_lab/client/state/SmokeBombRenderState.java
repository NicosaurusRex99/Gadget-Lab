package nicusha.gadget_lab.client.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmokeBombRenderState extends EntityRenderState {
    public final ItemStackRenderState item = new ItemStackRenderState();

    public SmokeBombRenderState() {
    }
}