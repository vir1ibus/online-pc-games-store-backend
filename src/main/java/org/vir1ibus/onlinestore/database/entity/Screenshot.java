package org.vir1ibus.onlinestore.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.json.JSONObject;
import org.vir1ibus.onlinestore.utils.CustomJSONObject;

import javax.persistence.*;
@Builder
@AllArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "screenshot")
public class Screenshot extends CustomJSONObject {

    public Screenshot() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "path", nullable = false)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false, referencedColumnName = "id")
    private Item item;

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("path", this.path);
    }
}
