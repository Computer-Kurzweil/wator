package org.woehlke.computer.kurzweil.wator;

import org.woehlke.computer.kurzweil.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.wator.ui.KochSnowflakeTab;

import java.io.File;
import java.net.URL;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 *
 * (C) 2006 - 2022 Thomas Woehlke.
 * @see <a href="https://github.com/Computer-Kurzweil/kochsnowflake">Github</a>
 * @see <a href="https://java.woehlke.org/kochsnowflake">Maven Project Reports</a>
 * @author Thomas Woehlke
 */
public class KochSnowflakeApplication {

    private KochSnowflakeApplication() {
        String configFileName = "/application.yml";
        URL fileUrl = getClass().getResource(configFileName);
        File configFile = new File(fileUrl.getFile());
        ComputerKurzweilProperties properties = ComputerKurzweilProperties.propertiesFactory(configFile);
        KochSnowflakeTab frame = new KochSnowflakeTab(properties);
    }

    /**
     * Starting the Application.
     * @param args CLI Parameter
     */
    public static void main(String[] args) {
        KochSnowflakeApplication application = new KochSnowflakeApplication();
    }
}
