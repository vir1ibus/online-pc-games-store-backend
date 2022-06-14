package org.vir1ibus.onlinestore.database.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.json.JSONObject;
import org.vir1ibus.onlinestore.utils.CustomJSONObject;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "authorization_token")
public class AuthorizationToken extends CustomJSONObject {

    public AuthorizationToken() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "ip", length = 126, nullable = false)
    private String ip;

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject("token", this.value);
    }
}
