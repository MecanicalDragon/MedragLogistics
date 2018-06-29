package net.medrag.model.service;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface SecurityService {

    String getRoleOfSignedInUser();
    String getUsernameOfSignedInUser();
//    void autoLogin(String username, String password);
}
