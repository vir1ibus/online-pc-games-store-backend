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
@Table(name = "item_has_system_requirement")
public class ItemHasSystemRequirement extends CustomJSONObject {

    public ItemHasSystemRequirement() {}

    @EmbeddedId
    ItemHasSystemRequirementId itemHasSystemRequirementId;

    @MapsId("itemId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    @MapsId("systemRequirementId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "system_requirement_id", nullable = false)
    private SystemRequirement systemRequirement;

    @Column(name = "value", nullable = false)
    private String value;

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("title", this.systemRequirement.getTitle())
                .put("value", this.value);
    }
}