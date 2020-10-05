
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

import java.util.HashMap;
import java.util.Map;

import tools.dynamia.integration.Containers;
import tools.dynamia.viewers.Field;

/**
 * Dashboard context
 *
 * @author Mario Serrano Leones
 */
public class DashboardContext {

    private Dashboard dashboard;
    private DashboardWidgetWindow window;
    private Map<String, Object> data = new HashMap<>();
    private Field field;

    public DashboardContext(Dashboard dashboard, DashboardWidgetWindow window, Field field) {
        this.dashboard = dashboard;
        this.window = window;
        this.window.setDashboardContext(this);
        this.field = field;
    }

    public DashboardWidgetWindow getWindow() {
        return window;
    }

    public void add(String name, Object value) {
        data.put(name, value);
    }

    public Object get(String name) {
        return data.get(name);
    }

    public Map<String, Object> getDataMap() {
        return data;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public Field getField() {
        return field;
    }

    /**
     * Find user info provider or null if not found
     *
     * @return
     */
    public UserInfoProvider findUserInfo() {
        return Containers.get().findObject(UserInfoProvider.class);
    }

}
