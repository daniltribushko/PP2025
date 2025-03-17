package ru.tdd.backend.domen.service.files;

import org.springframework.core.io.Resource;

public interface FileService {
    Resource downloadFile(String fileName);
    Boolean fileExists(String fileName);
}
