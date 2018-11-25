
package tools.dynamia.modules.dashboard;

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

    V getView();
}
