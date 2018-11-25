
package tools.dynamia.modules.dashboard;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 *
 * @author Mario Serrano Leones
 */

@Target(ElementType.TYPE)
@Component
@Scope("prototype")
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface InstallDashboardWidget {
    
}
