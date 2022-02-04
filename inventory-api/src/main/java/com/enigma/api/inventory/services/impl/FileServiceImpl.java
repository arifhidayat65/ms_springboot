package com.enigma.api.inventory.services.impl;

import com.enigma.api.inventory.entities.AbstractEntity;
import com.enigma.api.inventory.services.FileService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    @Value("${application.data-dir}")
    private String dataDir;
    
    @Override
    public void upload(AbstractEntity entity, InputStream in) throws IOException {
        File dir = new File(dataDir, entity.getClass().getSimpleName().toLowerCase());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        File file = new File(dir, entity.getId().toString());
        try (OutputStream out = new FileOutputStream(file)) {
            int length;
            byte[] data = new byte[8192];
            while ((length = in.read(data)) > -1) {
                out.write(data, 0, length);
            }
        }
    }

    @Override
    public void download(AbstractEntity entity, OutputStream out) throws IOException {
        File dir = new File(dataDir, entity.getClass().getSimpleName().toLowerCase());
        File file = new File(dir, entity.getId().toString());
        
        try (InputStream in = new FileInputStream(file)) {
            int length;
            byte[] data = new byte[8192];
            while ((length = in.read(data)) > -1) {
                out.write(data, 0, length);
            }
        }
    }
}
