package net.mysticarts.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.mysticarts.network.BlinkC2SPacket;
import org.lwjgl.glfw.GLFW;

public class MysticArtsClient implements ClientModInitializer {
    public static KeyBinding BLINK_KEY;

    @Override
    public void onInitializeClient() {
        BLINK_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.mysticarts.blink",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "category.mysticarts.skills"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (BLINK_KEY.wasPressed()) {
                if (client.player != null) {
                    ClientPlayNetworking.send(BlinkC2SPacket.BLINK_PACKET_ID, PacketByteBufs.empty());
                }
            }
        });
    }

    public static void openCodex() {
        MinecraftClient.getInstance().setScreen(new MysticCodexScreen());
    }
}
