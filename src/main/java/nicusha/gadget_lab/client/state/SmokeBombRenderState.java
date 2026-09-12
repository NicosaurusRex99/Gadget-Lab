package nicusha.gadget_lab.client.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class SmokeBombRenderState extends EntityRenderState {
    public final ItemStackRenderState item = new ItemStackRenderState();

    public SmokeBombRenderState() {
    }
}