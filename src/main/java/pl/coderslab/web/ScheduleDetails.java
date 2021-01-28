package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.PlanIdDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/ScheduleDetails")
public class ScheduleDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Plan plan = PlanDao.read(id);
            HttpSession session = request.getSession();
            session.setAttribute("plan", plan);

            List<PlanId> currentPlan = PlanIdDao.readCurrentPlan(id);
            session.setAttribute("currentPlan", currentPlan);

            response.sendRedirect("/scheduleDetails.jsp");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
