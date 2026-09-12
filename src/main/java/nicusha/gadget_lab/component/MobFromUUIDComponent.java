package nicusha.gadget_lab.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;

import java.util.Optional;
import java.util.UUID;

public record MobFromUUIDComponent(Optional<UUID> uuid, Optional<Component> name) {

    public static final MobFromUUIDComponent NONE = new MobFromUUIDComponent(Optional.empty(), Optional.empty());
    public static final Codec<MobFromUUIDComponent> ENTITY_ID_COMPONENT_CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    Codec.STRING.optionalFieldOf("uuid").forGetter((MobFromUUIDComponent c) -> c.uuid().map(UUID::toString)),
                    ComponentSerialization.CODEC.optionalFieldOf("name").forGetter(MobFromUUIDComponent::name)
            ).apply(instance, (Optional<String> uuid, Optional<Component> name) ->
                    new MobFromUUIDComponent(uuid.map(UUID::fromString), name)
            ));

    public static final StreamCodec<RegistryFriendlyByteBuf, MobFromUUIDComponent> STREAM_ENTITY_ID = new StreamCodec<>() {

        @Override
        public MobFromUUIDComponent decode(RegistryFriendlyByteBuf buf) {
            return new MobFromUUIDComponent(buf.readBoolean() ? Optional.of(UUID.fromString(buf.readUtf())) : Optional.empty(), buf.readBoolean() ? Optional.of(ComponentSerialization.STREAM_CODEC.decode(buf)) : Optional.empty());
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, MobFromUUIDComponent component) {
            buf.writeBoolean(component.uuid().isPresent());
            component.uuid().ifPresent(uuid -> buf.writeUtf(uuid.toString()));
            buf.writeBoolean(component.name().isPresent());
            component.name().ifPresent(comp -> ComponentSerialization.STREAM_CODEC.encode(buf, comp));
        }
    };
}
