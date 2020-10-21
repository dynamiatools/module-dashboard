/*
 * Copyright (C)  2020. Dynamia Soluciones IT S.A.S - NIT 900302344-1
 * Colombia - South America
 *  All Rights Reserved.
 *
 * This file is free software: you can redistribute it and/or modify it  under the terms of the
 *  GNU Lesser General Public License (LGPL v3) as published by the Free Software Foundation,
 *   either version 3 of the License, or (at your option) any later version.
 *
 *  This file is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *   See the GNU Lesser General Public License for more details. You should have received a copy of the
 *   GNU Lesser General Public License along with this file.
 *   If not, see <https://www.gnu.org/licenses/>.
 *
 */

package tools.dynamia.modules.dashboard;

import org.zkoss.zul.Div;
import tools.dynamia.actions.ActionLoader;
import tools.dynamia.commons.BeanUtils;
import tools.dynamia.viewers.*;
import tools.dynamia.viewers.util.Viewers;

import java.util.ArrayList;
import java.util.List;

/**
 * Render {@link ViewDescriptor} of type 'dashboard' @{@link DashboardViewType}
 *
 * @author Mario Serrano Leones
 */
public class DashboardViewRenderer implements ViewRenderer<List<DashboardWidgetWindow>> {

    private static final int DEFAULT_COLUMNS = 4;

    @Override
    public View<List<DashboardWidgetWindow>> render(ViewDescriptor descriptor, List<DashboardWidgetWindow> value) {
        System.out.println("Rendering dashboard " + descriptor);
        value = new ArrayList<>();
        ViewLayout layout = descriptor.getLayout();

        int columns = DEFAULT_COLUMNS;

        if (layout.getParams().containsKey(Viewers.LAYOUT_PARAM_COLUMNS)) {
            columns = (int) layout.getParams().get(Viewers.LAYOUT_PARAM_COLUMNS);
        }

        Dashboard dashboard = new Dashboard();
        dashboard.setViewDescriptor(descriptor);
        renderFields(dashboard, descriptor, value, columns);
        loadActions(dashboard);
        dashboard.setValue(value);
        BeanUtils.setupBean(dashboard, descriptor.getParams());
        dashboard.initWidgets();
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
            window.showLoading();
            value.add(window);
            if (field.getParams().containsKey(Viewers.PARAM_SPAN)) {
                window.setSpan((int) field.getParams().get(Viewers.PARAM_SPAN));
            }
            int realSpan = getRealSpan(window.getSpan(), columns);

            String colxs = "";
            if (field.getParams().containsKey(Viewers.PARAM_SPAN + "-xs")) {
                int spanxs = (int) field.getParams().get(Viewers.PARAM_SPAN + "-xs");
                colxs = " col-" + toBootstrapColumns(spanxs);
            }

            int tabletColSpan = 6;
            try {
                if (field.getParams().containsKey(Viewers.PARAM_SPAN + "-sm")) {
                    tabletColSpan = Integer.parseInt(field.getParams().get(Viewers.PARAM_SPAN + "-sm").toString());
                    tabletColSpan = getRealSpan(tabletColSpan, columns);
                }
            } catch (Exception e) {
            }

            window.setSclass("col-md-" + realSpan + " col-sm-" + tabletColSpan + colxs);
            spaceLeft = spaceLeft - realSpan;
            window.setParent(row);
            if (spaceLeft <= 0) {
                spaceLeft = 12;
                row = newRow(dashboard);
            }


        }
    }

    public Div newRow(Dashboard dashboard) {
        Div row = new Div();
        row.setZclass("row");
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
