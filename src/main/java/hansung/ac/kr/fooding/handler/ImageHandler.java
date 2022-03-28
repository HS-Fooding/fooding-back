package hansung.ac.kr.fooding.handler;

import hansung.ac.kr.fooding.var.Variable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageHandler {
    public static List<String> upload(List<MultipartFile> images){
        List<String> imagePath = new ArrayList<>();
        if(images == null)
            return null;
        for(MultipartFile image : images) {
            String path = getRootFolder();
            String fileName = getNowTime24()  + "_" +image.getOriginalFilename();

            try {
                image.transferTo(new File(path+fileName));
                imagePath.add("http://13.124.207.219:8080/sample_project/image/"+fileName);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return imagePath;
    }

    private static String getRootFolder() throws IllegalStateException{
        String os = System.getProperty("os.name").toLowerCase();
        String rootFolder = "";
        if (os.contains("win")) {
            System.out.println("\n");
            System.out.println("=======================================");
            System.out.println("[ModuleApiController] : [resourceInsertImage] : [os check]");
            System.out.println("[os] : " + "Windows");
            System.out.println("=======================================");
            System.out.println("\n");
            rootFolder = Variable.LOCAL_FILE_PATH; //윈도우 경로 (디스크 필요)

        } else if (os.contains("linux")) {
            System.out.println("\n");
            System.out.println("=======================================");
            System.out.println("[ModuleApiController] : [resourceInsertImage] : [os check]");
            System.out.println("[os] : " + "Linux");
            System.out.println("=======================================");
            System.out.println("\n");
            rootFolder = Variable.SERVER_FILE_PATH;
        } else {
            System.out.println("\n");
            System.out.println("=======================================");
            System.out.println("[ModuleApiController] : [resourceInsertImage] : [os check]");
            System.out.println("[os] : " + "None");
            System.out.println("=======================================");
            System.out.println("\n");
            throw new IllegalStateException("Can't decide Image Root Path");
        }
        return rootFolder;
    }

    public static String getNowTime24() {
        long time = System.currentTimeMillis();
        //SimpleDateFormat dayTime = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddkkmmss");
        String str = dayTime.format(new Date(time));
        return "PT"+str; //TODO [PT는 picture 의미]
    }
}
