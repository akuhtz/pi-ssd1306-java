package eu.ondryaso.ssd1306.examples;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiFactory;

import eu.ondryaso.ssd1306.Constants;
import eu.ondryaso.ssd1306.Constants.ScrollSpeed;
import eu.ondryaso.ssd1306.Display;

public class Pixels {

    private static final Logger LOGGER = LoggerFactory.getLogger(Pixels.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        // on Pi3
        // CS : Pin 24 - GPIO_10
        // DC : Pin 16 - GPIO_04
        // RES : Pin 15 - GPIO_03
        // SDA : Pin 19 - GPIO_12
        // SCK : Pin 23 - GPIO_14
        // VDD : 3.3V
        // GND : GND

        LOGGER.info("Create display.");
        Display disp =
            new Display(Constants.LCD_WIDTH_128, Constants.LCD_HEIGHT_64, GpioFactory.getInstance(),
                SpiFactory.getInstance(SpiChannel.CS0, 8000000), RaspiPin.GPIO_03, RaspiPin.GPIO_04);

        LOGGER.info("Begin display.");

        disp.begin();

        LOGGER.info("Change pixels.");

        // long last, nano = 0;
        //
        // for(int x = 0; x < 64; x++) {
        // for (int y = 0; y < 64; y++) {
        // disp.setPixel(x, y, true);
        // last = System.nanoTime();
        // disp.display();
        // nano += (System.nanoTime() - last);
        // }
        // }
        //
        // LOGGER.info("Display lasts " + ((nano / 1000000) / (64 * 64)) + " ms");

        disp.clear();

        disp.displayString("BiDiB ist cool :-)");
        Thread.sleep(500);

        disp.invertDisplay(true);

        Thread.sleep(500);

        disp.invertDisplay(false);

        Thread.sleep(500);

        disp.scrollHorizontally(false, 0, 50, ScrollSpeed.SPEED_5_FRAMES);

        Thread.sleep(500);

        disp.scrollHorizontally(true, 0, 50, ScrollSpeed.SPEED_5_FRAMES);

        Thread.sleep(500);

        disp.stopScroll();

        disp.clearImage();

        Image i = ImageIO.read(BasicGraphics.class.getResourceAsStream("bidib_logo.png"));
        disp.getGraphics().setColor(Color.WHITE);
        disp.getGraphics().drawImage(i, 14, 0, null);
        // disp.getGraphics().setFont(new Font("Monospaced", Font.PLAIN, 10));
        // disp.getGraphics().drawString("Praise him", 64, 60);
        // disp.getGraphics().drawRect(0, 0, disp.getWidth() - 1, disp.getHeight() - 1);
        // Deal with the image using AWT

        disp.displayImage();

        disp.scrollHorizontally(false, 0, 60, ScrollSpeed.SPEED_64_FRAMES);

        Thread.sleep(1500);

        disp.scrollHorizontally(true, 0, 60, ScrollSpeed.SPEED_64_FRAMES);

        Thread.sleep(1500);

        disp.stopScroll();

    }
}
