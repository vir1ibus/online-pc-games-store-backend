package org.vir1ibus.onlinestore.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/image")
public class ImageController {

    /**
     * Проверяет наличие папки с названием "media" в директории приложения.
     * В случае успеха возвращает полный путь до неё в системе.
     * Иначе создаёт её и также возвращает полный путь.
     */

    public static String getOrCreateMediaFolder() {
        try {
            Path media = Path.of("media");
            return new File(media.toAbsolutePath().toString()).exists() ? media.toAbsolutePath().toString() : Files.createDirectory(media).toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Проверяет наличие папки с переданным в директории приложения.
     * В случае успеха возвращает полный путь до неё в системе.
     * Иначе создаёт её и также возвращает полный путь.
     */

    public static String getOrCreateFolder(String parentPath, String name) {
        try {
            Path path = Path.of(parentPath + "/" + name);
            return new File(path.toAbsolutePath().toString()).exists() ? path.toAbsolutePath().toString() : Files.createDirectory(path).toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод добавляет в папку "media" изображения, генерируя для них новые названия
     * и возвращает их в качестве результата.
     */

    public static String addImage(String folder, String imageName, MultipartFile image) {
        try {
            String fileExtension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf('.'));
            File img = new File(getOrCreateFolder(getOrCreateMediaFolder(), folder), imageName + fileExtension);
            img.createNewFile();
            FileOutputStream imageOutputStream = new FileOutputStream(img);
            byte[] imageBuffer = image.getBytes();
            imageOutputStream.write(imageBuffer, 0, imageBuffer.length);
            return img.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Метод вызывается при REST запросе с URI = /image и GET параметром,
     * содержащий путь до требуемого изображения.
     */

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void getImage(@RequestParam String path, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(new FileInputStream(getOrCreateMediaFolder() + path), response.getOutputStream());
    }
}
