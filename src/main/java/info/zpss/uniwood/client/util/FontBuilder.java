package info.zpss.uniwood.client.util;

import java.awt.*;
import java.util.Arrays;

public class FontBuilder {
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

    public FontBuilder() {
        font = new Font(fontName, Font.PLAIN, 12);
    }

    public FontBuilder size(float size) {
        font = this.font.deriveFont(size);
        return this;
    }

    public FontBuilder small() {
        font = this.font.deriveFont(font.getSize() - 2.0f);
        return this;
    }

    public FontBuilder large() {
        font = this.font.deriveFont(font.getSize() + 2.0f);
        return this;
    }

    public FontBuilder plain() {
        font = this.font.deriveFont(Font.PLAIN);
        return this;
    }

    public FontBuilder bold() {
        font = this.font.deriveFont(Font.BOLD);
        return this;
    }

    public FontBuilder italic() {
        font = this.font.deriveFont(Font.ITALIC);
        return this;
    }

    public Font build() {
        return font;
    }
}
