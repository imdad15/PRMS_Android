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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
