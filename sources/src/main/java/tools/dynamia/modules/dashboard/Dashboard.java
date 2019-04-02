package tools.dynamia.modules.dashboard;

import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import tools.dynamia.actions.Action;
import tools.dynamia.actions.ActionEvent;
import tools.dynamia.viewers.View;
import tools.dynamia.viewers.ViewDescriptor;
import tools.dynamia.zk.actions.ActionToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Mario Serrano Leones
 */
public class Dashboard extends Div implements View<List<DashboardWidgetWindow>> {

    private ViewDescriptor viewDescriptor;
    private View parentView;
    private List<DashboardWidgetWindow> value = new ArrayList<>();
    private ActionToolbar actionToolbar;

    public Dashboard() {
        setSclass("dashboard");
        actionToolbar = new ActionToolbar((source, params) -> new ActionEvent(Dashboard.this, Dashboard.this));
        appendChild(actionToolbar);
    }

    public void initWidgets() {

        for (DashboardWidgetWindow window : value) {
            DashboardContext context = new DashboardContext(this, window, window.getField());
            try {
                window.getWidget().init(context);
                window.initView(context);
            } catch (Exception e) {
                e.printStackTrace();
                window.getContent().getChildren().clear();
                window.getContent().appendChild(new Label("Error init widget for " + window.getField().getName() + ": " + e.getMessage()));
            }
        }
    }

    public void updateWidgets(Map<String, Object> params) {

        for (DashboardWidgetWindow window : value) {
            window.getWidget().update(params);
        }
    }


    @Override
    public ViewDescriptor getViewDescriptor() {
        return viewDescriptor;
    }

    @Override
    public void setViewDescriptor(ViewDescriptor viewDescriptor) {
        this.viewDescriptor = viewDescriptor;
    }

    @Override
    public View getParentView() {
        return parentView;
    }

    @Override
    public void setParentView(View parentView) {
        this.parentView = parentView;
    }

    @Override
    public List<DashboardWidgetWindow> getValue() {
        return value;
    }

    @Override
    public void setValue(List<DashboardWidgetWindow> value) {
        this.value = value;
    }

    public DashboardWidgetWindow getWidgetWindow(String name) {
        Optional<DashboardWidgetWindow> windows = value.stream().filter(w -> w.getField().getName().equals(name))
                .findFirst();
        return windows.orElse(null);
    }

    public ActionToolbar getActionToolbar() {
        return actionToolbar;
    }

    public void addAction(Action action) {
        actionToolbar.addAction(action);
    }

    public void setActionsVisible(boolean visible) {
        actionToolbar.setVisible(visible);
    }

    public boolean isActionsVisible() {
        return actionToolbar.isVisible();
    }

}
