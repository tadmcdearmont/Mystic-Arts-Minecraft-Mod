package net.mysticarts;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.mysticarts.item.MysticCodexItem;
import net.mysticarts.network.BlinkC2SPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysticArtsMod implements ModInitializer {
    public static final String MOD_ID = "mysticarts";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Items
    public static final Item MYSTIC_CODEX = new MysticCodexItem(
        new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)
    );

    // Sound Events
    public static final Identifier BELL_SOUND_ID = new Identifier(MOD_ID, "monk_bell");
    public static final SoundEvent MONK_BELL = SoundEvent.of(BELL_SOUND_ID);

    public static final Identifier BLINK_SOUND_ID = new Identifier(MOD_ID, "blink_dash");
    public static final SoundEvent BLINK_DASH = SoundEvent.of(BLINK_SOUND_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("[Mystic Arts] Initializing Mystic Arts (Monk Remake) for Fabric 1.20.1...");

        // Register Items
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "mystic_codex"), MYSTIC_CODEX);

        // Register Creative Tabs
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(MYSTIC_CODEX);
        });

        // Register Sounds
        Registry.register(Registries.SOUND_EVENT, BELL_SOUND_ID, MONK_BELL);
        Registry.register(Registries.SOUND_EVENT, BLINK_SOUND_ID, BLINK_DASH);

        // Register Server-Side Packet Receivers
        ServerPlayNetworking.registerGlobalReceiver(BlinkC2SPacket.BLINK_PACKET_ID, BlinkC2SPacket::receive);

        LOGGER.info("[Mystic Arts] Ready. The path of the ascetic monk is open.");
    }
}
