/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.dashboard;

import java.util.HashMap;
import java.util.Map;
import tools.dynamia.viewers.Field;

/**
 *
 * @author Mario Serrano Leones
 */
public class DashboardContext {

    private Dashboard dashboard;
    private Map<String, Object> data = new HashMap<>();
    private Field field;

    public DashboardContext(Dashboard dashboard, Field field) {
        this.dashboard = dashboard;
        this.field = field;
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

}
