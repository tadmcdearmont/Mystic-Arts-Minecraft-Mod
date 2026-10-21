package net.mysticarts.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.mysticarts.monk.MonkData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(net.minecraft.entity.LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    private float mysticarts$applyMonkDefenses(float amount, DamageSource source) {
        Entity entity = (Entity) (Object) this;
        if (!(entity instanceof PlayerEntity player)) return amount;

        if (!MonkData.isUnarmoredAndUnarmed(player)) return amount;

        int level = MonkData.getLevel(player);

        if (source.isOf(DamageTypes.FALL)) {
            if (level >= 16) return 0.0f;
            if (level >= 4) return amount * 0.2f;
        }

        if (source.isOf(DamageTypes.IN_FIRE) || source.isOf(DamageTypes.ON_FIRE) || source.isOf(DamageTypes.LAVA)) {
            if (level >= 12) return 0.0f;
        }

        if (source.isOf(DamageTypes.EXPLOSION) || source.isOf(DamageTypes.PLAYER_EXPLOSION)) {
            if (level >= 9) return amount * 0.25f;
        }

        float naturalArmor = MonkData.getNaturalArmor(player);
        if (naturalArmor > 0) {
            float reductionMultiplier = (20.0f - Math.min(18.0f, naturalArmor)) / 20.0f;
            amount *= reductionMultiplier;
        }

        return amount;
    }
}
