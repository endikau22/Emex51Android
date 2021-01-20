package model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Set;

@Root(name = "users")
public class Users {
    @ElementList(name = "user", inline = true)
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }
    public void setUsers (Set<User> users) {
        this.users = users;
    }
}
