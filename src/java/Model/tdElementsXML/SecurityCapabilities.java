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
public class SecurityCapabilities {
    private List<CryptographicProtocol> CryptographicProtocol;
    
    public SecurityCapabilities(){
        CryptographicProtocol = new ArrayList<>();
    }

    /**
     * @return the CryptographicProtocol
     */
    public List<CryptographicProtocol> getCryptographicProtocol() {
        return CryptographicProtocol;
    }

    /**
     * @param CryptographicProtocol the CryptographicProtocol to set
     */
    public void setCryptographicProtocol(List<CryptographicProtocol> CryptographicProtocol) {
        this.CryptographicProtocol = CryptographicProtocol;
    }
    
}
