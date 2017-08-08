package eu.ondryaso.ssd1306.examples;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

import eu.ondryaso.ssd1306.Display;

public class BasicGraphicsI2c {
    public static void main(String[] args) throws IOException, UnsupportedBusNumberException {
        Display disp = new Display(128, 64, GpioFactory.getInstance(),
                I2CFactory.getInstance(I2CBus.BUS_1), 0x3c, RaspiPin.GPIO_04);
        // Create 128x64 display I2C bus 1 address 3C hex

        disp.begin();
        // Init the display

        Image i = ImageIO.read(BasicGraphicsI2c.class.getResourceAsStream("lord.png"));
        disp.getGraphics().setColor(Color.WHITE);
        disp.getGraphics().drawImage(i, 0, 0, null);
        disp.getGraphics().setFont(new Font("Monospaced", Font.PLAIN, 10));
        disp.getGraphics().drawString("Praise him", 64, 60);
        disp.getGraphics().drawRect(0, 0, disp.getWidth() - 1, disp.getHeight() - 1);
        // Deal with the image using AWT

        disp.displayImage();
        // Copy AWT image to an inner buffer and send to the display

        for(int x = 70; x < 90; x += 2) {
            for(int y = 10; y < 30; y += 2) {
                disp.setPixel(x, y, true);
            }
        }
        // Set some pixels in the buffer manually

        disp.display();
        // Send the buffer to the display again, now with the modified pixels
    }
}
