package textanalysis;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sssd on 2017/7/17.
 */
public class ReadJsonFile {

    public static Map<Integer,String> readJsonFile(String jsonPath) throws Exception{
        Map<Integer,String> textMap = new LinkedHashMap<Integer, String>();
        File file = new File(jsonPath);
        BufferedReader reader = null;
        StringBuffer laststr = new StringBuffer();
        reader = new BufferedReader(new FileReader(file));
        String tempString = null;
        //一次读入一行，直到读入null为文件结束
        while ((tempString = reader.readLine()) != null) {
            JSONObject jo= new JSONObject(tempString);
            if (jo.has("content")){
                laststr.append(jo.getString("content")).append("\n");
            }
        }
        laststr.deleteCharAt(laststr.length()-1);
        reader.close();
        textMap.put(1,laststr.toString());
        return textMap;
    }
}
