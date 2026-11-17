package org.dides.studentmanagement.enums;

import java.util.Set;
import static org.dides.studentmanagement.enums.Permission.*;

public enum Roles {

    ADMIN(Set.of(
            USER_READ, USER_CREATE, USER_UPDATE, USER_DELETE,
            ADMISSION_READ, ADMISSION_CREATE, ADMISSION_UPDATE,
            ADMISSION_DELETE, ADMISSION_APPROVE,
            STUDENT_READ, STUDENT_CREATE, STUDENT_UPDATE, STUDENT_DELETE,
            COURSE_READ, COURSE_CREATE, COURSE_UPDATE, COURSE_DELETE,
            REPORT_VIEW, REPORT_EXPORT,
            SYSTEM_SETTINGS, ROLE_MANAGE, AUDIT_VIEW
    )),

    DIRECTOR(Set.of(
            USER_READ,
            ADMISSION_READ, ADMISSION_APPROVE,
            STUDENT_READ,
            COURSE_READ,
            REPORT_VIEW, REPORT_EXPORT,
            AUDIT_VIEW
    )),

    ADMISSIONUSER(Set.of(
            ADMISSION_READ, ADMISSION_CREATE, ADMISSION_UPDATE,
            STUDENT_READ,
            COURSE_READ
    )),

    STAFF(Set.of(
            STUDENT_READ, STUDENT_UPDATE,
            COURSE_READ,
            REPORT_VIEW
    )),

    STUDENT(Set.of(
            COURSE_READ,
            ADMISSION_READ
    ));

    private final Set<Permission> permissions;

    Roles(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
