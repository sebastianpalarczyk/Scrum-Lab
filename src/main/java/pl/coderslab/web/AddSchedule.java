package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@WebServlet("/app/plan/add")
public class AddSchedule extends HttpServlet {
    private PlanDao planDao = new PlanDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int adminId = 1;
        if (session.getAttribute("adminId") != null) {
            adminId = (int) session.getAttribute("adminId");
        }
        String planName = request.getParameter("planName");
        String planDescription = request.getParameter("planDescription");
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String created = myDateObj.format(myFormatObj);
        if (planName != null) {
            Plan plan = new Plan(planName, planDescription, created, adminId);
            planDao.create(plan);
        }
        response.sendRedirect("/schedules");
    }

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app-add-schedules.jsp")
                .forward(request, response);
    }
}
