package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Coordinate;
import hansung.ac.kr.fooding.domain.Location;
import hansung.ac.kr.fooding.var.Variable;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

@Service
public class GeocodeService {
    private static String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/address.json?query=";
//    private static String GEOCODE_USER_INFO = Variable.KAKAO_KEY;
    private static String GEOCODE_USER_INFO = Variable.KAKAO_KEY_JHS;

    public Location getGeocode(String address){
        Location result = null;
        float x = 0, y=0;
        URL obj;
        try {
            address = URLEncoder.encode(address, "UTF-8");
            obj = new URL(GEOCODE_URL + address);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", GEOCODE_USER_INFO);
            con.setRequestProperty("content-type", "application/json");
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);
            Charset charset = Charset.forName("UTF-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JSONObject jObject = new JSONObject(response.toString());
            JSONObject jMetaObject = (JSONObject)jObject.get("meta");
            JSONObject addrObject = jObject.getJSONArray("documents").getJSONObject(0);
            int size = (int)jMetaObject.get("total_count");

            if(size == 1){
                String addrType = ((JSONObject) jObject.getJSONArray("documents").get(0)).getString("address_type");
                if(addrType.equals("REGION") || addrType.equals("ROAD"))
                    return result;
                JSONObject roadAddress = addrObject.getJSONObject("road_address");
                x = roadAddress.getFloat("x");
                y = roadAddress.getFloat("y");
                result = Location.builder()
                        .addressName(roadAddress.getString("address_name"))
                        .region1Depth(roadAddress.getString("region_1depth_name"))
                        .region2Depth(roadAddress.getString("region_2depth_name"))
                        .region3Depth(roadAddress.getString("region_3depth_name"))
                        .roadName(roadAddress.getString("road_name"))
                        .buildingNo(roadAddress.getString("main_building_no"))
                        .coordinate(new Coordinate(x, y)).build();


            } else {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

