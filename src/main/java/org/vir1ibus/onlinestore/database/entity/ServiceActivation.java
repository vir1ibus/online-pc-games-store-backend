package org.vir1ibus.onlinestore.database.entity;

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
@Table(name = "service_activation")
public class ServiceActivation extends CustomJSONObject {

    public ServiceActivation() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "id")
    private Set<Item> items = new LinkedHashSet<>();

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("title", this.title);
    }
}