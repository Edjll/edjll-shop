package ru.edjll.shop.config.filter;

import com.google.gson.Gson;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.edjll.shop.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomUsernamePasswordAuthenticationFilter() {
        super();
    }

    private User getUser(HttpServletRequest request) {
        try {
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(request.getPart("user").getInputStream());
            return gson.fromJson(reader, User.class);
        } catch (IOException | ServletException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        User user = getUser(request);
        if (user == null) return null;
        return user.getEmail();
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        User user = getUser(request);
        if (user == null) return null;
        return user.getPassword();
    }
}
