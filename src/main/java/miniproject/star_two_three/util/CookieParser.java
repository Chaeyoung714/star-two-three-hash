package miniproject.star_two_three.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import miniproject.star_two_three.exception.CustomException;
import miniproject.star_two_three.exception.Exceptions;

public class CookieParser {

    public static String parseRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        throw new CustomException(Exceptions.NO_TOKEN);
    }
}
