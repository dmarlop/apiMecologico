package com.meco;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImagenController {

    // Ahora las imágenes estarán en una carpeta externa llamada "images" en la raíz del proyecto
    private final Path imageDirectory = Paths.get("images");

    
    @PostConstruct
    public void init() {
        System.out.println("✅ ImagenController cargado correctamente");
    }
    
    
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path file = imageDirectory.resolve(filename).normalize();
            System.out.println("🔍 Buscando imagen en: " + file.toAbsolutePath());

            if (!Files.exists(file)) {
                System.out.println("❌ Imagen no encontrada.");
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(file.toUri());

            // Detecta el tipo de contenido (MIME)
            String contentType = Files.probeContentType(file);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (MalformedURLException e) {
            System.out.println("❗ Error de URL: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.out.println("❗ Error general: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}

