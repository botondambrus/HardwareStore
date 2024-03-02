package edu.bbte.idde.abim2109.web.servlets;

import edu.bbte.idde.abim2109.backend.model.HardwareStore;
import edu.bbte.idde.abim2109.backend.repo.HardwareStoreDao;
import edu.bbte.idde.abim2109.backend.repo.factory.DaoFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet("/entity")
public class EntityServlet extends HttpServlet {
    private final HardwareStoreDao hardwareStoreDatabase = DaoFactory.getInstance().getHardwareStoreDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Collection<HardwareStore> items = hardwareStoreDatabase.findAll();
        List<HardwareStore> itemList = new ArrayList<>(items);
        req.setAttribute("items", itemList);
        req.getRequestDispatcher("entity.jsp").forward(req, res);
    }
}