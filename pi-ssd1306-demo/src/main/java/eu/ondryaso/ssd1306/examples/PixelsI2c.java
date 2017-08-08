package eu.ondryaso.ssd1306.examples;

import java.io.IOException;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

import eu.ondryaso.ssd1306.Constants;
import eu.ondryaso.ssd1306.Display;

public class PixelsI2c {
    public static void main(String[] args) throws IOException, UnsupportedBusNumberException {
        Display disp =
            new Display(Constants.LCD_WIDTH_128, Constants.LCD_HEIGHT_64, GpioFactory.getInstance(),
                I2CFactory.getInstance(I2CBus.BUS_1), 0x3c, null);
        // Create 128x64 display I2C bus 1 address 3C hex

        disp.begin();

        long last, nano = 0;

        for(int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                disp.setPixel(x, y, true);
                last = System.nanoTime();
                disp.display();
                nano += (System.nanoTime() - last);
            }
        }

        System.out.println("Display lasts " + ((nano / 1000000) / (64 * 64)) + " ms");
    }
}
