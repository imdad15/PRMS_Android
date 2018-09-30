package sg.edu.nus.iss.phoenix.user.entity;

/**
 * Created by rahul on 23-09-2018.
 */
import java.util.ArrayList;

public class User {
    private String id;
    private String name;
    private String password;
    private String address;
    private String dateOfJoining;
    private String siteLink;
    private ArrayList<Role> roles;
    private boolean isEdit;

    public User()
    {

    }

    public User(String idIn) {
        this.id = idIn;
    }

    public User(String name, ArrayList<Role> roles){
        this.name = name;
        if(this.roles == null)
            this.roles = new ArrayList<>();
        this.roles.addAll(roles);
    }

    public User(String name, String password,String id, ArrayList<Role> role,String address, String doj,String siteLink) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roles = role;
        this.address = address;
        this.dateOfJoining = doj;
        this.siteLink = siteLink;

    }


    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    /**
     * Get- and Set-methods for persistent variables. The default behavior does
     * not make any checks against malformed data, so these might require some
     * manual additions.
     */

    public String getId() {
        return this.id;
    }

    public void setId(String idIn) {
        this.id = idIn;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String passwordIn) {
        this.password = passwordIn;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String nameIn) {
        this.name = nameIn;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getSiteLink() {
        return siteLink;
    }

    public void setSiteLink(String siteLink) {
        this.siteLink = siteLink;
    }

    /**
     * setAll allows to set all persistent variables in one method call. This is
     * useful, when all data is available and it is needed to set the initial
     * state of this object. Note that this method will directly modify instance
     * variables, without going trough the individual set-methods.
     */

    public void setAll(String idIn, String passwordIn, String nameIn,
                       String roleIn) {
        this.id = idIn;
        this.password = passwordIn;
        this.name = nameIn;
        Role e = new Role(roleIn);
        this.roles.add(e);
    }

    /**
     * hasEqualMapping-method will compare two User instances and return true if
     * they contain same values in all persistent instance variables. If
     * hasEqualMapping returns true, it does not mean the objects are the same
     * instance. However it does mean that in that moment, they are mapped to
     * the same row in database.
     */
    public boolean hasEqualMapping(User valueObject) {

        if (valueObject.getId() != this.id) {
            return (false);
        }
        if (this.password == null) {
            if (valueObject.getPassword() != null)
                return (false);
        } else if (!this.password.equals(valueObject.getPassword())) {
            return (false);
        }
        if (this.name == null) {
            if (valueObject.getName() != null)
                return (false);
        } else if (!this.name.equals(valueObject.getName())) {
            return (false);
        }
        if (this.roles.get(0).getRole() != null) {
            if (valueObject.roles.get(0).getRole() != null)
                return (false);
        } else if (!this.roles.get(0).equals(valueObject.roles.get(0).getRole())) {
            return (false);
        }

        return true;
    }

    /**
     * toString will return String object representing the state of this
     * valueObject. This is useful during application development, and possibly
     * when application is writing object states in console logs.
     */
    public String toString() {
        StringBuffer out = new StringBuffer("toString: ");
        out.append("\nclass User, mapping to table user\n");
        out.append("Persistent attributes: \n");
        out.append("id = " + this.id + "\n");
        out.append("password = " + this.password + "\n");
        out.append("name = " + this.name + "\n");
        out.append("role = " + this.roles.get(0).getRole() + "\n");
        return out.toString();
    }
}
