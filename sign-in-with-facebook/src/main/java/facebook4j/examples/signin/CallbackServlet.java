package facebook4j.examples.signin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.RawAPIResponse;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

public class CallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 6305643034487441839L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
        String oauthCode = request.getParameter("code");
        try {
        	AccessToken info=facebook.getOAuthAccessToken(oauthCode);
        	System.out.println("token : "+info.getToken());
        	request.getSession().setAttribute("facebook", facebook);
        	RawAPIResponse res = facebook.callGetAPI("me/friends");
        	JSONObject jsonObject = res.asJSONObject();
        	JSONArray data = jsonObject.getJSONArray("data");
        	request.getSession().setAttribute("data", data);
        } catch (FacebookException e) {
            throw new ServletException(e);
        } catch (JSONException e) {
        	throw new ServletException(e);
		}
        response.sendRedirect(request.getContextPath() + "/");
    }
}
