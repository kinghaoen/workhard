package org.springframework.security.web.authentication.ui;


import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

/**
 * Generates a default log out page.
 *
 * @author Rob Winch
 * @since 5.1
 *
 * Remake by LangRi
 */
public class DefaultLogoutPageGeneratingFilter extends OncePerRequestFilter {
    // 缺省用户请求退出登录页面识别匹配器 : GET, /logout
    private RequestMatcher matcher = new AntPathRequestMatcher("/logout", "GET");

    private Function<HttpServletRequest, Map<String, String>> resolveHiddenInputs = request -> Collections
            .emptyMap();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 如果当前请求是请求退出登录页面，则渲染该页面给用户并结束对请求的处理
        if (this.matcher.matches(request)) {
            renderLogout(request, response);
        }
        // 如果当前请求不是请求退出登录页面，继续filter chain的调用
        else {
            filterChain.doFilter(request, response);
        }
    }

    // 往response中写入一个HTML，这个HTML包含一个FORM表单，该表单包含一个按钮，点击该按钮提交该表单
    // 到退出登录处理URL(也就是由LogoutFilter所负责的逻辑)
    private void renderLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String page =  "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "  <head>\n"
                + "    <meta charset=\"utf-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"
                + "    <meta name=\"description\" content=\"\">\n"
                + "    <meta name=\"author\" content=\"\">\n"
                + "    <title>Confirm Log Out?</title>\n"
                + "    <link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M\" crossorigin=\"anonymous\">\n"
                + "    <link href=\"https://getbootstrap.com/docs/4.0/examples/signin/signin.css\" rel=\"stylesheet\" crossorigin=\"anonymous\"/>\n"
                + "  </head>\n"
                + "  <body>\n"
                + "     <div class=\"container\">\n"
                + "      <form id=\"logoutForm\" class=\"form-signin\" method=\"post\" action=\"" + request.getContextPath() + "/logout\">\n"

                + renderHiddenInputs(request)
                + "        <input type=hidden name=redirect_uri value="+request.getParameter("redirect_uri")+"  />\n"
                + "         <script>document.getElementById('logoutForm').submit()</script>"
                + "      </form>\n"
                + "    </div>\n"
                + "  </body>\n"
                + "</html>";

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(page);
    }

    /**
     * Sets a Function used to resolve a Map of the hidden inputs where the key is the
     * name of the input and the value is the value of the input. Typically this is used
     * to resolve the CSRF token.
     * @param resolveHiddenInputs the function to resolve the inputs
     */
    public void setResolveHiddenInputs(
            Function<HttpServletRequest, Map<String, String>> resolveHiddenInputs) {
        Assert.notNull(resolveHiddenInputs, "resolveHiddenInputs cannot be null");
        this.resolveHiddenInputs = resolveHiddenInputs;
    }

    // 将一些隐藏输入字段，比如csrf token等，追加到退出登录页面
    private String renderHiddenInputs(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> input : this.resolveHiddenInputs.apply(request).entrySet()) {
            sb.append("<input name=\"").append(input.getKey()).append("\" type=\"hidden\" value=\"").append(input.getValue()).append("\" />\n");
        }
        return sb.toString();
    }
}
