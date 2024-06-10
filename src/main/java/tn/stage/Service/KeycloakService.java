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

    private final Keycloak keycloak;

    public KeycloakService() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl("http://127.0.0.1:8081")
                .realm("BWS-Suivi-Technicien")
                .grantType(OAuth2Constants.PASSWORD)
                .username("meza")
                .password("meza")
                .clientId("admin-cli")
                .clientSecret("4sx2owO4B0ALiMMAl6dzh2LIZIAUR8cF")
                .build();
    }
  

    public List<User> getAllUsers() {
         List<UserRepresentation> userRepresentations = keycloak.realm("BWS-Suivi-Technicien").users().list();
         return mapUsers(userRepresentations);
    }
    public User getUser(String id) {
        return mapUser(keycloak.realm("BWS-Suivi-Technicien").users().get(id).toRepresentation());
    }

    public  User createUser(User user){
        UserRepresentation userRep = mapUserRep(user);
        keycloak.realm("BWS-Suivi-Technicien").users().create(userRep);
        return user;
    }

    public void assignRoleToUser(String id, String roleName){
        RoleRepresentation role = keycloak.realm("BWS-Suivi-Technicien").roles().get(roleName).toRepresentation();
        keycloak.realm("BWS-Suivi-Technicien").users().get(id).roles().realmLevel().add(Arrays.asList(role));

    }

    public  User updateUser(User user){
        UserRepresentation userRep = mapUserRep(user);
        keycloak.realm("BWS-Suivi-Technicien").users().get(user.getId()).update(userRep);
        return user;
    }

    public  void deleteUser(String id){
        keycloak.realm("BWS-Suivi-Technicien").users().delete(id);

    }

    private List<User> mapUsers(List<UserRepresentation> userRepresentations) {
        List<User> users = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(userRepresentations)){
            userRepresentations.forEach(userRep -> {
                users.add(mapUser(userRep));
            });
        }
        return users;
    }

    private User mapUser(UserRepresentation userRep) {
        User user = new User();
        user.setId(userRep.getId());
        user.setFirstName(userRep.getFirstName());
        user.setLastName(userRep.getLastName());
        user.setEmail(userRep.getEmail());
        user.setUserName(userRep.getUsername());
        user.setRoles(mapRoles(keycloak.realm("BWS-Suivi-Technicien").users()
                .get(user.getId()).roles().realmLevel().listEffective()
        ));
        return user;
    }

    private UserRepresentation mapUserRep(User user){
        UserRepresentation userRep = new UserRepresentation();
        userRep.setId(user.getId());
        userRep.setFirstName(user.getFirstName());
        userRep.setLastName(user.getLastName());
        userRep.setEmail(user.getEmail());
        userRep.setUsername(user.getUserName());
        userRep.setEnabled(true);
        userRep.setEmailVerified(true);
        List<CredentialRepresentation> creds = new ArrayList<>();
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setTemporary(false);
        cred.setValue(user.getPassword());
        creds.add(cred);
        userRep.setCredentials(creds);
        return userRep;
    }
    private static final Set<String> ALLOWED_ROLES = Set.of("USER", "ADMIN", "MANAGER");

    private  List<Role> mapRoles(List<RoleRepresentation> representations) {
        List<Role> roles = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(representations)) {
            roles = representations.stream()
                    .filter(roleRep -> ALLOWED_ROLES.contains(roleRep.getName())) // Filter roles
                    .map(this::mapRole) // Map to Role objects
                    .collect(Collectors.toList());
        }
        return roles;
    }

    private Role mapRole(RoleRepresentation roleRep) {
        Role role = new Role();
        role.setName(roleRep.getName());
        return role;
    }

    private Byte PKV_GetKeyByte(Long seed,int a,int b,int c){
        a = a % 25;
        b = b % 3;

        Byte result = 0;
        if (a % 2 == 0){
           return result = (byte) (((seed >> a) & 0xFF0000FF) ^ (seed >> b ) | c);
        }

        return result;
    }

}
