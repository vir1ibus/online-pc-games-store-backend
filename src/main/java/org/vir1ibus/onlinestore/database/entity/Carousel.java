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
@Table(name = "carousel")
public class Carousel extends CustomJSONObject {

    public Carousel() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name="item_id")
    @OneToOne
    private Item item;

    @JoinColumn(name = "screenshot_id")
    @OneToOne
    private Screenshot screenshot;

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("item", item.toMinimalJSONObject())
                .put("screenshot", screenshot.toJSONObject());
    }
}
