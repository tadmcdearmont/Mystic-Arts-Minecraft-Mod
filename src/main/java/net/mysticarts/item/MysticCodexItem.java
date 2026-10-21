package net.mysticarts.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.mysticarts.monk.MonkData;

/**
 * The Mystic Codex: The sacred parchment guidebook replacing achievements.
 * Right-clicking opens the dual-page monastic journal showing current level,
 * ascetic trials, and unlocked supernatural martial arts.
 */
public class MysticCodexItem extends Item {
    public MysticCodexItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient) {
            openCodexScreen(user);
        } else {
            MonkData.syncPlayerProgress(user);
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 0.8f, 1.0f);
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    private void openCodexScreen(PlayerEntity player) {
        net.mysticarts.client.MysticArtsClient.openCodex();
    }
}
