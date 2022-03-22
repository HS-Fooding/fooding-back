package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.var.Variable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class GeocodeService {
    private static String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/address.json?query=";
    private static String GEOCODE_USER_INFO = Variable.KakaoKey;

    public Map<String, Float> getGeocode(String address){
        Map result = new HashMap<String, Float>();
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
            int size = (int)jMetaObject.get("total_count");

            if(size == 1){
                String addrType = ((JSONObject) jObject.getJSONArray("documents").get(0)).getString("address_type");
                if(addrType.equals("REGION") || addrType.equals("ROAD"))
                    return result;
                x = ((JSONObject) jObject.getJSONArray("documents").get(0)).getFloat("x");
                y = ((JSONObject) jObject.getJSONArray("documents").get(0)).getFloat("y");
            } else {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("x", x);
        result.put("y", y);
        return result;
    }
}

