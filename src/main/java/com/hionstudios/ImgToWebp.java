package com.hionstudios;

import io.github.biezhi.webp.WebpIO;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImgToWebp {
    byte[] image;
    String format;

    public ImgToWebp(byte[] image, String format) {
        this.format = format;
        this.image = image;
    }

    public byte[] convert() throws IOException {
        File src = Files.createTempFile(null, "." + format).toFile();
        FileUtils.writeByteArrayToFile(src, image);

        File dest = Files.createTempFile(null, ".webp").toFile();
        new WebpIO().toWEBP(src, dest);
        byte[] webp = Files.readAllBytes(dest.toPath());
        src.delete();
        dest.delete();
        return webp;
    }
}
