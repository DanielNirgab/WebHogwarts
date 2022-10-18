package ru.hogwarts.school.conrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.serviceInterfacies.InfoInterface;

@RestController
public class InfoController {

    private final InfoInterface infoInterface;

    public InfoController(InfoInterface infoInterface) {
        this.infoInterface = infoInterface;
    }

    @GetMapping("/getPort")
    public ResponseEntity<Integer> getPort() {
        return ResponseEntity.ok(infoInterface.getServerPort());
    }
}
