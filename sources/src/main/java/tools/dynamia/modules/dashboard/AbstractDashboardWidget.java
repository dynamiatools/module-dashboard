
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

import java.util.Map;

/**
 * Basic Default Widget implementation
 *
 * @author Mario Serrano Leones
 */
public abstract class AbstractDashboardWidget<V> implements DashboardWidget<V> {

    private String name;
    private String title;
    private boolean titleVisible;
    private boolean editable;
    private boolean closable;
    private boolean maximizable;
    private boolean asyncSupported;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isTitleVisible() {
        return titleVisible;
    }

    public void setTitleVisible(boolean titleVisible) {
        this.titleVisible = titleVisible;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isClosable() {
        return closable;
    }

    public void setClosable(boolean closable) {
        this.closable = closable;
    }

    public boolean isMaximizable() {
        return maximizable;
    }

    public void setMaximizable(boolean maximizable) {
        this.maximizable = maximizable;
    }

    public boolean isAsyncSupported() {
        return asyncSupported;
    }

    public void setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
    }

    @Override
    public void update(Map<String,Object> params) {
        //do nothing by default
    }
}
