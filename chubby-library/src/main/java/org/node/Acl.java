package org.node;






//TODO (edit): un file di tipo Acl è un FileNode contenuto nel b-tree di jetcd che ha come fileContent una lista di
// client che possiedono i permessi per effettuare read/write/ecc... nei nodi (vedi appunti tablet)











//TODO (leggi i commenti)
//? PER 'apache shiro' vd. sotto:
//? CONVIENE PROBABILMENTE USARE "apache shiro" è una libreria fatta apposta per l'autenticazione

//! risposta di chat got a 'fammi l'acl come è scritto nel paper di chubby':
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;

public class Acl {
    private static final DefaultSecurityManager securityManager;
    private final IniRealm realm;

    public Acl() {
        this.securityManager = new DefaultSecurityManager;
        this.realm = new IniRealm("classpath:shiro.ini");
        this.securityManager.setRealm(this.realm);
        SecurityUtils.setSecurityManager(this.securityManager);
    }

    public boolean authenticate(String username, String password) {
        // Create a new user token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();

        try {
            // Try to authenticate the user
            currentUser.login(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkPermission(String username, String password, String permission) {
        if (authenticate(username, password)) {
            // Check if the authenticated user has the required permission
            return SecurityUtils.getSubject().isPermitted(permission);
        }
        return false;
    }
}

    //! risposta di chat got a 'fammi l'acl come è scritto nel paper di chubby'
    /*import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;

public class ChubbyACL {
    private DefaultSecurityManager securityManager;
    private IniRealm realm;

    public ChubbyACL() {
        // Initialize the security manager and realm
        this.securityManager = new DefaultSecurityManager();
        this.realm = new IniRealm("classpath:shiro.ini");
        this.securityManager.setRealm(this.realm);
        SecurityUtils.setSecurityManager(this.securityManager);
    }

    public boolean authenticate(String username, String password) {
        // Create a new user token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();

        try {
            // Try to authenticate the user
            currentUser.login(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkPermission(String username, String password, String permission) {
        if (authenticate(username, password)) {
            // Check if the authenticated user has the required permission
            return SecurityUtils.getSubject().isPermitted(permission);
        }
        return false;
    }
}

//? In this example, the shiro.ini file would contain the ACLs for each node. Each section in the INI file represents a user, and the user directive lists the user’s permissions. For instance:
//
//[users]
//foo = password, node1:write, node2:read
//bar = password, node1:read, node2:write
//
//This is a very basic implementation and might need to be adjusted based on your specific requirements. Please note that this code does not handle the inheritance of ACLs from parent directories, as described in the Chubby paper. This would require a more complex setup and possibly a custom Realm implementation. Also, remember to handle exceptions and edge cases in a production environment.

*/