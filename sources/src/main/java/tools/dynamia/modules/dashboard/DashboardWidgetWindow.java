/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.dashboard;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;
import tools.dynamia.viewers.Field;
import tools.dynamia.zk.util.ZKUtil;

/**
 *
 * @author mario
 */
public class DashboardWidgetWindow extends Div {

    public static final String ON_EDIT = "onEdit";
    private DashboardWidget widget;
    private Field field;
    private boolean editable;
    private int span = 1;
    private Window content;

    public DashboardWidgetWindow(DashboardWidget widget, Field field) {
        setZclass("dashboard-widget");

        this.widget = widget;
        this.field = field;
        this.content = new Window();
        this.content.setParent(this);

        if (widget.isTitleVisible()) {
            content.setClosable(widget.isClosable());
            content.setMaximizable(widget.isMaximizable());
            setEditable(widget.isEditable());
            content.setTitle(widget.getTitle());
        }

    }

    public void initView(DashboardContext context) {
        content.getChildren().clear();
        renderTitle();

        Object view = widget.getView();
        if (view instanceof Component) {
            ((Component) view).setParent(content);
        } else if (view instanceof String) {
            String url = (String) view;
            ZKUtil.createComponent(url, content, context.getDataMap());
        }
    }

    private void renderTitle() {
        if (widget.isTitleVisible()) {
            Caption caption = new Caption(widget.getTitle());
            caption.setParent(content);
            if (isEditable()) {
                Button btn = new Button();
                btn.setIconSclass("z-icon-edit");
                btn.setParent(caption);
                btn.addEventListener(Events.ON_CLICK, evt -> Events.postEvent(new Event(ON_EDIT, this, widget)));
            }
        }
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        if (span <= 0) {
            span = 1;
        } else if (span > 12) {
            span = 12;
        }
        this.span = span;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public DashboardWidget getWidget() {
        return widget;
    }

    public Field getField() {
        return field;
    }

    public Window getContent() {
        return content;
    }

}
