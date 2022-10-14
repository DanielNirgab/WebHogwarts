package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.serviceInterfacies.InfoInterface;

@Service
public class InfoService implements InfoInterface {
    @Value("${server.port}")
    private int serverPort;
    @Override
    public int getServerPort() {
        return serverPort;
    }
}
