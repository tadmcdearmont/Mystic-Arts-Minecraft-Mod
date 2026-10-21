package net.mysticarts.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MysticCodexScreen extends Screen {
    private static final Identifier BOOK_TEXTURE = new Identifier("minecraft", "textures/gui/book.png");
    private int currentLevel = 1;
    private final int maxLevel = 20;

    public MysticCodexScreen() {
        super(Text.translatable("gui.mysticarts.codex.title"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("<- Previous"), button -> {
            if (currentLevel > 1) {
                currentLevel--;
                playPageTurn();
            }
        }).dimensions(centerX - 140, centerY + 80, 70, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Next ->"), button -> {
            if (currentLevel < maxLevel) {
                currentLevel++;
                playPageTurn();
            }
        }).dimensions(centerX + 70, centerY + 80, 70, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close Tome"), button -> {
            this.close();
        }).dimensions(centerX - 40, centerY + 80, 80, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        context.drawTexture(BOOK_TEXTURE, centerX - 96, centerY - 90, 20, 1, 192, 192);
        context.drawText(this.textRenderer, Text.literal("Mystic Codex: Level " + currentLevel),
            centerX - 70, centerY - 70, 0x3F2B14, false);

        super.render(context, mouseX, mouseY, delta);
    }

    private void playPageTurn() {
        if (this.client != null && this.client.player != null) {
            this.client.player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, 0.7f, 1.0f);
        }
    }
}
