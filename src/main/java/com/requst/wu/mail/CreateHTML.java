/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requst.wu.mail;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zagraevskaya
 */
public class CreateHTML {

    public String creatHtmlBodyMail(Map<String,String> params){
      StringBuilder stb=new StringBuilder();
      
      stb.append("<html><body>"
                    + "<table border='2'>");
        
      for (Map.Entry<String, String> pair : params.entrySet()){
            stb.append("<tr>")
                .append("<td>").append(pair.getKey()).append("</td>")
                .append("<td>").append(pair.getValue()).append("</td>")      
            .append("</tr>");
      }
      stb.append("</html></body>");
     return stb.toString();
    }
    
    
}
