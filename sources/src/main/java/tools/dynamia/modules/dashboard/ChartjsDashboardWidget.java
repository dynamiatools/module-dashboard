package tools.dynamia.modules.dashboard;


import tools.dynamia.zk.addons.chartjs.Chartjs;
import tools.dynamia.zk.addons.chartjs.ChartjsData;

/**
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

        customizeChart(chart);
        return chart;
    }

    public abstract ChartjsData initChartjsData(DashboardContext context);

    public abstract String getChartjsType();

    protected void customizeChart(Chartjs chart) {

    }

}
