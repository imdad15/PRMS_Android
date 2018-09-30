package sg.edu.nus.iss.phoenix.user.entity;

import java.io.Serializable;

public class Role implements Serializable {

        private String role;
        private String accessPrivilege;

        /**
         * Constructors. The first one takes no arguments and provides the most
         * simple way to create object instance. The another one takes one argument,
         * which is the primary key of the corresponding table.
         */
	public Role() {

        }

	public Role(String roleIn) {
            this.role = roleIn;
        }

        /**
         * Get- and Set-methods for persistent variables. The default behaviour does
         * not make any checks against malformed data, so these might require some
         * manual additions.
         * @return
         */
        public String getRole() {
            return this.role;
        }

        public void setRole(String roleIn) {
            this.role = roleIn;
        }

        public String getAccessPrivilege() {
            return this.accessPrivilege;
        }

        public void setAccessPrivilege(String accessPrivilegeIn) {
            this.accessPrivilege = accessPrivilegeIn;
        }

        /**
         * setAll allows to set all persistent variables in one method call. This is
         * useful, when all data is available and it is needed to set the initial
         * state of this object. Note that this method will directly modify instance
         * variales, without going trough the individual set-methods.
         * @param roleIn
         * @param accessPrivilegeIn
         */

        public void setAll(String roleIn, String accessPrivilegeIn) {
            this.role = roleIn;
            this.accessPrivilege = accessPrivilegeIn;
        }

        /**
         * hasEqualMapping-method will compare two Role instances and return true if
         * they contain same values in all persistent instance variables. If
         * hasEqualMapping returns true, it does not mean the objects are the same
         * instance. However it does mean that in that moment, they are mapped to
         * the same row in database.
         * @param valueObject
         * @return
         */
        public boolean hasEqualMapping(Role valueObject) {

            if (this.role == null) {
                if (valueObject.getRole() != null)
                    return (false);
            } else if (!this.role.equals(valueObject.getRole())) {
                return (false);
            }
            if (this.accessPrivilege == null) {
                if (valueObject.getAccessPrivilege() != null)
                    return (false);
            } else if (!this.accessPrivilege.equals(valueObject
                    .getAccessPrivilege())) {
                return (false);
            }

            return true;
        }

        /**
         * toString will return String object representing the state of this
         * valueObject. This is useful during application development, and possibly
         * when application is writing object states in textlog.
         */
        public String toString() {
            StringBuffer out = new StringBuffer("toString: ");
            out.append("\nclass Role, mapping to table role\n");
            out.append("Persistent attributes: \n");
            out.append("role = ").append(this.role).append("\n");
            out.append("accessPrivilege = ").append(this.accessPrivilege).append("\n");
            return out.toString();
        }

    }
