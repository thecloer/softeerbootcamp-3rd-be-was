package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {

    public static byte[] read(String path) throws IOException {
        File file = new File(path);
        if (file.length() > Integer.MAX_VALUE)
            throw new IOException("파일의 크기가 너무 큽니다.: " + file.getName());

        try (FileInputStream fis = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] data = new byte[(int) file.length()];
            int totalBytesRead = 0;
            int bytesRead;

            while (totalBytesRead < data.length && (bytesRead = bis.read(data, totalBytesRead, data.length - totalBytesRead)) != -1)
                totalBytesRead += bytesRead;

            if (totalBytesRead != data.length)
                throw new IOException("파일 전체를 읽는데 실패했습니다.: " + file.getName());

            return data;
        }
    }
}
