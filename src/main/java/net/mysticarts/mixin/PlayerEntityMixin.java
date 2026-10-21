package net.mysticarts.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.mysticarts.monk.MonkData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void mysticarts$buffFistMiningSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!MonkData.isUnarmoredAndUnarmed(player)) return;

        int level = MonkData.getLevel(player);
        float baseSpeed = cir.getReturnValue();

        if (level >= 13) {
            cir.setReturnValue(Math.max(baseSpeed * 8.0f, 12.0f));
        } else if (level >= 1 && block.isIn(net.minecraft.registry.tag.BlockTags.LOGS)) {
            cir.setReturnValue(baseSpeed * 3.0f);
        }
    }
}
