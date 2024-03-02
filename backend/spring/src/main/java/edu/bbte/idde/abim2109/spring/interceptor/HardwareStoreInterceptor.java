package edu.bbte.idde.abim2109.spring.interceptor;

import edu.bbte.idde.abim2109.spring.exception.BadRequestException;
import edu.bbte.idde.abim2109.spring.model.HardwareStore;
import edu.bbte.idde.abim2109.spring.service.jpa.HardwareStoreJpaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
@Slf4j
public class HardwareStoreInterceptor implements HandlerInterceptor {
    @Autowired
    private HardwareStoreJpaService hardwareStoreService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        Map<String, String> pathVariables =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (pathVariables != null) {
            String hardwareStoreId = pathVariables.get("hardwareStoreId");

            if (hardwareStoreId == null) {
                log.error("HardwareStore id is null");
                throw new BadRequestException("HardwareStore id is null");
            }

            HardwareStore hardwareStore = hardwareStoreService.getHardwareStoreById(Long.valueOf(hardwareStoreId));

            if (hardwareStore == null) {
                log.error("HardwareStore with id {} not found", hardwareStoreId);
                throw new BadRequestException("HardwareStore not found");
            }
        }

        return true;
    }
}

