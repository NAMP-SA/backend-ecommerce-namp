package com.namp.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICloudinaryService {
    /**
     * Sube una imagen a Cloudinary con un publicId personalizado.
     *
     * @param file MultipartFile que contiene la imagen.
     * @param publicId identificador único para la imagen (sin extensión ni carpeta).
     * @return URL segura de la imagen subida.
     * @throws IOException si ocurre un error al leer el archivo o subirlo.
     */
    String uploadImage(MultipartFile file, String publicId) throws IOException;

    /**
     * Elimina una imagen de Cloudinary utilizando su URL.
     *
     * @param imageUrl URL completa de la imagen a eliminar.
     */
    void deleteImageByUrl(String imageUrl);
}
