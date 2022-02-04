package com.enigma.api.inventory.services;

import com.enigma.api.inventory.entities.AbstractEntity;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileService {
    
    public void upload(AbstractEntity entity, InputStream in) throws IOException;
    
    public void download(AbstractEntity entity, OutputStream out) throws IOException;
}
