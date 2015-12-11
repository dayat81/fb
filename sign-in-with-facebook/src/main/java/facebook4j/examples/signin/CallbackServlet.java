package facebook4j.examples.signin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import facebook4j.Facebook;
import facebook4j.RawAPIResponse;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONObject;

public class CallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 6305643034487441839L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Facebook facebook = (Facebook) request.getSession().getAttribute("fb");
        String oauthCode = request.getParameter("code");
        try {
        	AccessToken info=facebook.getOAuthAccessToken(oauthCode);
        	System.out.println("token : "+info.getToken());
        	request.getSession().setAttribute("facebook", facebook);
        	RawAPIResponse res = facebook.callGetAPI("me/friends");
        	JSONObject jsonObject = res.asJSONObject();
        	JSONArray data = jsonObject.getJSONArray("data");
        	request.getSession().setAttribute("data", data);
        	//insert fb name & id to mongo
        	String ref=(String)request.getSession().getAttribute("ref");
        	System.out.println("ref cb "+ref);
        	//check ref exists in users collection
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "mlm" );
			DBCollection coll = db.getCollection("users");
			BasicDBObject whereQuery1 = new BasicDBObject();
			whereQuery1.put("id", facebook.getId());
			DBCursor cursor1 = coll.find(whereQuery1);
			if(!cursor1.hasNext()){
				BasicDBObject whereQuery = new BasicDBObject();
				whereQuery.put("id", ref);
				DBCursor cursor = coll.find(whereQuery);
				if(!cursor.hasNext()) {
					ref="-1";
				}
				DBObject listItem = new BasicDBObject("id", facebook.getId()).append("name",facebook.getName()).append("ref", ref);
				coll.insert(listItem);		
			}
        } catch(com.mongodb.MongoException.DuplicateKey e){
        	e.printStackTrace();
        }catch (Exception e) {
            throw new ServletException(e);
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
}
