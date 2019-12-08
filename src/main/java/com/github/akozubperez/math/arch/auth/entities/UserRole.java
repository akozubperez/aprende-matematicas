package com.github.akozubperez.math.arch.auth.entities;

import com.github.akozubperez.math.arch.common.AuditableEntity;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends AuditableEntity implements Serializable {

    @EmbeddedId
    private UserRoleId id;
}
