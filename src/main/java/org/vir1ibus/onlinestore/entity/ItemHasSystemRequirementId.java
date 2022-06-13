package org.vir1ibus.onlinestore.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Builder
@AllArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class ItemHasSystemRequirementId implements Serializable {

    public ItemHasSystemRequirementId() {}

    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "system_requirement_id", nullable = false)
    private Integer systemRequirementId;

}
