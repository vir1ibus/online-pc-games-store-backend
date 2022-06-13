package org.vir1ibus.onlinestore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.json.JSONObject;
import org.vir1ibus.onlinestore.utils.CustomJSONObject;
import org.vir1ibus.onlinestore.utils.JSONConverter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends CustomJSONObject {
    public Role() {}

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "role_name"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new LinkedHashSet<>();

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("name", this.name)
                .put("users", JSONConverter.toMinimalJsonArray(this.users));
    }

    @Override
    public JSONObject toMinimalJSONObject() {
        return new JSONObject()
                .put("name", this.name);
    }
}
