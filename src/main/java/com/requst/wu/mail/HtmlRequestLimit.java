/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requst.wu.mail;

import com.requst.wu.model.AcWuDictUserTermDiv;
import com.requst.wu.model.WuRequestLimit;

/**
 *
 * @author zagraevskaya
 */
public class HtmlRequestLimit {

    public String creatHtmlBodyMail(WuRequestLimit wuRequest, AcWuDictUserTermDiv acWuDictUser){
      StringBuilder stb=new StringBuilder();

      stb.append("<html><body>");
      stb.append("<p>Доброго дня!</p>");
      stb.append("<p>Прошу збільшити ліміт на відправку переказу по системі Western Union.</p>");
      stb.append("<p><font color=\"#FF0000\">Відповідь (при необхідності) прошу надсилати на адресу "+acWuDictUser.getOperatorEmail()+"</p>");
      stb.append("<table border='2'>");  
      stb.append("<tr bgcolor=\"#c0c0c0\"><td>Cума</td><td>"+wuRequest.getAmount()+"</td></tr>")
         .append("<tr bgcolor=\"#c0c0c0\"><td>Валюта</td><td>"+wuRequest.getCurrency()+"</td></tr>")
         .append("<tr><td>Термінал</td><td>"+acWuDictUser.getCodeTerminal()+"</td></tr>")
         .append("<tr><td>Відправлено по терміналу</td><td><font color=\"#FF0000\">"+wuRequest.getAmountSend()+"</td></tr>")
         .append("<tr><td>Субагент</td><td>"+"ACCORDBANK,Branch#"+acWuDictUser.getTt()+"</td></tr>")
         .append("<tr><td>Код оператора</td><td>"+acWuDictUser.getOperatorNo().toString()+"</td></tr>")
         .append("<tr><td>Виконавець</td><td>"+acWuDictUser.getOperatorFio()+"</td></tr>")
         .append("<tr><td>Коментар</td><td>"+wuRequest.getComent()+"</td></tr>");
      
         
      stb.append("</html></body>");
     return stb.toString();
    }
    
}
