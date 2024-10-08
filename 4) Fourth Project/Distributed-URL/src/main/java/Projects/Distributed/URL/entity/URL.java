package Projects.Distributed.URL.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "URLs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class URL {

    @Id
    //Store the base62 representation of the snowflake Id;
    private String shortendURL;

    @Column(name = "original_url", nullable = false)
    private String OriginalURL;

    // Getters and Setters
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;



}
