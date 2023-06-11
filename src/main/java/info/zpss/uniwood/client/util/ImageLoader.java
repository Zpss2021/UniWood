package info.zpss.uniwood.client.util;

import javax.swing.*;
import java.util.Objects;

public class ImageLoader {
    public static ImageIcon load(String path) {
        return new ImageIcon(Objects.requireNonNull(ImageLoader.class.getClassLoader().getResource(path)));
    }

    public static ImageIcon load(String path, int width, int height) {
        return new ImageIcon(new ImageIcon(Objects.requireNonNull(ImageLoader.class.getClassLoader().getResource(path)))
                .getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
    }
}
