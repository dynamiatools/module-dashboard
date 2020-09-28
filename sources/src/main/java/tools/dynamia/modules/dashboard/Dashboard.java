package tools.dynamia.modules.dashboard;

import org.zkoss.bind.sys.BinderCtrl;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import tools.dynamia.actions.Action;
import tools.dynamia.actions.ActionEvent;
import tools.dynamia.commons.logger.LoggingService;
import tools.dynamia.commons.logger.SLF4JLoggingService;
import tools.dynamia.integration.scheduling.SchedulerUtil;
import tools.dynamia.ui.UIMessages;
import tools.dynamia.viewers.View;
import tools.dynamia.viewers.ViewDescriptor;
import tools.dynamia.zk.EventQueueSubscriber;
import tools.dynamia.zk.Subscribe;
import tools.dynamia.zk.actions.ActionToolbar;
import tools.dynamia.zk.util.LongOperation;
import tools.dynamia.zk.util.ZKBindingUtil;
import tools.dynamia.zk.util.ZKUtil;
import tools.dynamia.zk.websocket.WebSocketPushSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
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


    public Dashboard() {
        setSclass("dashboard");
        actionToolbar = new ActionToolbar((source, params) -> new ActionEvent(Dashboard.this, Dashboard.this));
        appendChild(actionToolbar);
    }

    public void initWidgets() {
        WebSocketPushSender.initWS();
        this.loaded = false;
        this.rendered = false;
        var desktop = Executions.getCurrent().getDesktop();

        if (EventQueues.exists("dashboard")) {
            UIMessages.showMessage("Cargando Dashboard.. espere");
            return; //busy
        }

        var queue = EventQueues.lookup("dashboard");
        queue.subscribe(op -> loadWidgets(), callback -> {
            renderWidgets();
            EventQueues.remove("dashboard");
        });

        queue.publish(new Event("start"));

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
}
