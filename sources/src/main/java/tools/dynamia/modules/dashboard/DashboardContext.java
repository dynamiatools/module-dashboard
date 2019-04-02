
package tools.dynamia.modules.dashboard;

import java.util.HashMap;
import java.util.Map;

import tools.dynamia.integration.Containers;
import tools.dynamia.viewers.Field;

/**
 * @author Mario Serrano Leones
 */
public class DashboardContext {

    private Dashboard dashboard;
    private DashboardWidgetWindow window;
    private Map<String, Object> data = new HashMap<>();
    private Field field;

    public DashboardContext(Dashboard dashboard, DashboardWidgetWindow window, Field field) {
        this.dashboard = dashboard;
        this.window = window;
        this.field = field;
    }

    public DashboardWidgetWindow getWindow() {
        return window;
    }

    public void add(String name, Object value) {
        data.put(name, value);
    }

    public Object get(String name) {
        return data.get(name);
    }

    public Map<String, Object> getDataMap() {
        return data;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public Field getField() {
        return field;
    }

    /**
     * Find user info provider or null if not found
     * @return
     */
    public UserInfoProvider findUserInfo() {
        return Containers.get().findObject(UserInfoProvider.class);
    }

}
