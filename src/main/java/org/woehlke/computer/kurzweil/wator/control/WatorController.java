package org.woehlke.computer.kurzweil.wator.control;

import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.wator.view.WatorFrame;
import org.woehlke.computer.kurzweil.wator.view.canvas.WatorCanvas;
import org.woehlke.computer.kurzweil.wator.model.WatorModel;
import org.woehlke.computer.kurzweil.wator.view.canvas.population.PopulationStatisticsElementsPanelLifeCycle;

import java.io.Serializable;

/**
 * The ControllerThread controls the Interactions between Model and View (MVC-Pattern).
 *
 * &copy; 2006 - 2013 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/simulated-evolution">Github Repository</a>
 * @see <a href="https://java.woehlke.org/simulated-evolution/">Maven Project Repository</a>
 *
 * Date: 05.02.2006
 * Time: 00:36:20
 */
@Log4j2
public class WatorController extends Thread implements Runnable, Serializable {

    static final long serialVersionUID = 242L;

    /**
     * Data Model for the Simulation
     */
    private final WatorModel model;

    /**
     * Canvas, where to paint in the GUI.
     */
    private final WatorCanvas canvas;
    private final PopulationStatisticsElementsPanelLifeCycle panelLifeCycle;
    private final WatorFrame tab;

    /**
     * Time to Wait in ms.
     */
    private final int timeToWait;

    /**
     * Control for Threading
     */
    private Boolean mySemaphore;

    public WatorController(
        WatorModel model,
        WatorCanvas canvas,
        PopulationStatisticsElementsPanelLifeCycle panelLifeCycle,
        WatorFrame frame
    ) {
        this.model = model;
        this.canvas = canvas;
        this.panelLifeCycle = panelLifeCycle;
        this.tab = frame;
        this.timeToWait = this.tab.getComputerKurzweilProperties().getSimulatedevolution()
            .getControl().getThreadSleepTime();
        mySemaphore = Boolean.TRUE;
    }

    public void run() {
        boolean doMyJob;
        do {
            doMyJob = isRunning();
            model.letLivePopulation();
            panelLifeCycle.update();
            panelLifeCycle.repaint();
            canvas.repaint();
            tab.repaint();
            try {
                sleep(timeToWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (doMyJob);
    }

    public synchronized Boolean isRunning(){
        return this.mySemaphore;
    }

    public synchronized void exit() {
        mySemaphore = Boolean.FALSE;
    }
}
