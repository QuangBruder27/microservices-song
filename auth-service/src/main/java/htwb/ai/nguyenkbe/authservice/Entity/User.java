package htwb.ai.nguyenkbe.authservice.Entity;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@Entity
@Table(name="user")
@XmlRootElement(name = "user")
public class User {

    @Id
    private String userId;
    private String password;
    private String firstName;
    private String lastName;


    public User() { }

    public User(String uuserId, String upassword, String ufirstName, String ulastName){
        userId = uuserId;
        password = upassword;
        lastName = ulastName;
        firstName = ufirstName;
    }

    public User(Builder builder) {
        this.userId = builder.userId;
        this.password = builder.password;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId) &&
                password.equals(user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, firstName, lastName);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }


    /**
     * Creates builder to build {@link User}.
     * @return created builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder to build {@link User}.
     */
    public static final class Builder {
        private String userId;
        private String firstName;
        private String lastName;
        private String password;

        private Builder() {
        }


        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withFirstname(String firstname) {
            this.firstName = firstname;
            return this;
        }

        public Builder withLastname(String lastname) {
            this.lastName = lastname;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}