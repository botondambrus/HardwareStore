package edu.bbte.idde.abim2109.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.abim2109.backend.model.HardwareStore;
import edu.bbte.idde.abim2109.backend.repo.HardwareStoreDao;
import edu.bbte.idde.abim2109.backend.repo.RepositoryException;
import edu.bbte.idde.abim2109.backend.repo.factory.DaoFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

import static jakarta.servlet.http.HttpServletResponse.*;

@WebServlet("/hardware-store")
public class HardwareStoreServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HardwareStoreDao hardwareStoreDatabase = DaoFactory.getInstance().getHardwareStoreDao();

    private void handleErrorResponse(HttpServletResponse res, int statusCode, String message) throws IOException {
        res.setStatus(statusCode);
        res.setContentType("application/json");
        res.getWriter().write("{\"error\": \"" + message + "\"}");
    }

    private void setCorsHeader(HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) {
        setCorsHeader(res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        setCorsHeader(res);
        try {
            String idParam = req.getParameter("id");

            if (idParam == null) {
                Collection<HardwareStore> items = hardwareStoreDatabase.findAll();
                res.getWriter().write(objectMapper.writeValueAsString(items));

            } else {
                Integer id = Integer.parseInt(idParam);
                HardwareStore item = hardwareStoreDatabase.findById(id);

                if (item == null) {
                    handleErrorResponse(res, SC_NOT_FOUND, "Item not found with ID: " + id);

                } else {

                    res.getWriter().write(objectMapper.writeValueAsString(item));
                }
            }
        } catch (NumberFormatException e) {
            handleErrorResponse(res, SC_BAD_REQUEST, "Invalid ID format");
        } catch (IOException e) {
            handleErrorResponse(res, SC_BAD_REQUEST, "Required fields are missing");
        } catch (RepositoryException e) {
            handleErrorResponse(res, SC_INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        setCorsHeader(res);
        try {
            HardwareStore item = objectMapper.readValue(req.getReader(), HardwareStore.class);
            if (item.getName() == null || item.getCategory() == null || item.getDescription() == null
                    || item.getPrice() < 0 || item.getQuantity() < 0) {
                res.setStatus(SC_BAD_REQUEST);
                res.getWriter().write("Required fields are missing");

                return;
            }

            HardwareStore createdItem = hardwareStoreDatabase.create(item);
            res.getWriter().write(objectMapper.writeValueAsString(createdItem));

        } catch (IOException e) {
            handleErrorResponse(res, SC_BAD_REQUEST, "Required fields are missing");
        } catch (RepositoryException e) {
            handleErrorResponse(res, SC_INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        setCorsHeader(res);
        try {
            String idParam = req.getParameter("id");

            if (idParam == null) {
                res.setStatus(SC_BAD_REQUEST);
                res.getWriter().write("ID is missing");

            } else {
                Integer id = Integer.parseInt(idParam);
                HardwareStore item = objectMapper.readValue(req.getReader(), HardwareStore.class);
                HardwareStore updatedItem = hardwareStoreDatabase.update(id, item);

                if (updatedItem == null) {
                    res.setStatus(SC_NOT_FOUND);
                    res.getWriter().write("Update is not possible with ID: " + id);
                } else {
                    res.getWriter().write(objectMapper.writeValueAsString(updatedItem));
                }
            }
        } catch (NumberFormatException e) {
            handleErrorResponse(res, SC_BAD_REQUEST, "Invalid ID format");
        } catch (IOException e) {
            handleErrorResponse(res, SC_BAD_REQUEST, "Required fields are missing");
        } catch (RepositoryException e) {
            handleErrorResponse(res, SC_INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        setCorsHeader(res);
        try {
            String idParam = req.getParameter("id");

            if (idParam == null) {
                res.setStatus(SC_BAD_REQUEST);
                res.getWriter().write("ID parameter is required");
            } else {
                Integer id = Integer.parseInt(idParam);
                hardwareStoreDatabase.delete(id);
            }

        } catch (NumberFormatException e) {
            handleErrorResponse(res, SC_BAD_REQUEST, "Invalid ID format");
        } catch (IOException e) {
            handleErrorResponse(res, SC_BAD_REQUEST, "Required fields are missing");
        } catch (RepositoryException e) {
            handleErrorResponse(res, SC_INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
        }
    }
}
