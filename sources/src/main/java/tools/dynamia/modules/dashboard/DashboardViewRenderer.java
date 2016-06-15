/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.dashboard;

import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.Div;
import tools.dynamia.commons.BeanUtils;
import tools.dynamia.viewers.Field;
import tools.dynamia.viewers.View;
import tools.dynamia.viewers.ViewDescriptor;
import tools.dynamia.viewers.ViewLayout;
import tools.dynamia.viewers.ViewRenderer;
import tools.dynamia.viewers.ViewRendererException;
import tools.dynamia.viewers.util.Viewers;

/**
 *
 * @author mario
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
        dashboard.setValue(value);
        dashboard.initWidgets();
        return dashboard;
    }

    private int toBootstrapColumns(int columns) {
        return 12 / columns;
    }

    private void renderFields(Dashboard dashboard, ViewDescriptor descriptor, List<DashboardWidgetWindow> value, int columns) {
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
            window.setSclass("col-md-" + realSpan+" col-sm-" + realSpan);
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
