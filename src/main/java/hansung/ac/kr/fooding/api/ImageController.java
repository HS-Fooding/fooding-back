package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.var.Variable;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class ImageController {

    @RequestMapping("/image/{imageName:.+}")
    @ResponseBody
    public ResponseEntity getImage(@PathVariable("imageName") String imageName){
        String path = Variable.SERVER_FILE_PATH;
        //String path = "d:/dev/images/";

        byte[] byteArr = null;
        try {
            InputStream imageStream = new FileInputStream(path + imageName);
            byteArr = IOUtils.toByteArray(imageStream);
            imageStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Fooding-No Image",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<byte[]>(byteArr, HttpStatus.OK);
    }
}