/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.tdElementsXML;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Principal
 */
public class MeasurementTriggerMethod {

    private List<EventTrigger> EventTrigger = new ArrayList<>();
    private List<TimeSampleTrigger> TimeSampleTrigger = new ArrayList<>();

    private List<ServiceStartTrigger> ServiceStartTrigger = new ArrayList<>();


    /**
     * @return the EventTrigger
     */
    public List<EventTrigger> getEventTrigger() {
        return EventTrigger;
    }

    /**
     * @param EventTrigger the EventTrigger to set
     */
    public void setEventTrigger(List<EventTrigger> EventTrigger) {
        this.EventTrigger = EventTrigger;
    }

    /**
     * @return the TimeSampleTrigger
     */
    public List<TimeSampleTrigger> getTimeSampleTrigger() {
        return TimeSampleTrigger;
    }

    /**
     * @param TimeSampleTrigger the TimeSampleTrigger to set
     */
    public void setTimeSampleTrigger(List<TimeSampleTrigger> TimeSampleTrigger) {
        this.TimeSampleTrigger = TimeSampleTrigger;
    }

    /**
     * @return the ServiceStartTrigger
     */
    public List<ServiceStartTrigger> getServiceStartTrigger() {
        return ServiceStartTrigger;
    }

    /**
     * @param ServiceStartTrigger the ServiceStartTrigger to set
     */
    public void setServiceStartTrigger(List<ServiceStartTrigger> ServiceStartTrigger) {
        this.ServiceStartTrigger = ServiceStartTrigger;
    }

}
