/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.dashboard;

import tools.dynamia.zk.viewers.ui.Viewer;

/**
 *
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
        viewer.setValue(viewValue);

        return viewer;
    }

}
