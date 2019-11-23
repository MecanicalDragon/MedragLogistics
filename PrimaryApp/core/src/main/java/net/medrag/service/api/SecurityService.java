package net.medrag.service.api;

/**
 * Security API for working with SecurityContextHolder.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface SecurityService {

    /**
     * Getting role of signed in user.
     *
     * @return - stringified role.
     */
    String getRoleOfSignedInUser();

    /**
     * Getting username of signed in user.
     *
     * @return - username of signed in user.
     */
    String getUsernameOfSignedInUser();
}
