
package tools.dynamia.modules.dashboard;

import org.springframework.stereotype.Component;
import tools.dynamia.viewers.ViewRenderer;
import tools.dynamia.viewers.ViewType;

/**
 *
 * @author Mario Serrano Leones
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
