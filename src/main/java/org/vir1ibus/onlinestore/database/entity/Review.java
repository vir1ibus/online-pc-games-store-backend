package org.vir1ibus.onlinestore.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.json.JSONObject;
import org.vir1ibus.onlinestore.utils.CustomJSONObject;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Date;

@Builder
@AllArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "review")
public class Review extends CustomJSONObject {

    public Review() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @Max(5)
    @Column(name = "stars", nullable = false)
    private Integer stars;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Builder.Default
    @Column(name = "date_review", nullable = false)
    private Date dateReview = new Date();

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("id", this.id)
                .put("title", this.title)
                .put("text", this.text)
                .put("stars", this.stars)
                .put("author", this.author.toMinimalJSONObject())
                .put("date", this.dateReview);
    }

}
