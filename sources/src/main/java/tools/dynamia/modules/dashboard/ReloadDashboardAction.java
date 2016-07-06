package tools.dynamia.modules.dashboard;

import tools.dynamia.actions.ActionEvent;
import tools.dynamia.actions.InstallAction;
import tools.dynamia.commons.BeanUtils;
import tools.dynamia.viewers.ViewCustomizer;

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
		
		if(dashboard.getViewDescriptor().getViewCustomizerClass()!=null){
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
