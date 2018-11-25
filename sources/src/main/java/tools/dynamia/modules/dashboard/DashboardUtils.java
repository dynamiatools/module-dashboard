
package tools.dynamia.modules.dashboard;

import java.util.Optional;
import tools.dynamia.integration.Containers;

/**
 *
 * @author Mario Serrano Leones
 */
public abstract class DashboardUtils {

    public static DashboardWidget getWidgetById(String widgetId) {
        Optional<DashboardWidget> widget = Containers.get().findObjects(DashboardWidget.class,
                (DashboardWidget object) -> (widgetId.equals(object.getId()))).stream().findFirst();

        return widget.orElse(null);
    }

}
