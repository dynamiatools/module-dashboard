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

import tools.dynamia.actions.ActionEvent;
import tools.dynamia.actions.InstallAction;
import tools.dynamia.commons.BeanUtils;
import tools.dynamia.viewers.ViewCustomizer;

/**
 * Simple action that reload all dashboard widget
 */
@InstallAction
public class ReloadDashboardAction extends DashboardAction {

    public ReloadDashboardAction() {
        setName("Reload");
        setImage("refresh");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Dashboard dashboard = (Dashboard) evt.getData();
        dashboard.initWidgets();

        if (dashboard.getViewDescriptor().getViewCustomizerClass() != null) {
            try {
                ViewCustomizer customizer = BeanUtils.newInstance(dashboard.getViewDescriptor().getViewCustomizerClass());
                customizer.customize(dashboard);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
