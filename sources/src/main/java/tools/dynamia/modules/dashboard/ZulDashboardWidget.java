
package tools.dynamia.modules.dashboard;

/**
 *
 * @author Mario Serrano Leones
 */
public abstract class ZulDashboardWidget extends AbstractDashboardWidget<String> {

    @Override
    public String getView() {
        return getUri();
    }

    public abstract String getUri();

}
