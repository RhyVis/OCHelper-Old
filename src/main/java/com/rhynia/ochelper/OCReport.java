package com.rhynia.ochelper;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.util.ApacheLang3Support;
import com.csvreader.CsvReader;
import com.rhynia.ochelper.ae.AEFluid;
import com.rhynia.ochelper.ae.AEItem;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@WebServlet(name = "oc-report", value = "/oc-report")
public class OCReport extends HttpServlet {

    private static Map<Pair<String, Integer>, String> NAME_MAP_ITEM = new HashMap<>();
    private static Map<String, String> NAME_MAP_FLUID = new HashMap<>();

    public void init() {
        try {
            InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/classes/path.properties");
            Properties prop = new Properties();
            prop.load(is);
            String csvPath = prop.getProperty("csv.path");

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) {

        try {
            InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/classes/path.properties");
            Properties prop = new Properties();
            prop.load(is);
            String jsonPath = prop.getProperty("json.path");
            String iconPath = prop.getProperty("icon.path");
            String altPath = prop.getProperty("icon.default");

            res.setContentType("text/html;charset=UTF-8");
            FileReader f_i = new FileReader(jsonPath + "indexItem.json");
            BufferedReader bw_i = new BufferedReader(f_i);
            String JSONItemRaw = bw_i.readLine();
            bw_i.close();

            res.setContentType("text/html;charset=UTF-8");
            FileReader f_f = new FileReader(jsonPath + "indexFluid.json");
            BufferedReader bw_f = new BufferedReader(f_f);
            String JSONFluidRaw = bw_f.readLine();
            bw_f.close();

            List<AEItem> items = JSONArray.parseArray(JSONItemRaw).toList(AEItem.class);
            List<AEFluid> fluids = JSONArray.parseArray(JSONFluidRaw).toList(AEFluid.class);

            PrintWriter out = res.getWriter();
            out.println("<link rel=\"stylesheet\" href=\"style.css\">");
            out.println("<html><body>");

            out.println("<h1>" + "<  物品  >" + "</h1>");
            for (AEItem item : items) {
                if (Objects.equals(item.getAeName(), "ae2fc:fluid_drop")) continue;

                Pair<String, Integer> sign = Pair.of(item.getAeName(), item.getAeMeta());
                String local;
                if (NAME_MAP_ITEM.containsKey(sign)) {
                    local = NAME_MAP_ITEM.get(sign);
                } else {
                    local = item.getAeLabel();
                }

                out.println("<p><line>" + local + " : " + item.getAeSize() + "</line></p>");
            }
            out.println("<h1>" + "<  流体  >" + "</h1>");
            for (AEFluid fluid : fluids) {
                String local;
                if (NAME_MAP_FLUID.containsKey(fluid.getAeName())) {
                    local = NAME_MAP_FLUID.get(fluid.getAeName());
                } else {
                    local = fluid.getAeLabel();
                }

                out.println("<p><line>" + local + " : " + fluid.getAeSize() + "</line></p>");
            }

            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void destroy() {
    }

}
