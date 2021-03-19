package dobble;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageLoader {
            
    public static byte[] getImage(String path) {
        File file = new File("Cartas\\" + path + ".png");
        try {
            return readFile(new BufferedInputStream(new FileInputStream(file)));
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    private static byte[] readFile(BufferedInputStream is) {
                byte[] buffer = new byte[4096];
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                try {
                    while(true) {
                        int length = is.read(buffer);
                        if(length < 0) {
                            break;
                        }
                        os.write(buffer, 0, length);
                    }
                    byte[] result  = os.toByteArray();
                    os.close();
                    return result;
                }
                catch(IOException e) {
                    return null;
                }
            }
}
