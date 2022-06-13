package org.vir1ibus.onlinestore.entity;

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
@Table(name = "description")
public class Description extends CustomJSONObject {

    public Description() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "text")
    private String text;

    @OneToOne
    @JoinColumn(name = "item_id", nullable = false, referencedColumnName = "id")
    Item item;

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("title", this.title)
                .put("text", this.text);
    }
}
