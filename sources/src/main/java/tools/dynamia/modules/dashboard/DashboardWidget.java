
package tools.dynamia.modules.dashboard;

import java.util.Map;

/**
 *
 * @author Mario Serrano Leones
 */
public interface DashboardWidget<V> {

    String getId();

    String getName();

    String getTitle();

    boolean isAsyncSupported();

    boolean isMaximizable();

    boolean isClosable();

    boolean isEditable();

    boolean isTitleVisible();

    void init(DashboardContext context);

    void update(Map<String,Object> params);

    V getView();
}
