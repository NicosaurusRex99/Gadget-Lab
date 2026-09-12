package nicusha.gadget_lab.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public record CapturedMobsComponent(List<StoredMob> mobs) {

    public static final CapturedMobsComponent EMPTY = new CapturedMobsComponent(List.of());

    public record StoredMob(CompoundTag entityData, Component displayName) {
        public static final Codec<StoredMob> CODEC = RecordCodecBuilder.create(instance -> instance.group(CompoundTag.CODEC.fieldOf("entity_data").forGetter(StoredMob::entityData), ComponentSerialization.CODEC.fieldOf("display_name").forGetter(StoredMob::displayName)).apply(instance, StoredMob::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, StoredMob> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.COMPOUND_TAG, StoredMob::entityData, ComponentSerialization.STREAM_CODEC, StoredMob::displayName, StoredMob::new);
    }

    public static final Codec<CapturedMobsComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(StoredMob.CODEC.listOf().fieldOf("mobs").forGetter(CapturedMobsComponent::mobs)).apply(instance, CapturedMobsComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CapturedMobsComponent> STREAM_CODEC = StreamCodec.composite(StoredMob.STREAM_CODEC.apply(ByteBufCodecs.list()), CapturedMobsComponent::mobs, CapturedMobsComponent::new);

    public boolean isFull() {
        return mobs.size() >= 10;
    }

    public boolean isEmpty() {
        return mobs.isEmpty();
    }

    public CapturedMobsComponent withAddedMob(StoredMob mob) {
        if (isFull()) return this;
        List<StoredMob> updated = new ArrayList<>(mobs);
        updated.add(mob);
        return new CapturedMobsComponent(updated);
    }

    public CapturedMobsComponent withRemovedFirst(StoredMob[] removedHolder) {
        if (isEmpty()) return this;
        List<StoredMob> updated = new ArrayList<>(mobs);
        removedHolder[0] = updated.remove(0);
        return new CapturedMobsComponent(updated);
    }
}