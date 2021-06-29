package com.requst.wu.mail;

import com.requst.wu.model.AcWuDictUserTermDiv;
import com.requst.wu.model.WuRequestPayment;

public class HtmlRequestPayment {

        public String creatHtmlBodyMail(WuRequestPayment wuRequest, AcWuDictUserTermDiv acWuDictUser){
            StringBuilder stb=new StringBuilder();

            stb.append("<html><body>");
            stb.append("<p>Доброго дня!</p>");
            stb.append("<p>Шановні колеги,</p>");
            stb.append("<p>У додатку запит на зміну реквізитів переказу.</p>");
            stb.append("<p><font color=\"#FF0000\">Відповідь (при необхідності) прошу надсилати на адресу "+acWuDictUser.getOperatorEmail()+"</p>");
            stb.append("<table border='2'>");
            stb.append("<tr><td>Термінал</td><td>"+acWuDictUser.getCodeTerminal()+"</td></tr>")
                    .append("<tr><td>Субагент</td><td>"+"ACCORDBANK,Branch#"+acWuDictUser.getTt()+"</td></tr>")
                    .append("<tr><td>Код оператора</td><td>"+acWuDictUser.getOperatorNo().toString()+"</td></tr>")
                    .append("<tr><td>Виконавець</td><td>"+acWuDictUser.getOperatorFio()+"</td></tr>")
                    .append("<tr><td>Коментар</td><td>"+wuRequest.getComent()+"</td></tr>");


            stb.append("</html></body>");
            return stb.toString();
        }

    }


