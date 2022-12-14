package org.woehlke.computer.kurzweil.wator.view.canvas.garden;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.wator.view.WatorFrame;
import org.woehlke.computer.kurzweil.wator.view.tabs.SubTabImpl;
import org.woehlke.computer.kurzweil.wator.view.tabs.TabPanel;
import org.woehlke.computer.kurzweil.wator.model.WatorModel;

import java.io.Serializable;

/**
 * &copy; 2006 - 2008 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/simulated-evolution">Github Repository</a>
 * @see <a href="https://java.woehlke.org/simulated-evolution/">Maven Project Repository</a>
 */
@Log4j2
@Getter
@ToString(callSuper = true)
@Deprecated
public class GardenOfEdenPanelRow extends SubTabImpl implements Serializable {

    static final long serialVersionUID = 242L;

    @ToString.Exclude
    private final WatorFrame tab;
    @ToString.Exclude
    private final WatorModel tabModel;
    private final GardenOfEdenCheckBox gardenOfEdenEnabled;
    private final GardenOfEdenToggleButton buttonToggleGardenOfEden;
    //private final GardenOfEdenPanel gardenOfEdenPanel;

    public GardenOfEdenPanelRow(WatorFrame tab) {
        super("Garden of Eden",tab.getComputerKurzweilProperties());
        this.tab = tab;
        this.tabModel = this.tab.getWatorModel();
        this.gardenOfEdenEnabled = new GardenOfEdenCheckBox(this.tab);
        this.buttonToggleGardenOfEden = new GardenOfEdenToggleButton(this.tab);
        /*
        this.gardenOfEdenPanel = new GardenOfEdenPanel(this.tabCtx);
        this.gardenOfEdenPanel.add(this.gardenOfEdenEnabled);
        this.gardenOfEdenPanel.add(this.buttonToggleGardenOfEden);
        this.add( this.gardenOfEdenPanel);
        */
        this.add(this.gardenOfEdenEnabled);
        this.add(this.buttonToggleGardenOfEden);
    }

    public void setSelected(boolean selected) {
        this.tabModel.getWatorParameter().setGardenOfEdenEnabled(selected);
        update();
    }

    public void toggleGardenOfEden() {
        this.tabModel.getWatorParameter().toggleGardenOfEden();
        update();
    }

    public void addActionListener(TabPanel myTabPanel) {
        this.buttonToggleGardenOfEden.addActionListener(myTabPanel);
    }

    public void update() {
        boolean enabled = tabModel.getWatorParameter().isGardenOfEdenEnabled();
        this.buttonToggleGardenOfEden.setSelected(enabled);
        this.gardenOfEdenEnabled.setSelected(enabled);
    }
}
