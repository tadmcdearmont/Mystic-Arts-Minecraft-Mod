package net.mysticarts.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.mysticarts.MysticArtsMod;
import net.mysticarts.monk.MonkData;

public class BlinkC2SPacket {
    public static final Identifier BLINK_PACKET_ID = new Identifier(MysticArtsMod.MOD_ID, "blink_packet");

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            int level = MonkData.getLevel(player);
            if (level < 11 || !MonkData.isUnarmoredAndUnarmed(player)) {
                return;
            }

            double distance = level >= 18 ? 24.0 : 10.0;
            Vec3d start = player.getEyePos();
            Vec3d rotation = player.getRotationVector();
            Vec3d end = start.add(rotation.multiply(distance));

            ServerWorld world = player.getServerWorld();
            BlockHitResult hit = world.raycast(new RaycastContext(
                start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player
            ));

            Vec3d targetPos = (hit.getType() == HitResult.Type.BLOCK) ? hit.getPos().subtract(rotation.multiply(0.5)) : end;

            world.spawnParticles(ParticleTypes.SQUID_INK, player.getX(), player.getY() + 0.5, player.getZ(),
                15, 0.3, 0.5, 0.3, 0.05);

            player.teleport(targetPos.x, targetPos.y, targetPos.z);

            world.spawnParticles(ParticleTypes.PORTAL, targetPos.x, targetPos.y + 0.5, targetPos.z,
                20, 0.4, 0.6, 0.4, 0.1);
            world.playSound(null, targetPos.x, targetPos.y, targetPos.z,
                MysticArtsMod.BLINK_DASH, SoundCategory.PLAYERS, 1.0f, 1.2f);
        });
    }
}
