package org.vir1ibus.onlinestore.database.entity;

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
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "liked")
public class Liked extends CustomJSONObject {

    public Liked() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany
    @JoinTable(name = "liked_has_items",
            joinColumns = @JoinColumn(name = "liked_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    @Builder.Default
    private Set<Item> items = new LinkedHashSet<>();

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("user", this.user.toMinimalJSONObject())
                .put("items", JSONConverter.toJsonArray(this.items));
    }

    @Override
    public JSONObject toMinimalJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("items", JSONConverter.toJsonArray(this.items));
    }
}