package info.zpss.uniwood.desktop.client.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class Avatar {
    private ImageIcon avatarIcon;

    public Avatar() {
    }

    public void fromBase64(String base64) {
        this.avatarIcon = toImageIcon(base64);
    }

    public void fromFile(String srcFilePath) {
        this.avatarIcon = standardized(new ImageIcon(new File(srcFilePath).getAbsolutePath()), 64);
    }

    public ImageIcon toIcon() {
        return avatarIcon;
    }

    public ImageIcon toIcon(int length) {
        return new ImageIcon(avatarIcon.getImage().getScaledInstance(length, length, Image.SCALE_SMOOTH));
    }

    public String toBase64() {
        BufferedImage bufferedImage = new BufferedImage(
                avatarIcon.getIconWidth(),
                avatarIcon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        avatarIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            return String.format("data:image/png;base64,%s", Base64.getEncoder().encodeToString(imageBytes));
        } catch (IOException e) {
            e.printStackTrace();    // TODO
        } finally {
            try {
                outputStream.close();
                bufferedImage.flush();
            } catch (IOException ex) {
                ex.printStackTrace();   // TODO
            }
        }
        return null;
    }

    public void toFile(String dstFilePath) {
        BufferedImage bufferedImage = new BufferedImage(
                avatarIcon.getIconWidth(),
                avatarIcon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        avatarIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);

        File dstFile = new File(dstFilePath);
        try {
            ImageIO.write(bufferedImage, "png", dstFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferedImage.flush();
        }
    }

    public static ImageIcon toImageIcon(String base64) {
        if (base64.startsWith("data:image/png;base64,"))
            base64 = base64.substring(22);
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes)) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return new ImageIcon(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ImageIcon standardized(ImageIcon inputIcon, int length) {
        BufferedImage inputImage = new BufferedImage(
                inputIcon.getIconWidth(),
                inputIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        inputIcon.paintIcon(null, inputImage.getGraphics(), 0, 0);
        BufferedImage outputImage = new BufferedImage(length, length, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = outputImage.createGraphics();
        graphics2D.drawImage(inputImage, 0, 0, 64, 64, null);
        graphics2D.dispose();
        return new ImageIcon(outputImage);
    }

    public static void main(String[] args) {

    }
}