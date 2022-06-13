package org.vir1ibus.onlinestore.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.vir1ibus.onlinestore.utils.CustomJSONObject;
import org.vir1ibus.onlinestore.utils.JSONConverter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
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
@Table(name = "item")
public class Item extends CustomJSONObject {

    public Item() {}

    @Id
    @Column(name = "id", nullable = false)
    @NotBlank
    private String id;

    @NotBlank
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank
    @Column(name = "img", nullable = false)
    private String img;

    @PositiveOrZero
    @Column(name = "price", nullable = false)
    private Integer price;

    @PositiveOrZero
    @Column(name = "discount", nullable = false)
    private Integer discount;

    @NotBlank
    @Column(name = "language_support", nullable = false)
    private String languageSupport;

    @PastOrPresent
    @Column(name = "date_realise", nullable = false)
    private LocalDate dateRealise;

    @OneToOne(mappedBy = "item")
    private Description description;

    @NotBlank
    @Column(name = "platform", nullable = false)
    private String platform;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_activation_id", nullable = false, referencedColumnName = "id")
    private RegionActivation regionActivation;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publisher_id", nullable = false, referencedColumnName = "id")
    private Publisher publisher;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "developer_id", nullable = false, referencedColumnName = "id")
    private Developer developer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_type_id", nullable = false, referencedColumnName = "id")
    private ItemType itemType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_activation_id", nullable = false, referencedColumnName = "id")
    private ServiceActivation serviceActivation;

    @NotNull
    @OneToMany(mappedBy = "item")
    private Set<Screenshot> screenshots = new LinkedHashSet<>();

    @NotNull
    @OneToMany(mappedBy = "item")
    private Set<Trailer> trailers = new LinkedHashSet<>();
    @NotNull
    @OneToMany(mappedBy = "item")
    private Set<Review> reviews = new LinkedHashSet<>();
    @NotNull
    @OneToMany(mappedBy = "item")
    private Set<ItemHasSystemRequirement> itemHasSystemRequirement;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "genre_has_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new LinkedHashSet<>();

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany
    @JoinTable(name = "purchases_has_items",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "purchases_id"))
    @Builder.Default
    private Set<Purchase> purchases = new LinkedHashSet<>();

    @OneToMany(mappedBy = "item")
    private Set<ActivateKey> activateKeys = new LinkedHashSet<>();

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("title", this.title)
                .put("img", this.img)
                .put("price", this.price)
                .put("discount", this.discount)
                .put("language_support", this.languageSupport)
                .put("date_realise", this.dateRealise)
                .put("platform", this.platform)
                .put("region_activation", this.regionActivation.toJSONObject())
                .put("publisher", this.publisher.toMinimalJSONObject())
                .put("developer", this.developer.toMinimalJSONObject())
                .put("description", this.description.toJSONObject())
                .put("item_type_title", this.itemType.toJSONObject())
                .put("service_activation", this.serviceActivation.toJSONObject())
                .put("screenshots", JSONConverter.toJsonArray(this.screenshots))
                .put("trailers", JSONConverter.toJsonArray(this.trailers))
                .put("reviews", JSONConverter.toJsonArray(this.reviews))
                .put("system_requirements", JSONConverter.toJsonArray(this.itemHasSystemRequirement))
                .put("genres", JSONConverter.toJsonArray(this.genres))
                .put("count", this.activateKeys.stream().filter(key -> key.getPurchase() == null).count());
    }
    @Override
    public JSONObject toMinimalJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("title", this.title)
                .put("price", this.price)
                .put("discount", this.discount)
                .put("img", this.img)
                .put("service_activation", this.serviceActivation.toJSONObject())
                .put("region_activation", this.regionActivation.toJSONObject())
                .put("count", this.activateKeys.stream().filter(key -> key.getPurchase() == null).count());
    }
}