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
public class ReportDeliveryModes {
    private List<DeliveryMode> DeliveryMode = new ArrayList<>();

    /**
     * @return the DeliveryMode
     */
    public List<DeliveryMode> getDeliveryMode() {
        return DeliveryMode;
    }

    /**
     * @param DeliveryMode the DeliveryMode to set
     */
    public void setDeliveryMode(List<DeliveryMode> DeliveryMode) {
        this.DeliveryMode = DeliveryMode;
    }
}
