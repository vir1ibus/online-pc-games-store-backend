package org.vir1ibus.onlinestore.database.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.json.JSONObject;
import org.vir1ibus.onlinestore.utils.CustomJSONObject;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@Setter
@Getter
@NonNull
@Accessors(chain = true)
@Entity
@Table(name = "region_activation")
public class RegionActivation extends CustomJSONObject {

    public RegionActivation() {}

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
