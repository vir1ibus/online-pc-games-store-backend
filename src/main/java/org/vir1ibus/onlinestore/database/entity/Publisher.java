package org.vir1ibus.onlinestore.database.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
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
@Component
@Table(name = "publisher")
public class Publisher extends CustomJSONObject {

    public Publisher() {}

    @Id
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "img")
    private String img;

    @OneToMany(mappedBy = "publisher")
    @Builder.Default
    private Set<Item> items = new LinkedHashSet<>();

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("title", this.title)
                .put("img", this.img)
                .put("items", JSONConverter.toMinimalJsonArray(this.items))
                .put("count_items", this.items.size());
    }

    @Override
    public JSONObject toMinimalJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("title", this.title)
                .put("img", this.img)
                .put("count_items", this.items.size());
    }
}