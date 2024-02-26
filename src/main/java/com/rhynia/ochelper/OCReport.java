package com.rhynia.ochelper;

import com.alibaba.fastjson2.JSONArray;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

@WebServlet(name = "oc-report", value = "/oc-report")
public class OCReport extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) {

        try {
            InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/classes/path.properties");
            Properties prop = new Properties();
            prop.load(is);
            String path = prop.getProperty("jsonpath");

            res.setContentType("text/html;charset=UTF-8");
            FileReader f = new FileReader(path);
            BufferedReader bw = new BufferedReader(f);
            String JSONRaw = bw.readLine();
            bw.close();

            List<AEItem> items = JSONArray.parseArray(JSONRaw).toList(AEItem.class);

            PrintWriter out = res.getWriter();
            out.println("<html><body>");
            for (AEItem item : items) {
                out.println("<h1>" + item.getAeLabel() + "</h1>");
                out.println("<h1>" + item.getAeSize() + "</h1>");
            }
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void destroy() {
    }

}
