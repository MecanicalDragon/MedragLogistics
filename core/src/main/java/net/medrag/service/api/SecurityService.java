package net.medrag.service.api;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface SecurityService {

    String getRoleOfSignedInUser();
    String getUsernameOfSignedInUser();
}
