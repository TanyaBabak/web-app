package com.babak.service.impl;

import com.babak.entity.Captcha;
import com.babak.service.CaptchaService;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaptchaServiceImpl implements CaptchaService {

    private Set<Captcha> captchas;
    private Random random;
    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    private static final int CAPTCHA_SIZE = 35;
    private static final int CAPTCHA_PADDING = 5;
    private static final int CAPTCHA_COMPLEXITY = 7;
    private static final String CAPTCHA_TEXT_FONT = "Arial";
    private static final long CAPTCHA_TIME_OUT = 120000;

    public CaptchaServiceImpl() {
        captchas = new HashSet<>();
        random = new Random();
    }

    @Override
    public Captcha createCaptcha() {
        long captureId = UUID.randomUUID().getMostSignificantBits();
        LOGGER.debug("Id new captcha installed");
        String captureString = generateString();
        LOGGER.debug("Value new captcha installed");
        BufferedImage image = generateImage(captureString);
        LOGGER.debug("Image new captcha installed");
        long finishTime = System.currentTimeMillis() + CAPTCHA_TIME_OUT;
        LOGGER.debug("Finish time new captcha installed");
        Captcha captcha = Captcha.builder()
                .id(captureId)
                .captchaString(captureString)
                .image(image)
                .finishTime(finishTime).build();
        LOGGER.debug("New captcha created");
        LOGGER.debug("New captcha added in list all captchas");
        return captcha;
    }

    @Override
    public Captcha receiveImageById(long id) {
        for (Captcha captcha : captchas) {
            if (captcha.getId() == id) {
                LOGGER.debug("Captcha found by Id");
                return captcha;
            }
        }
        LOGGER.debug("Captcha didn't find by Id");
        return null;
    }

    @Override
    public boolean checkCaptchaByString(String answer) {
        for (Captcha captcha : captchas) {
            if (captcha.getCaptchaString().equals(answer)) {
                LOGGER.debug("The answer is the same");
                return true;
            }
        }
        LOGGER.debug("The answer isn't the same");
        return false;
    }

    @Override
    public boolean checkTimeOutForRegistration(long id, long currentTime) {
        Captcha captcha = receiveImageById(id);
        LOGGER.debug("Captcha found by Id");
        long finishTime = captcha.getFinishTime();
        LOGGER.debug("Finish time captcha's defined");
        return finishTime < currentTime;
    }

    @Override
    public void removeCaptcha(long id) {
        captchas.removeIf(captcha -> captcha.getId() == id);
        LOGGER.debug("Captcha removed");
    }

    @Override
    public void putCaptcha(Captcha captcha) {
        captchas.add(captcha);
    }

    private String generateString() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int min = 1;
        int max = 9;
        for (int i = 0; i < 6; i++) {
            int randomValue = random.nextInt((max - min) + 1) + min;
            String value = String.valueOf(randomValue);
            stringBuilder.append(value);
        }
        LOGGER.debug("Value generated for captcha");
        return stringBuilder.toString();
    }

    private BufferedImage generateImage(String captchaString) {
        BufferedImage buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = buffer.createGraphics();
        Font font = new Font(CAPTCHA_TEXT_FONT, Font.BOLD, CAPTCHA_SIZE);
        Rectangle2D r = font.getStringBounds(captchaString, g2d.getFontRenderContext());
        buffer = new BufferedImage(
                (int) r.getWidth() + CAPTCHA_PADDING * 2,
                (int) r.getHeight() + CAPTCHA_PADDING * 2,
                BufferedImage.TYPE_INT_RGB);
        g2d = (Graphics2D) buffer.getGraphics();
        g2d.setFont(font);
        Color background = Color.WHITE;
        Color foreground = new Color(64, 64, 64);
        g2d.setColor(background);
        g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        drawText(captchaString, foreground, buffer, g2d);
        transformText(buffer);
        drawLines(buffer, g2d);
        g2d.dispose();
        LOGGER.debug("Image generated for captcha");
        return buffer;
    }

    /**
     * Print text on the image.
     *
     * @param captchaString - String should be printed on image
     * @param foreground    - Color for captcha elements
     * @param buffer        - BufferedImage that contents intermediate results of drawing
     * @param g2d           - Graphics2D object for working with image
     */
    private void drawText(String captchaString, Color foreground, BufferedImage buffer, Graphics2D g2d) {
        g2d.setTransform(AffineTransform.getRotateInstance(0d));
        g2d.setColor(foreground);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2d.drawString(captchaString, CAPTCHA_PADDING, buffer.getHeight() - CAPTCHA_PADDING * CAPTCHA_SIZE / 15);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        g2d.drawString(captchaString, CAPTCHA_PADDING / 2, buffer.getHeight() - (CAPTCHA_PADDING * CAPTCHA_SIZE / 14));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.drawString(captchaString, (int) (CAPTCHA_PADDING / 0.8), buffer.getHeight() - (CAPTCHA_PADDING * CAPTCHA_SIZE / 18));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        g2d.drawString(captchaString, (int) (CAPTCHA_PADDING / 1.9), buffer.getHeight() - (CAPTCHA_PADDING * CAPTCHA_SIZE / 17));
    }

    /**
     * Transform captcha image with sin and cos functions.
     *
     * @param buffer - BufferedImage that contents intermediate results of drawing
     */
    private void transformText(BufferedImage buffer) {
        BufferedImage buffer2 = new BufferedImage(
                buffer.getWidth(),
                buffer.getHeight(),
                buffer.getType());
        Graphics2D g2d2 = (Graphics2D) buffer2.getGraphics();
        g2d2.drawImage(buffer, 0, 0, null);
        double seed = random.nextDouble() * 3d + 5d;
        for (int x = 0; x < buffer.getWidth(); x++) {
            for (int y = 0; y < buffer.getHeight(); y++) {
                int xx = x + (int) (Math.cos((double) y / seed) * ((double) CAPTCHA_COMPLEXITY / 2d));
                int yy = y + (int) (Math.sin((double) x / (seed + 1)) * ((double) CAPTCHA_COMPLEXITY / 2d));
                xx = Math.abs(xx % buffer.getWidth());
                yy = Math.abs(yy % buffer.getHeight());
                buffer.setRGB(x, y, buffer2.getRGB(xx, yy));
            }
        }
    }

    /**
     * Draw additional lines for making captcha difficult.
     *
     * @param buffer - BufferedImage that contents intermediate results of drawing
     * @param g2d    - Graphics2D object for working with image
     */
    private void drawLines(BufferedImage buffer, Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2d.setTransform(AffineTransform.getRotateInstance(random.nextDouble() * 0.3d - 0.15d));
        g2d.setColor(new Color(128, 128, 128, 128));
        for (int x = -100; x < buffer.getWidth() + 100; x = x + (random.nextInt(9) + 6)) {
            g2d.setStroke(new BasicStroke(
                    1,
                    BasicStroke.CAP_SQUARE,
                    BasicStroke.CAP_SQUARE,
                    10, new float[] {random.nextInt(10) + 2, random.nextInt(4) + 2},
                    0));
            g2d.drawLine(x, -100, x, buffer.getHeight() + 100);
        }
        g2d.setColor(new Color(188, 188, 128, 64));
        for (int y = -100; y < buffer.getHeight() + 100; y = y + (random.nextInt(8) + 7)) {
            g2d.setStroke(new BasicStroke(
                    1,
                    BasicStroke.CAP_SQUARE,
                    BasicStroke.CAP_SQUARE,
                    10, new float[] {random.nextInt(10) + 2, random.nextInt(3) + 2},
                    0));
            g2d.drawLine(-100, y, buffer.getWidth() + 100, y);
        }
    }
}
