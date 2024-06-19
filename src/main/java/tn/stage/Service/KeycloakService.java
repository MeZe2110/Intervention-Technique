package tn.stage.Service;

import jakarta.enterprise.context.ApplicationScoped;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import tn.stage.Entity.Role;
import tn.stage.Entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class KeycloakService {

    public final Keycloak keycloak;

    // Constructor to build the Keycloak instance with the needed parameters
    public KeycloakService() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl("http://127.0.0.1:8081") // Set the Keycloak server URL
                .realm("BWS-Suivi-Technicien") // Set the realm
                .grantType(OAuth2Constants.PASSWORD) // Set the grant type for authentication
                .username("meza") // Set the username for authentication
                .password("meza") // Set the password for authentication
                .clientId("admin-cli") // Set the client ID
                .clientSecret("4sx2owO4B0ALiMMAl6dzh2LIZIAUR8cF") // Set the client secret
                .build(); // Build the Keycloak instance
    }

    // Return the list of all users in the realm "BWS-Suivi-Technicien"
    public List<User> getAllUsers() {
        // Fetch all user representations from the realm
        List<UserRepresentation> userRepresentations = keycloak.realm("BWS-Suivi-Technicien").users().list();
        // Map the user representations to a list of User objects
        return mapUsers(userRepresentations);
    }

    // Return the user of a defined ID
    public User getUser(String id) {
        // Fetch the user representation by ID and map it to a User object
        return mapUser(keycloak.realm("BWS-Suivi-Technicien").users().get(id).toRepresentation());
    }

    // Create a new user
    public User createUser(User user) {
        // Map the User object to a UserRepresentation
        UserRepresentation userRep = mapUserRep(user);
        // Create the user in the realm
        keycloak.realm("BWS-Suivi-Technicien").users().create(userRep);
        return user; // Return the created User object
    }

    // Assign a role to a user
    public void assignRoleToUser(String id, String roleName) {
        // Fetch the role representation by name
        RoleRepresentation role = keycloak.realm("BWS-Suivi-Technicien").roles().get(roleName).toRepresentation();
        // Assign the role to the user by ID
        keycloak.realm("BWS-Suivi-Technicien").users().get(id).roles().realmLevel().add(Arrays.asList(role));
    }

    // Update a user
    public User updateUser(User user) {
        // Map the User object to a UserRepresentation
        UserRepresentation userRep = mapUserRep(user);
        // Update the user in the realm by ID
        keycloak.realm("BWS-Suivi-Technicien").users().get(user.getId()).update(userRep);
        return user; // Return the updated User object
    }

    // Delete a user
    public void deleteUser(String id) {
        // Delete the user by ID from the realm
        keycloak.realm("BWS-Suivi-Technicien").users().delete(id);
    }

    // Map a list of UserRepresentation objects to a list of User objects
    private List<User> mapUsers(List<UserRepresentation> userRepresentations) {
        List<User> users = new ArrayList<>(); // Initialize an empty list of User objects
        // Check if the list of user representations is not empty
        if (CollectionUtil.isNotEmpty(userRepresentations)) {
            // Iterate over each user representation and map it to a User object
            userRepresentations.forEach(userRep -> {
                users.add(mapUser(userRep));
            });
        }
        return users; // Return the list of mapped User objects
    }


    // Maps a UserRepresentation object to a User object
    private User mapUser(UserRepresentation userRep) {
        User user = new User();  // Create a new User instance
        user.setId(userRep.getId());  // Set the ID from the UserRepresentation
        user.setFirstName(userRep.getFirstName());  // Set the first name from the UserRepresentation
        user.setLastName(userRep.getLastName());  // Set the last name from the UserRepresentation
        user.setEmail(userRep.getEmail());  // Set the email from the UserRepresentation
        user.setUserName(userRep.getUsername());  // Set the username from the UserRepresentation

        // Fetch and map the roles associated with the user from Keycloak
        user.setRoles(mapRoles(keycloak.realm("BWS-Suivi-Technicien").users()
                .get(user.getId()).roles().realmLevel().listEffective()
        ));

        return user;  // Return the mapped User object
    }

    // Maps a User object to a UserRepresentation object
    private UserRepresentation mapUserRep(User user) {
        UserRepresentation userRep = new UserRepresentation();  // Create a new UserRepresentation instance
        userRep.setId(user.getId());  // Set the ID from the User
        userRep.setFirstName(user.getFirstName());  // Set the first name from the User
        userRep.setLastName(user.getLastName());  // Set the last name from the User
        userRep.setEmail(user.getEmail());  // Set the email from the User
        userRep.setUsername(user.getUserName());  // Set the username from the User
        userRep.setEnabled(true);  // Set the user as enabled
        userRep.setEmailVerified(true);  // Set the email as verified

        // Create and set credentials for the UserRepresentation
        List<CredentialRepresentation> creds = new ArrayList<>();
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setTemporary(false);  // Set the credential as not temporary
        cred.setValue(user.getPassword());  // Set the credential value (password) from the User
        creds.add(cred);  // Add the credential to the list
        userRep.setCredentials(creds);  // Set the credentials to the UserRepresentation

        return userRep;  // Return the mapped UserRepresentation object
    }

    // Define the allowed roles for filtering
    private static final Set<String> ALLOWED_ROLES = Set.of("USER", "ADMIN", "MANAGER");

    // Maps a list of RoleRepresentation objects to a list of Role objects, filtering by allowed roles
    private List<Role> mapRoles(List<RoleRepresentation> representations) {
        List<Role> roles = new ArrayList<>();  // Initialize an empty list of Role objects

        // Check if the representations list is not empty
        if (CollectionUtil.isNotEmpty(representations)) {
            roles = representations.stream()
                    .filter(roleRep -> ALLOWED_ROLES.contains(roleRep.getName()))  // Filter the roles by allowed roles
                    .map(this::mapRole)  // Map each RoleRepresentation to a Role object
                    .collect(Collectors.toList());  // Collect the results into a list
        }

        return roles;  // Return the mapped and filtered list of Role objects
    }

    // Maps a RoleRepresentation object to a Role object
    private Role mapRole(RoleRepresentation roleRep) {
        Role role = new Role();  // Create a new Role instance
        role.setName(roleRep.getName());  // Set the role name from the RoleRepresentation
        return role;  // Return the mapped Role object
    }


}
