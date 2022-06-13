package org.vir1ibus.onlinestore.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.json.JSONObject;
import org.vir1ibus.onlinestore.utils.CustomJSONObject;

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
@Table(name = "item_type")
public class ItemType extends CustomJSONObject {

    public ItemType() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject(this);
    }

}
