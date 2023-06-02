package info.zpss.uniwood.client.util;

import java.awt.*;
import java.util.Arrays;

public class FontMaker {
    private static final String fontName;
    private Font font;

    static {
        String[] availableFontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontName = Arrays.stream(availableFontNames)
                .filter(s -> s.equals("微软雅黑"))
                .findFirst()
                .orElseGet(() -> Arrays.stream(availableFontNames)
                        .filter(s -> s.equals("黑体"))
                        .findFirst()
                        .orElse("SansSerif"));
    }

    public FontMaker() {
        font = new Font(fontName, Font.PLAIN, 12);
    }

    public FontMaker size(float size) {
        font = this.font.deriveFont(size);
        return this;
    }

    public FontMaker small() {
        font = this.font.deriveFont(font.getSize() - 2.0f);
        return this;
    }

    public FontMaker large() {
        font = this.font.deriveFont(font.getSize() + 2.0f);
        return this;
    }

    public FontMaker plain() {
        font = this.font.deriveFont(Font.PLAIN);
        return this;
    }

    public FontMaker bold() {
        font = this.font.deriveFont(Font.BOLD);
        return this;
    }

    public FontMaker italic() {
        font = this.font.deriveFont(Font.ITALIC);
        return this;
    }

    public Font build() {
        return font;
    }
}
