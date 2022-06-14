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
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "purchase")
public class Purchase extends CustomJSONObject {

    public Purchase() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    private User buyer;

    @Builder.Default
    @Column(name = "date_purchase", nullable = false)
    private Date datePurchase = new Date();

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "purchases_has_items",
            joinColumns = @JoinColumn(name = "purchases_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "purchase")
    private Set<ActivateKey> activateKeys = new LinkedHashSet<>();

    @Column(name = "sum", nullable = false)
    @PositiveOrZero
    private Integer sum;

    @Column(name = "paid", nullable = false)
    @Builder.Default
    private Boolean paid = false;

    @Column(name = "billId")
    private String billId;

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("user", this.buyer.toMinimalJSONObject())
                .put("date_purchase", this.datePurchase)
                .put("items", JSONConverter.toMinimalJsonArray(this.items))
                .put("sum", this.sum);
    }
}
