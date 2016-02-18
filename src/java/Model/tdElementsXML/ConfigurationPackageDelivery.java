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
public class ConfigurationPackageDelivery {
    private List<ConfigurationMode> ConfigurationMode = new ArrayList<>();

    /**
     * @return the ConfigurationMode
     */
    public List<ConfigurationMode> getConfigurationMode() {
        return ConfigurationMode;
    }

    /**
     * @param ConfigurationMode the ConfigurationMode to set
     */
    public void setConfigurationMode(List<ConfigurationMode> ConfigurationMode) {
        this.ConfigurationMode = ConfigurationMode;
    }
}
