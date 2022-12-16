package org.woehlke.computer.kurzweil.wator.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.wator.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.wator.model.cell.Cell;
import org.woehlke.computer.kurzweil.wator.model.census.WatorPopulationCensus;
import org.woehlke.computer.kurzweil.wator.model.census.WatorPopulationCensusContainer;
import org.woehlke.computer.kurzweil.wator.model.world.WatorParameter;
import org.woehlke.computer.kurzweil.wator.model.lattice.WatorWorldLattice;
import org.woehlke.computer.kurzweil.wator.model.world.WorldPoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * The World contains Water, Cells and Food.
 * It is the Data Model of the Simulation in a MVC Pattern.
 *
 * &copy; 2006 - 2008 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see Cell
 * @see WatorWorldLattice
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/simulated-evolution">Github Repository</a>
 * @see <a href="https://java.woehlke.org/simulated-evolution/">Maven Project Repository</a>
 *
 * User: thomas
 * Date: 04.02.2006
 * Time: 19:06:20
 */
@Log4j2
@ToString(exclude = {"random"})
@EqualsAndHashCode(exclude = {"random"})
public class WatorModel implements Serializable {

    static final long serialVersionUID = 242L;

    /**
     * List of the Simulated Bacteria Cells.
     */
    private List<Cell> cells;

    /**
     * Start with 20 Cells.
     */
    private final int INITIAL_POPULATION = 20;

    /**
     * Random Generator used for Bacteria Motion.
     */
    private Random random;

    /**
     * Definition of the World's Size in Pixel Width and Height.
     */
    @Getter
    private final WorldPoint worldDimensions;

    /**
     * Map of the World monitoring growth and eating food.
     */
    private final WatorWorldLattice watorWorldLattice;

    @Getter
    private final WatorPopulationCensusContainer watorPopulationCensusContainer;

    @Getter
    private final WatorParameter watorParameter;

    @Getter
    private final ComputerKurzweilProperties computerKurzweilProperties;

    public WatorModel(ComputerKurzweilProperties computerKurzweilProperties) {
        this.computerKurzweilProperties = computerKurzweilProperties;
        int scale = this.computerKurzweilProperties.getSimulatedevolution().getView().getScale();
        int width = scale * this.computerKurzweilProperties.getSimulatedevolution().getView().getWidth();
        int height = scale * this.computerKurzweilProperties.getSimulatedevolution().getView().getHeight();
        this.worldDimensions = new WorldPoint(width,height);
        long seed = new Date().getTime();
        this.random = new Random(seed);
        this.watorWorldLattice = new WatorWorldLattice(
            this.worldDimensions, this.random
        );
        this.watorPopulationCensusContainer = new WatorPopulationCensusContainer(
            computerKurzweilProperties
        );
        this.watorParameter = new WatorParameter();
        this.createPopulation();
    }

    /**
     * Create the initial Population of Bacteria Cells and give them their position in the World.
     */
    private void createPopulation() {
        WatorPopulationCensus populationCensus = new WatorPopulationCensus();
        cells = new ArrayList<Cell>();
        for (int i = 0; i < INITIAL_POPULATION; i++) {
            int x = random.nextInt(worldDimensions.getX());
            int y = random.nextInt(worldDimensions.getY());
            if (x < 0) {
                x *= -1;
            }
            if (y < 0) {
                y *= -1;
            }
            WorldPoint pos = new WorldPoint(x, y);
            Cell cell = new Cell(worldDimensions, pos, random);
            cells.add(cell);
            populationCensus.countStatusOfOneCell(cell.getLifeCycleStatus());
        }
        this.watorPopulationCensusContainer.push(populationCensus);
    }

    /**
     * One Step of Time in the World in which the Population of Bacteria Cell perform Life:
     * Every Cell moves, eats, dies of hunger, and it has sex: splitting into two children with changed DNA.
     */
    public boolean letLivePopulation() {
        WatorPopulationCensus populationCensus = new WatorPopulationCensus();
        watorWorldLattice.letFoodGrow();
        WorldPoint pos;
        List<Cell> children = new ArrayList<Cell>();
        List<Cell> died = new ArrayList<Cell>();
        for (Cell cell:cells) {
            cell.move();
            if(cell.died()){
                died.add(cell);
            } else {
                pos = cell.getPosition();
                int food = watorWorldLattice.eat(pos);
                cell.eat(food);
                if (cell.isPregnant()) {
                    Cell child = cell.performReproductionByCellDivision();
                    children.add(child);
                }
            }
        }
        for(Cell dead:died){
            cells.remove(dead);
        }
        cells.addAll(children);
        for (Cell cell:cells) {
            populationCensus.countStatusOfOneCell(cell.getLifeCycleStatus());
        }
        this.watorPopulationCensusContainer.push(populationCensus);
        return ! cells.isEmpty();
    }

    public List<Cell> getAllCells(){
        return cells;
    }

    public boolean hasFood(int x, int y) {
        return watorWorldLattice.hasFood(x,y);
    }

    public boolean isPopulationAlive() {
        return !cells.isEmpty();
    }
}
