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
public class PermissionOperationModes {
    private List<PermissionMode> PermissionMode = new ArrayList<>();

    /**
     * @return the PermissionMode
     */
    public List<PermissionMode> getPermissionMode() {
        return PermissionMode;
    }

    /**
     * @param PermissionMode the PermissionMode to set
     */
    public void setPermissionMode(List<PermissionMode> PermissionMode) {
        this.PermissionMode = PermissionMode;
    }
}
