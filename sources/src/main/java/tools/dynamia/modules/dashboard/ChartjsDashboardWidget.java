/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.dashboard;


import tools.dynamia.zk.addons.chartjs.Chartjs;
import tools.dynamia.zk.addons.chartjs.ChartjsData;

/**
 *
 * @author Mario Serrano Leones
 */
public abstract class ChartjsDashboardWidget extends AbstractDashboardWidget<Chartjs> {

    private ChartjsData data;

    @Override
    public void init(DashboardContext context) {
        data = initChartjsData(context);
    }

    @Override
    public Chartjs getView() {
        Chartjs chart = new Chartjs(getChartjsType(), data);
        chart.setTitle(getTitle());
        customizeChart(chart);
        return chart;
    }

    abstract ChartjsData initChartjsData(DashboardContext context);

    abstract String getChartjsType();

    protected void customizeChart(Chartjs chart) {

    }

}
