package org.vir1ibus.onlinestore.database.entity;

import lombok.*;
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
@Setter
@Getter
@NonNull
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class User extends CustomJSONObject {

    public User() {}

    @Id
    private String id;

    @Column(name="username", nullable = false, length = 75)
    private String username;

    @Column(name="email", nullable = false, length = 100)
    private String email;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @Column(name = "password", nullable = false, length = 512)
    private String password;

    @Column(name = "salt", nullable = false, length = 16)
    private String salt;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Basket basket;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "liked_id", referencedColumnName = "id")
    private Liked liked;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.REMOVE)
    private Set<Purchase> purchases = new LinkedHashSet<>();

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_name"))
    private Set<Role> roles = new LinkedHashSet<>();
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private Set<Review> reviews = new LinkedHashSet<>();

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("username", this.username)
                .put("email", this.email)
                .put("role", JSONConverter.toMinimalJsonArray(this.roles))
                .put("basket", this.basket.toMinimalJSONObject())
                .put("liked", this.liked.toMinimalJSONObject())
                .put("purchases", JSONConverter.toJsonArray(this.purchases))
                .put("reviews", JSONConverter.toJsonArray(this.reviews));
    }

    @Override
    public JSONObject toMinimalJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("username", this.username);
    }
}
