package org.woehlke.computer.kurzweil.wator;

import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.wator.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.wator.view.WatorFrame;

/**
 * Class with main Method for Starting the Desktop Application.
 *
 * &copy; 2006 - 2008 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see WatorFrame
 *
 * @see <a href="https://github.com/Computer-Kurzweil/simulated-evolution">Github Repository</a>
 * @see <a href="https://java.woehlke.org/simulated-evolution/">Maven Project Repository</a>
 */
@Log4j2
public class WatorApplication {

    private final WatorFrame watorFrame;

    private WatorApplication() {
        String configFileName = "application.yml";
        String jarFilePath = "target/wator.jar";
        ComputerKurzweilProperties properties = ComputerKurzweilProperties.propertiesFactory(configFileName, jarFilePath);
        this.watorFrame = new WatorFrame(properties);

    }

    public void start(){
        watorFrame.start();
    }

    /**
     * Starting the Desktop Application
     * @param args CLI Parameter
     */
    public static void main(String[] args) {
        WatorApplication application = new WatorApplication();
        application.start();
    }
}
