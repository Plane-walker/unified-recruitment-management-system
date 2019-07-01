package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class Loginfilter
 */
@WebFilter("/Loginfilter")
public class Loginfilter implements Filter {

    /**
     * Default constructor. 
     */
    public Loginfilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpServletResponse resp = (HttpServletResponse)response;
        HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession();
        String ID = (String)session.getAttribute("ID");
        String type=(String)session.getAttribute("type");
        String uri = req.getRequestURI();
        if(ID==null)
        	resp.sendRedirect(req.getContextPath()+"/main");
        else
        	if((type.equals("login_app")||type.equals("register_app"))&&!uri.endsWith("market"))
        		resp.sendRedirect(req.getContextPath()+"/market");
        	else
        		if((type.equals("login_com")||type.equals("register_com"))&&!uri.endsWith("table"))
        			resp.sendRedirect(req.getContextPath()+"/table");
        		else
        	chain.doFilter(request, response);
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
