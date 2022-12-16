package org.woehlke.computer.kurzweil.wator;

import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.wator.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.wator.view.WatorTab;

/**
 * Class with main Method for Starting the Desktop Application.
 *
 * &copy; 2006 - 2008 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see WatorTab
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/simulated-evolution">Github Repository</a>
 * @see <a href="https://java.woehlke.org/simulated-evolution/">Maven Project Repository</a>
 */
@Log4j2
public class WatorApplication {

    private final WatorTab watorTab;

    private WatorApplication() {
        String configFileName = "application.yml";
        String jarFilePath = "target/simulatedevolution.jar";
        ComputerKurzweilProperties properties = ComputerKurzweilProperties.propertiesFactory(configFileName, jarFilePath);
        this.watorTab = new WatorTab(properties);

    }

    public void start(){
        watorTab.start();
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
