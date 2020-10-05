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

import tools.dynamia.zk.viewers.ui.Viewer;

/**
 * Extend this class if you need to render widgets that use another {@link tools.dynamia.viewers.View}
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
