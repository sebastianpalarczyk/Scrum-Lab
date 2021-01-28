/*
package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/testDayName")
public class TestDayName extends HttpServlet {

    private DayNameDao dayNameDao = new DayNameDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //response.getWriter().println(dayNameDao.findAll());
            response.getWriter().println(dayNameDao.read("monday"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }
}
*/
