/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.dashboard;

import java.util.Optional;
import tools.dynamia.integration.Containers;

/**
 *
 * @author mario
 */
public abstract class DashboardUtils {

    public static DashboardWidget getWidgetById(String widgetId) {
        Optional<DashboardWidget> widget = Containers.get().findObjects(DashboardWidget.class,
                (DashboardWidget object) -> (widgetId.equals(object.getId()))).stream().findFirst();

        if (widget.isPresent()) {
            return widget.get();
        } else {
            return null;
        }
    }

}
