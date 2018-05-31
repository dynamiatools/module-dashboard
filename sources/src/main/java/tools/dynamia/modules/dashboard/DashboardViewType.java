/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.dashboard;

import org.springframework.stereotype.Component;
import tools.dynamia.viewers.ViewRenderer;
import tools.dynamia.viewers.ViewType;

/**
 *
 * @author mario
 */
@Component
public class DashboardViewType implements ViewType {

    public static final String NAME = "dashboard";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ViewRenderer getViewRenderer() {
        return new DashboardViewRenderer();
    }

}
