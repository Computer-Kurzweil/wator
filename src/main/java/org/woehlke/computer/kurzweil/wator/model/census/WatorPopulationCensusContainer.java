package org.woehlke.computer.kurzweil.wator.model.census;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.wator.config.ComputerKurzweilProperties;

import java.io.Serializable;
import java.util.Stack;

/**
 * Holds Data how many Cells per LifeCycleStatus and how many Cells in the whole Population for a Stack of Generations
 *
 * &copy; 2006 - 2008 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/simulated-evolution">Github Repository</a>
 * @see <a href="https://java.woehlke.org/simulated-evolution/">Maven Project Repository</a>
 */
@Log4j2
@ToString(callSuper = true, exclude={"statistics"})
@EqualsAndHashCode(exclude={"statistics"})
public class WatorPopulationCensusContainer implements Serializable {

    static final long serialVersionUID = 242L;

    private final int queueMaxLength;

    private final Stack<WatorPopulationCensus> statistics =
        new Stack<>();

    private volatile WatorPopulationCensus currentPopulationCensus;

    @Getter
    private volatile long worldIteration;

    public WatorPopulationCensusContainer(
        ComputerKurzweilProperties p
    ) {
        this.queueMaxLength = p.getSimulatedevolution().getControl().getQueueMaxLength();
        this.worldIteration = 0L;
    }

    public synchronized void push(WatorPopulationCensus populationCensus) {
        this.currentPopulationCensus = populationCensus;
        this.worldIteration++;
        populationCensus.setWorldIteration(worldIteration);
        statistics.push(populationCensus);
        if (statistics.size() > queueMaxLength) {
            statistics.removeElementAt(0);
        }
        log.info(worldIteration + " : " + populationCensus.toString());
    }

    public synchronized WatorPopulationCensus peek() {
        if(null == this.currentPopulationCensus) {
            WatorPopulationCensus populationCensus = new WatorPopulationCensus();
            populationCensus.setWorldIteration(worldIteration);
            statistics.push(populationCensus);
        }
        return this.currentPopulationCensus;
    }
}
