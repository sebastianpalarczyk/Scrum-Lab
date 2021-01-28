package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private AdminDao adminDao = new AdminDao();

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String email = request.getParameter("email");

        if (repassword.equals(password)) {
            try {
                Admin admin = new Admin(name, surname, email, password, 0, true);
                adminDao.create(admin);
                response.sendRedirect("/login");

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        } else {

            PrintWriter out = response.getWriter();
            String title = "Incorrect  passwords!!!";
            String docType =
                    "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

            out.println(docType +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"pl-pl\" lang=\"pl-PL\" dir=\"ltr\" >\n" +
                    "<head>\n" +
                    "<title>" + title + "</title>\n" +

                    "<meta content=\"text/html\" charset=\"UTF-8\">\n" +
                    "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\"\n" +
                    "          crossorigin=\"anonymous\">\n" +
                    "    <link href=\"https://fonts.googleapis.com/css?family=Charmonman:400,700|Open+Sans:400,600,700&amp;subset=latin-ext\"\n" +
                    "          rel=\"stylesheet\">\n" +
                    "    <link href='<c:url value=\"/css/style.css\"/>' rel=\"stylesheet\" type=\"text/css\">\n" +
                    "    <link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.5.0/css/all.css\" integrity=\"sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU\" crossorigin=\"anonymous\">" +
                    "</head>\n" +
                    "<body>\n" +
                    "<script type=\"text/javascript\">\n" +
                    "setTimeout(\"location.href='/register';\",5000);\n" +
                    "</script>\n" +
                    "<h1 align = \"center\">" + title + "</h1>\n" +
                    "<p align = \"center\">Dokonaj ponownej rejestracji...</p>\n"
            );
        }

    }
}

