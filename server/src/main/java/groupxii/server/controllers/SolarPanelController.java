package groupxii.server.controllers;

import groupxii.database.PanelEntry;
import groupxii.solarpanels.Panel;
import groupxii.solarpanels.PanelCalculations;
import groupxii.solarpanels.PanelData;
import groupxii.solarpanels.PanelNameList;
import groupxii.solarpanels.SavePanel;
import groupxii.solarpanels.UsedPanelList;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class SolarPanelController {

    private SavePanel savePanel = new SavePanel();
    private PanelCalculations panelCalculations = new PanelCalculations();
    private PanelData panelData = new PanelData();
    private List<Panel> panelList = new ArrayList<Panel>();
    private final AtomicLong counter = new AtomicLong();
    private List<String> usedPanelList = new ArrayList<String>();

    /**
     First run this to load in the PanelDataList on the server,
     this has only to be done once the server starts.
     in the future we will load this also on the boot of the server.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void setPanelData() throws IOException {
        panelData.readPanelListData();
        this.panelList = panelData.getPanelList();
    }

    /**
     * This method will return the List of panels which are requested.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getPanelData")
    public List<Panel> getPanelData() {
        return panelData.getPanelList();
    }

    /**
     * This method will return the panelEntry which is saved.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getPanelEntry")
    public PanelEntry getPanelEntry() {
        return savePanel.getPanelEntry();
    }

    /**
     This method will transform the data from the panelList into one string, which then can be used
     by the client, so the choice boxes/list views in the GUI are able to show all the panel names.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/panelNameList")
    public String getNameList() {
        PanelNameList panelNameList = new PanelNameList();
        panelNameList.setPanelList(this.panelList);
        String panelNameListString = panelNameList.getPanelNameList();
        return panelNameListString;
    }

    /**
     the client can send data to the server with the right values as parameter,
     then this method will store the data in the database.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/savePanelData")
    public void savePanelData(@RequestParam(value = "paneltype",
            defaultValue = "Unknown") String paneltype, @RequestParam(value = "efficiencyrate",
            defaultValue = "0") int efficiencyrate, @RequestParam(value = "amount",
            defaultValue = "0") int amount) throws IOException {
        int reducedCO2 = getReducedCO2(paneltype);
        savePanel.setPanelList(this.panelList);
        savePanel.savePanelData(counter.incrementAndGet(), paneltype,
                reducedCO2 , efficiencyrate, amount);

    }

    /**
     * This method will return a String with used Panels in json format.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/usedPanelList")
    public String getUsedPanelList() throws IOException {
        UsedPanelList usedPanelListclass = new UsedPanelList();
        usedPanelListclass.readDatabase();
        String jsonReturn = "";
        usedPanelList = usedPanelListclass.getUsedPanelList();
        for (int i = 0; i < usedPanelList.size(); i++ ) {
            jsonReturn += usedPanelList.get(i) + " - ";
        }
        return jsonReturn;
    }

    /**
     * get method for the reduced co2.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getReducedCO2")
    public int getReducedCO2(@RequestParam(value = "paneltype",
            defaultValue = "Unknown") String paneltype) throws IOException {
        panelCalculations.setPanelList(this.panelList);

        int reducedCO2 =  panelCalculations.calculateCO2(paneltype);
        return reducedCO2;
    }
}