package Projects.Distributed.URL.Controller;

import Projects.Distributed.URL.DTO.URL_DTO;
import Projects.Distributed.URL.Service.URL_Service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
public class URL_Controller {
    private final URL_Service urlService;

    @PostMapping("/shortenURL")
    public ResponseEntity<URL_DTO> shortenURL(@RequestBody URL_DTO URLDto) {
        String shortenedUrl = urlService.shortenUrl(URLDto.getOriginalUrl());
        URL_DTO responseDto = new URL_DTO(URLDto.getOriginalUrl(), shortenedUrl);
        return ResponseEntity.ok(responseDto);
    }

}
