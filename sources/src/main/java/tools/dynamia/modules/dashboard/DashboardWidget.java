/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.dashboard;

/**
 *
 * @author mario
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
