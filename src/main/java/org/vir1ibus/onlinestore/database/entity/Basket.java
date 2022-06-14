package org.vir1ibus.onlinestore.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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
@Table(name = "basket")
public class Basket extends CustomJSONObject {

    public Basket() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(name = "basket_has_items",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
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
                .put("items", JSONConverter.toMinimalJsonArray(this.items));
    }
}
