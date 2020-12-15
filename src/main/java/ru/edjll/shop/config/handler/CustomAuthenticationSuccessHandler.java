package ru.edjll.shop.config.handler;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.model.cart.CartProduct;
import ru.edjll.shop.service.BasketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private BasketService basketService;

    public CustomAuthenticationSuccessHandler() {
        super();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(request.getPart("cart").getInputStream());
            List<CartProduct> cart = gson.fromJson(reader, new TypeToken<List<CartProduct>>() {}.getType());
            if (cart != null) {
                cart.forEach(cartProduct -> {
                    basketService.save((User) authentication.getPrincipal(), cartProduct);
                });
            }
        } catch (JsonParseException exception) {
            exception.printStackTrace();
        }
        String targetUrl = this.determineTargetUrl(request, response, authentication);
        response.setHeader("Referer", targetUrl);
        this.clearAuthenticationAttributes(request);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        if (this.isAlwaysUseDefaultTargetUrl()) {
            return this.getDefaultTargetUrl();
        } else {
            String targetUrl = null;
            if (this.getTargetUrlParameter() != null) {
                targetUrl = request.getParameter(this.getTargetUrlParameter());
                if (StringUtils.hasText(targetUrl)) {
                    this.logger.debug("Found targetUrlParameter in request: " + targetUrl);
                    return targetUrl;
                }
            }

            if (!StringUtils.hasLength(targetUrl)) {
                DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest)request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                if (defaultSavedRequest != null && !defaultSavedRequest.getRequestURI().equals(request.getRequestURI())) {
                    targetUrl = defaultSavedRequest.getRequestURL();
                    this.logger.debug("Using session referer: " + targetUrl);
                }
            }

            if (!StringUtils.hasText(targetUrl)) {
                targetUrl = this.getDefaultTargetUrl();
                this.logger.debug("Using default Url: " + targetUrl);
            }

            return targetUrl;
        }
    }
}
