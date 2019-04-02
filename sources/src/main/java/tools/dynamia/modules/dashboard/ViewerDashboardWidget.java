package tools.dynamia.modules.dashboard;

import tools.dynamia.zk.viewers.ui.Viewer;

/**
 * @author Mario Serrano Leones
 */
public abstract class ViewerDashboardWidget extends AbstractDashboardWidget<Viewer> {

    private Object viewValue;

    public abstract String getViewDescriptorId();

    public abstract String getViewType();

    public abstract Object initViewValue(DashboardContext context);

    @Override
    public void init(DashboardContext context) {
        viewValue = initViewValue(context);
    }

    @Override
    public Viewer getView() {
        Viewer viewer = new Viewer();
        viewer.setDescriptorId(getViewDescriptorId());
        if (viewer.getDescriptorId() == null) {
            viewer.setViewType(getViewType());
        }
        viewer.setVflex("1");
        viewer.setContentVflex("1");
        viewer.setValue(viewValue);

        return viewer;
    }

}
