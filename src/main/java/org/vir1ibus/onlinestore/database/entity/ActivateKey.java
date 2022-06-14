package org.vir1ibus.onlinestore.database.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.vir1ibus.onlinestore.utils.CustomJSONObject;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@Setter
@Getter
@NonNull
@Accessors(chain = true)
@Entity
@Component
@Table(name = "activate_key")
public class ActivateKey extends CustomJSONObject {
    public ActivateKey() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("key", this.value)
                .put("item", this.item.toMinimalJSONObject())
                .put("purchase", this.purchase);
    }

}
