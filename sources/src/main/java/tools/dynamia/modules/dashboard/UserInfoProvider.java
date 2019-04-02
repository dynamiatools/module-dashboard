package tools.dynamia.modules.dashboard;

import java.util.List;

public interface UserInfoProvider {

    String getUsername();

    boolean isAdmin();

    boolean hasRole(String roleName);

    boolean hasGrant(String grant);

    Long getUserLocation();

    List<String> getUserRoles();
}
