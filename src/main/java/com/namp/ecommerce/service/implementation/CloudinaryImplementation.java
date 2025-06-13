package com.namp.ecommerce.service.implementation;
import com.namp.ecommerce.service.ICloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.net.URI;
import java.util.Map;
import java.io.IOException;

@Service
public class CloudinaryImplementation implements ICloudinaryService {
    private final Cloudinary cloudinary;

    /**
     * Constructor que inicializa el cliente de Cloudinary con variables de entorno.
     */
    public CloudinaryImplementation() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", System.getenv("CLOUDINARY_CLOUD_NAME"),
                "api_key", System.getenv("CLOUDINARY_API_KEY"),
                "api_secret", System.getenv("CLOUDINARY_API_SECRET"),
                "secure", true
        ));
    }

    /**
     * Sube una imagen a Cloudinary bajo la carpeta "productos", con un formato forzado a .webp.
     *
     * @param file MultipartFile recibido desde el frontend.
     * @param publicId identificador personalizado para la imagen (sin extensión).
     * @return URL completa y segura de la imagen subida.
     */
    @Override
    public String uploadImage(MultipartFile file, String publicId) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "public_id", publicId,
                "folder", "productos", // Carpeta personalizada en Cloudinary
                "resource_type", "image",
                "format", "webp" // Fuerza conversión a formato webp
        ));
        return uploadResult.get("secure_url").toString(); // Devuelve la URL pública segura
    }

    /**
     * Elimina una imagen en Cloudinary a partir de su URL.
     *
     * @param imageUrl URL completa de la imagen en Cloudinary.
     */
    @Override
    public void deleteImageByUrl(String imageUrl) {
        try {
            String publicId = extractPublicIdFromUrl(imageUrl);

            Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            // Podés loguear el resultado si querés debuggear:
            // System.out.println("Resultado de eliminación: " + result);

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar imagen de Cloudinary", e);
        }
    }

    /**
     * Extrae el public_id de una imagen a partir de su URL.
     * Elimina la parte de versionado y la extensión (.webp, .jpg, etc.)
     *
     * @param imageUrl URL completa de Cloudinary.
     * @return public_id listo para eliminar (ej: "productos/Producto_123")
     */
    private String extractPublicIdFromUrl(String imageUrl) {
        try {
            URI uri = new URI(imageUrl);
            String path = uri.getPath(); // ej: /.../upload/vXXXXXX/productos/NombreImagen.webp

            // Cortamos desde después de "/upload/"
            String afterUpload = path.substring(path.indexOf("/upload/") + "/upload/".length());

            // Quitamos el versionado
            String[] parts = afterUpload.split("/", 2); // [vXXXXX, productos/NombreImagen.webp]
            if (parts.length < 2) return null;

            String publicIdWithExtension = parts[1];

            // Quitamos extensión final
            return publicIdWithExtension.replaceFirst("\\.[^.]+$", "");
        } catch (Exception e) {
            throw new RuntimeException("No se pudo extraer el public_id desde la URL", e);
        }
    }
}