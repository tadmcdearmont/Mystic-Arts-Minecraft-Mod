package net.mysticarts.monk;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MonkData {
    private static final Map<UUID, Integer> PLAYER_LEVELS = new HashMap<>();
    private static final Map<UUID, Integer> CURRENT_GOAL_PROGRESS = new HashMap<>();

    public static int getLevel(PlayerEntity player) {
        return PLAYER_LEVELS.getOrDefault(player.getUuid(), 0);
    }

    public static void setLevel(PlayerEntity player, int level) {
        PLAYER_LEVELS.put(player.getUuid(), Math.max(0, Math.min(20, level)));
    }

    public static int getGoalProgress(PlayerEntity player) {
        return CURRENT_GOAL_PROGRESS.getOrDefault(player.getUuid(), 0);
    }

    public static void addGoalProgress(PlayerEntity player, int amount) {
        int current = getGoalProgress(player);
        CURRENT_GOAL_PROGRESS.put(player.getUuid(), current + amount);
    }

    public static boolean isUnarmoredAndUnarmed(PlayerEntity player) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                if (!player.getEquippedStack(slot).isEmpty()) {
                    return false;
                }
            }
        }

        ItemStack mainHand = player.getMainHandStack();
        if (!mainHand.isEmpty() && !(mainHand.getItem() instanceof net.mysticarts.item.MysticCodexItem)) {
            return false;
        }

        return true;
    }

    public static float getUnarmedAttackDamage(PlayerEntity player) {
        if (!isUnarmoredAndUnarmed(player)) return 1.0f;

        int level = getLevel(player);
        if (level >= 20) return 50.0f; // Supreme Grandmaster (25 hearts!)
        if (level >= 18) return 18.0f;
        if (level >= 15) return 15.0f;
        if (level >= 13) return 12.0f;
        if (level >= 6)  return 6.0f;
        if (level >= 1)  return 4.0f;
        return 1.0f;
    }

    public static float getNaturalArmor(PlayerEntity player) {
        if (!isUnarmoredAndUnarmed(player)) return 0.0f;

        int level = getLevel(player);
        if (level >= 20) return 20.0f;
        if (level >= 19) return 18.0f;
        if (level >= 17) return 16.0f;
        if (level >= 13) return 13.0f;
        if (level >= 10) return 11.0f;
        if (level >= 5)  return 6.0f;
        if (level >= 2)  return 4.0f;
        return 0.0f;
    }

    public static void syncPlayerProgress(PlayerEntity player) {
        // Sends S2C sync packet to client
    }
}
