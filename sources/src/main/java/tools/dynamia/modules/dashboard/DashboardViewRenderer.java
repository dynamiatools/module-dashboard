package tools.dynamia.modules.dashboard;

import org.zkoss.zul.Div;
import tools.dynamia.actions.ActionLoader;
import tools.dynamia.commons.BeanUtils;
import tools.dynamia.viewers.*;
import tools.dynamia.viewers.util.Viewers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mario Serrano Leones
 */
public class DashboardViewRenderer implements ViewRenderer<List<DashboardWidgetWindow>> {

    private static final int DEFAULT_COLUMNS = 4;

    @Override
    public View<List<DashboardWidgetWindow>> render(ViewDescriptor descriptor, List<DashboardWidgetWindow> value) {
        value = new ArrayList<>();
        ViewLayout layout = descriptor.getLayout();

        int columns = DEFAULT_COLUMNS;

        if (layout.getParams().containsKey(Viewers.LAYOUT_PARAM_COLUMNS)) {
            columns = (int) layout.getParams().get(Viewers.LAYOUT_PARAM_COLUMNS);
        }

        Dashboard dashboard = new Dashboard();

        renderFields(dashboard, descriptor, value, columns);
        loadActions(dashboard);
        dashboard.setValue(value);
        dashboard.initWidgets();
        BeanUtils.setupBean(dashboard, descriptor.getParams());
        return dashboard;
    }

    private void loadActions(Dashboard dashboard) {
        ActionLoader<DashboardAction> loader = new ActionLoader<>(DashboardAction.class);
        loader.load().forEach(dashboard::addAction);

    }

    private int toBootstrapColumns(int columns) {
        return 12 / columns;
    }

    private void renderFields(Dashboard dashboard, ViewDescriptor descriptor, List<DashboardWidgetWindow> value,
                              int columns) {
        int spaceLeft = 12;
        Div row = newRow(dashboard);
        for (Field field : Viewers.getFields(descriptor)) {

            DashboardWidget widget = getWidget(field);
            DashboardWidgetWindow window = new DashboardWidgetWindow(widget, field);
            BeanUtils.setupBean(window, field.getParams());
            value.add(window);
            if (field.getParams().containsKey(Viewers.PARAM_SPAN)) {
                window.setSpan((int) field.getParams().get(Viewers.PARAM_SPAN));
            }
            int realSpan = getRealSpan(window.getSpan(), columns);

            String colxs = "";
            if (field.getParams().containsKey(Viewers.PARAM_SPAN + "-xs")) {
                int spanxs = (int) field.getParams().get(Viewers.PARAM_SPAN + "-xs");
                colxs = " col-xs-" + toBootstrapColumns(spanxs);
            }

            window.setSclass("col-md-" + realSpan + " col-sm-" + realSpan + colxs);
            spaceLeft = spaceLeft - realSpan;
            if (spaceLeft <= 0) {
                spaceLeft = 12;
                row = newRow(dashboard);
            }

            window.setParent(row);
        }
    }

    public Div newRow(Dashboard dashboard) {
        Div row = new Div();

        row.setParent(dashboard);
        return row;
    }

    protected int getRealSpan(int span, int columns) {
        return toBootstrapColumns(columns) * span;

    }

    private DashboardWidget getWidget(Field field) {
        String widgetId = (String) field.getParams().get("widget");
        if (widgetId == null) {
            throw new ViewRendererException("Field " + field.getName() + " dont have widget param");
        }

        DashboardWidget widget = DashboardUtils.getWidgetById(widgetId);
        if (widget == null) {
            throw new ViewRendererException("No widget found with id " + widgetId);
        }
        return widget;
    }

}
