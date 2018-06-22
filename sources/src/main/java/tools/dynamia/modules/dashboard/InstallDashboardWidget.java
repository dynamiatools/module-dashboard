/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
