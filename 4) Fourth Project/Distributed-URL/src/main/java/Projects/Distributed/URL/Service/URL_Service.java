package Projects.Distributed.URL.Service;

import Projects.Distributed.URL.entity.URL;
import Projects.Distributed.URL.repository.URL_REPO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
@AllArgsConstructor
public class URL_Service {

     private final URL_REPO urlRepository;
     private final SnowFlackIdGenerator snowflakeIdGenerator;

     // Method to shorten a URL
     public String shortenUrl(String originalUrl) {
          long snowflakeId = snowflakeIdGenerator.nextId();  // Generate a Snowflake ID
          String shortenedUrl = Base62Encoder.encode(snowflakeId);  // Convert to Base62

          //expire after 30 days
          LocalDateTime expirationDate = LocalDateTime.now().plus(30, ChronoUnit.DAYS);

          URL url = new URL(shortenedUrl, originalUrl, LocalDateTime.now(),expirationDate);  // Create Url entity
          urlRepository.save(url);  // Save to database

          return shortenedUrl;  // Return shortened URL
     }

     //  retrieve original URL by shortened URL
     public String getOriginalUrl(String shortenedUrl) {
          return urlRepository.findById(shortenedUrl)
                  .map(URL::getOriginalURL)
                  .orElse(null);
     }
}
