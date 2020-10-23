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

import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import tools.dynamia.actions.Action;
import tools.dynamia.actions.ActionEvent;
import tools.dynamia.commons.logger.LoggingService;
import tools.dynamia.commons.logger.SLF4JLoggingService;
import tools.dynamia.ui.UIMessages;
import tools.dynamia.viewers.View;
import tools.dynamia.viewers.ViewDescriptor;
import tools.dynamia.zk.actions.ActionToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Dashboard main view. Need to implement a {@link ViewDescriptor} in YML or XML to describe dashboard widget. See {@link DashboardViewRenderer}
 *
 * @author Mario Serrano Leones
 */
public class Dashboard extends Div implements View<List<DashboardWidgetWindow>>, IdSpace {


    public static final String COMMAND = "dashboard-loaded";
    private LoggingService logger = new SLF4JLoggingService(Dashboard.class);
    private ViewDescriptor viewDescriptor;
    private View parentView;
    private List<DashboardWidgetWindow> value = new ArrayList<>();
    private ActionToolbar actionToolbar;
    private boolean loaded;
    private boolean rendered;
    private boolean asyncLoad = true;


    public Dashboard() {
        setSclass("dashboard");
        actionToolbar = new ActionToolbar((source, params) -> new ActionEvent(Dashboard.this, Dashboard.this));
        appendChild(actionToolbar);
    }

    public void initWidgets() {
        this.loaded = false;
        this.rendered = false;

        if (isAsyncLoad()) {
            if (EventQueues.exists(viewDescriptor.getId())) {
                UIMessages.showMessage("Cargando Dashboard.. espere");
                return; //busy
            }

            var queue = EventQueues.lookup(viewDescriptor.getId());
            queue.subscribe(op -> loadWidgets(), callback -> {
                renderWidgets();
                EventQueues.remove(viewDescriptor.getId());
            });

            queue.publish(new Event("start"));
        } else {
            addEventListener(Events.ON_FULFILL, evt -> {


                for (DashboardWidgetWindow window : value) {
                    try {
                        new DashboardContext(this, window, window.getField());
                        window.initWidget();
                        window.initView();
                    } catch (Exception e) {
                        logger.error("Error loading dashboard widget -  " + window.getWidget(), e);
                        window.exceptionCaught(e);
                    }
                }
                loaded = true;
                rendered = true;
            });
            Events.postEvent(new Event(Events.ON_FULFILL, this));
        }

    }

    private void loadWidgets() {
        logger.info("Loading dashboard widgets ");
        for (DashboardWidgetWindow window : value) {
            try {
                new DashboardContext(this, window, window.getField());
                window.initWidget();
            } catch (Exception e) {
                logger.error("Error loading dashboard widget -  " + window.getWidget(), e);
                window.exceptionCaught(e);
            }
        }
        this.loaded = true;
        logger.info("Dashboard " + getViewDescriptor().getId() + " Loaded");
    }


    public void renderWidgets() {
        try {
            for (DashboardWidgetWindow window : value) {
                try {
                    new DashboardContext(this, window, window.getField());
                    window.initView();
                } catch (Exception e) {
                    window.exceptionCaught(e);
                    window.initView();
                    logger.error("Error rendering dashboard widget -  " + window.getWidget(), e);
                }
            }
            this.rendered = true;
            logger.info("Dashboard Rendered");
        } catch (Exception e) {
            e.printStackTrace();
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

    public boolean isLoaded() {
        return loaded;
    }

    public boolean isRendered() {
        return rendered;
    }

    public boolean isAsyncLoad() {
        return asyncLoad;
    }

    public void setAsyncLoad(boolean asyncLoad) {
        this.asyncLoad = asyncLoad;
    }
}
