/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *
 * @author stuart
 */
public class AdaptableHttpRequest extends HttpServletRequestWrapper{

   private HashMap<String, String> params = new HashMap();

   public AdaptableHttpRequest(HttpServletRequest request) {
           super(request);
   }

   public String getParameter(String name) {
           // if we added one, return that one
           if ( params.get( name ) != null ) {
                 return params.get( name );
           }
           // otherwise return what's in the original request
           HttpServletRequest req = (HttpServletRequest) super.getRequest();
           return req.getParameter( name );
   }

   public void addParameter( String name, String value ) {
           params.put( name, value );
   }
}
