package com.rhynia.ochelper;

import static com.rhynia.ochelper.util.LocalSwitch.trySwitchFluidName;
import static com.rhynia.ochelper.util.Preload.NAME_MAP_FLUID;
import static com.rhynia.ochelper.util.Preload.NAME_MAP_ITEM;
import static com.rhynia.ochelper.util.Preload.altPath;
import static com.rhynia.ochelper.util.Preload.iconPath;
import static com.rhynia.ochelper.util.Preload.jsonPath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;

import com.alibaba.fastjson2.JSONArray;
import com.rhynia.ochelper.jsonMap.AEFluid;
import com.rhynia.ochelper.jsonMap.AEItem;

@WebServlet(name = "ae-storage-report", value = "/ae-storage-report")
public class AEStorageReport extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("text/html;charset=UTF-8");

        FileReader f_i = new FileReader(jsonPath + "indexItem.json");
        BufferedReader bw_i = new BufferedReader(f_i);
        String JSONItemRaw = bw_i.readLine();
        f_i.close();
        FileReader f_f = new FileReader(jsonPath + "indexFluid.json");
        BufferedReader bw_f = new BufferedReader(f_f);
        String JSONFluidRaw = bw_f.readLine();
        f_f.close();

        List<AEItem> items = JSONArray.parseArray(JSONItemRaw).toList(AEItem.class);
        List<AEFluid> fluids = JSONArray.parseArray(JSONFluidRaw).toList(AEFluid.class);

        PrintWriter out = res.getWriter();
        out.println("<link rel=\"stylesheet\" href=\"static/dist/css/adminlte.min.css\">");
        out.println("<script src=\"static/dist/js/adminlte.min.js\"></script>");
        out.println(
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" /> ");

        out.println("<html><head><title>AE Storage Center</title></head>");
        out.println("<body>");

        out.println(
            "<script src=\"https://cdn.jsdelivr.net/npm/overlayscrollbars@2.3.0/browser/overlayscrollbars.browser.es6.min.js\" integrity=\"sha256-H2VM7BKda+v2Z4+DRy69uknwxjyDRhszjXFhsL4gD3w=\" crossorigin=\"anonymous\"></script>");
        out.println(
            "<script src=\"https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js\" integrity=\"sha256-whL0tQWoY1Ku1iskqPFvmZ+CHsvmRWx/PIoEvIeWh4I=\" crossorigin=\"anonymous\"></script>");
        out.println(
            "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js\" integrity=\"sha256-YMa+wAM6QkVyz999odX7lPRxkoYAan8suedu4k2Zur8=\" crossorigin=\"anonymous\"></script>");

        out.println("<main class=\"app-main\">");
        out.println("<h1 style=\"text-align: center;\" class=\"mb-0\">[  物品  ]</h1>");
        out.println("</br>");
        out.println("<div class=\"container-fluid\">");
        out.println("<div class=\"row\">");

        for (AEItem item : items) {
            if (Objects.equals(item.getAeName(), "ae2fc:fluid_drop"))
                continue;
            if (Objects.equals(item.getAeSize(), "0"))
                continue;

            Pair<String, Integer> sign = Pair.of(item.getAeName(), item.getAeMeta());
            String local;
            if (NAME_MAP_ITEM.containsKey(sign)) {
                local = NAME_MAP_ITEM.get(sign);
            } else {
                local = item.getAeLabel();
            }

            String imgLocal =
                "<img src=\"" + iconPath + local + ".png\", alt=\"" + altPath + "\", width=\"50\", height=\"50\">";

            out.println("<div class=\"col-12 col-sm-6 col-md-3\" style=\"min-width: 100px;\">");
            out.println(
                "<div class=\"info-box\"> <span class=\"info-box-icon text-bg-light shadow-sm\" style=\"min-width: 80px;min-height: 80px;\">"
                    + imgLocal + "</span>");
            out.println("<div class=\"info-box-content\"> <span class=\"info-box-text\">"
                + (item.hasAeTag() ? item.getAeLabel() : local)
                + "</span> <span class=\"info-box-number\" style=\"min-width: 180px;\">" + item.getAeSizeDisplay() + " "
                + item.getAeSizeByte() + "</span> </div> </div>");
            out.println("</div>");
        }
        out.println("</div></div>");

        out.println("<h1 style=\"text-align: center;\" class=\"mb-0\">[  流体  ]</h1>");
        out.println("</br>");
        out.println("<div class=\"container-fluid\">");
        out.println("<div class=\"row\">");
        for (AEFluid fluid : fluids) {
            String local;
            if (NAME_MAP_FLUID.containsKey(fluid.getAeName())) {
                local = NAME_MAP_FLUID.get(fluid.getAeName());
            } else {
                local = fluid.getAeLabel();
            }

            String imgLocal = "<img src=\"" + iconPath + trySwitchFluidName(local) + "单元.png\", alt=\"" + altPath
                + "\", width=\"50\", height=\"50\">";

            out.println("<div class=\"col-12 col-sm-6 col-md-3\" style=\"min-width: 100px;\">");
            out.println(
                "<div class=\"info-box\"> <span class=\"info-box-icon text-bg-light shadow-sm\" style=\"min-width: 80px;min-height: 80px;\">"
                    + imgLocal + "</span>");
            out.println("<div class=\"info-box-content\"> <span class=\"info-box-text\">" + local
                + "</span> <span class=\"info-box-number\" style=\"min-width: 180px;\">" + fluid.getAeSizeDisplay()
                + " " + fluid.getAeSizeByte() + "</span> </div> </div>");
            out.println("</div>");
        }
        out.println("</div></div>");

        out.println("</main>");
        out.println("</body></html>");

    }

    public void destroy() {}

}
