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
public class TransportCapabilitiesList {
    private List<TransportProtocolMode> TransportProtocolMode; 
    
    public TransportCapabilitiesList(){
        TransportProtocolMode = new ArrayList<>();
    }

    /**
     * @return the TransportProtocolMode
     */
    public List<TransportProtocolMode> getTransportProtocolMode() {
        return TransportProtocolMode;
    }

    /**
     * @param TransportProtocolMode the TransportProtocolMode to set
     */
    public void setTransportProtocolMode(List<TransportProtocolMode> TransportProtocolMode) {
        this.TransportProtocolMode = TransportProtocolMode;
    }

}
