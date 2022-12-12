package org.woehlke.computer.kurzweil.wator.model;


import java.io.Serializable;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 *
 * (C) 2006 - 2015 Thomas Woehlke.
 * @see <a href="https://github.com/Computer-Kurzweil/kochsnowflake">Github</a>
 * @see <a href="https://java.woehlke.org/kochsnowflake">Maven Project Reports</a>
 * @author Thomas Woehlke
 *
 * Created by tw on 16.12.2019.
 */
public class WatorTabStateMachine implements Serializable {

    private static final long serialVersionUID = 7526471155622776147L;

    private volatile WatorTabState applicationState;

    public WatorTabStateMachine() {
        this.applicationState = WatorTabState.MANDELBROT;
    }

    public void click(){
        WatorTabState nextApplicationState = null;
        switch (applicationState){
            case MANDELBROT:
                nextApplicationState = WatorTabState.MANDELBROT_ZOOM;
                break;
            case MANDELBROT_ZOOM:
                nextApplicationState = WatorTabState.MANDELBROT;
                break;
        }
        this.setApplicationState(nextApplicationState);
    }

    public void setModeSwitch() {
        WatorTabState nextApplicationState = this.applicationState;
        /*
        switch (applicationState){
            case MANDELBROT:
            case JULIA_SET:
                break;
            case MANDELBROT_ZOOM:
                nextApplicationState = MANDELBROT;
                break;
            case JULIA_SET_ZOOM:
                nextApplicationState = JULIA_SET;
                break;
        }
        */
        this.setApplicationState(nextApplicationState);
    }

    public void setModeZoom() {
        WatorTabState nextApplicationState = this.applicationState;
        switch (applicationState){
            case MANDELBROT:
                nextApplicationState = WatorTabState.MANDELBROT_ZOOM;
                break;
            //case JULIA_SET:
              //  nextApplicationState = JULIA_SET_ZOOM;
                //break;
            case MANDELBROT_ZOOM:
            //case JULIA_SET_ZOOM:
                break;
        }
        this.setApplicationState(nextApplicationState);
    }

    public WatorTabState getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(WatorTabState applicationState) {
        this.applicationState = applicationState;
    }

}
