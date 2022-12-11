package org.woehlke.computer.kurzweil.kochsnowflake.ui;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 *
 * (C) 2006 - 2022 Thomas Woehlke.
 * @see <a href="https://github.com/Computer-Kurzweil/kochsnowflake">Github</a>
 * @see <a href="https://java.woehlke.org/kochsnowflake">Maven Project Reports</a>
 * @author Thomas Woehlke
 *
 * Date: 05.02.2006
 * Time: 00:36:20
 */
public class KochSnowflakeController extends Thread implements Runnable {

    private static final long serialVersionUID = 7526471155622776147L;

    private final int THREAD_SLEEP_TIME = 1;
    private volatile KochSnowflakeModel mandelbrotModel;
    private volatile KochSnowflakeTab frame;
    private volatile Boolean goOn;

    public KochSnowflakeController(KochSnowflakeModel model, KochSnowflakeTab frame) {
        this.frame = frame;
        this.mandelbrotModel = model;
        goOn = Boolean.TRUE;
    }

    public void run() {
        boolean doIt;
        do {
            synchronized (goOn) {
                doIt = goOn.booleanValue();
            }
            if(this.mandelbrotModel.step()){
                frame.getCanvas().repaint();
            }
            try { sleep(THREAD_SLEEP_TIME); }
            catch (InterruptedException e) { }
        }
        while (doIt);
    }

    public void exit() {
        synchronized (goOn) {
            goOn = Boolean.FALSE;
        }
    }

}
