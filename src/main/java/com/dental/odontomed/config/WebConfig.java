package com.dental.odontomed.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Servir archivos subidos desde la carpeta uploads
        String uploadDir = new File(System.getProperty("user.dir"), "uploads").getAbsolutePath();
        
        // Convertir en URL válida (con slashes y sin backslashes)
        String uploadUrl = "file:///" + uploadDir.replace("\\", "/");
        
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadUrl + "/");
    }
}


