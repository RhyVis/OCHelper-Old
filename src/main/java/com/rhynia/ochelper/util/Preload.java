package com.rhynia.ochelper.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;

import com.alibaba.fastjson2.JSONArray;
import com.csvreader.CsvReader;
import com.rhynia.ochelper.jsonMap.SwitchFluid;

@WebServlet(loadOnStartup = 1, name = "preload", value = "/preload")
public class Preload extends HttpServlet {
    public static String csvPath;
    public static String jsonPath;
    public static String iconPath;
    public static String altPath;
    public static Map<Pair<String, Integer>, String> NAME_MAP_ITEM = new HashMap<>();
    public static Map<String, String> NAME_MAP_FLUID = new HashMap<>();
    public static final Map<String, String> SWITCH_MAP_NAME_FLUID = new HashMap<>();

    public void init() {
        try {

            // Load Properties
            InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/classes/path.properties");
            Properties prop = new Properties();
            prop.load(is);
            csvPath = prop.getProperty("csv.path");
            jsonPath = prop.getProperty("json.path");
            iconPath = prop.getProperty("icon.path");
            altPath = prop.getProperty("icon.default");
            is.close();

            // CSV Loader
            CsvReader itemCSV = new CsvReader(csvPath + "itempanel.csv", ',', StandardCharsets.UTF_8);
            itemCSV.readHeaders();
            while (itemCSV.readRecord()) {
                Pair<String, Integer> subject = Pair.of(itemCSV.get(0), Integer.valueOf(itemCSV.get(2)));
                NAME_MAP_ITEM.put(subject, itemCSV.get(4));
            }
            CsvReader fluidCSV = new CsvReader(csvPath + "fluid.csv", ',', StandardCharsets.UTF_8);
            fluidCSV.readHeaders();
            while (fluidCSV.readRecord()) {
                NAME_MAP_FLUID.put(fluidCSV.get(1), fluidCSV.get(2));
            }

            // Switch Name Loader
            InputStream is_f = this.getServletContext().getResourceAsStream("/WEB-INF/classes/switch_fluid.json");
            InputStreamReader is_fr = new InputStreamReader(is_f);
            BufferedReader bw_i = new BufferedReader(is_fr);
            String JSON_FS_Raw = bw_i.readLine();
            List<SwitchFluid> switchFluids = JSONArray.parseArray(JSON_FS_Raw).toList(SwitchFluid.class);

            for (SwitchFluid switchFluid : switchFluids) {
                SWITCH_MAP_NAME_FLUID.put(switchFluid.getPreName(), switchFluid.getAltName());
            }

        } catch (Exception ignored) {
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" /> ");
        out.println("<html><head><title>Preloader</title></head>");
        out.println("<body>");
        out.println("<h1>This is the dummy page for preloader.</h1>");
        out.println("</body></html>");
    }
}
