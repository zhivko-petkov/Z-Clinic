package com.zhivkoproject.ZClinic.model.entity;

import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
@Data
@Setter
public class UserRole extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

}
