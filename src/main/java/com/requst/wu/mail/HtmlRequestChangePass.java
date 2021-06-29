package com.requst.wu.mail;

import com.requst.wu.model.AcWuDictUserTermDiv;
import com.requst.wu.model.WuRequestChangePass;

public class HtmlRequestChangePass {

        public String creatHtmlBodyMail(WuRequestChangePass wuRequest, AcWuDictUserTermDiv acWuDictUser){
            StringBuilder stb=new StringBuilder();

            stb.append("<html><body>");
            stb.append("<p>Доброго дня!</p>");
            stb.append("<p>Прошу сбросить пароль по системе Western Union</p>");
            stb.append("<p><font color=\"#FF0000\">Відповідь необхідно надіслати на адресу "+acWuDictUser.getOperatorEmail()+"</p>");
            stb.append("<table border='2'>");
            stb.append("<tr><td>ТТ</td>")
                    .append("<td>Місто</td>")
                    .append("<td>Вулиця, дім</td>")
                    .append("<td>USD</td>")
                    .append("<td>Код оператора</td>")
                    .append("<td>Оператор (ФИО, Имя)</td>")
                    .append("<td> С - изменить/сбросить пароль</td>")
                    .append("<td>Терминал</td>")
                    .append("</tr>")
                    .append("<tr><td>"+acWuDictUser.getTt()+"</td>")
                    .append("<td>"+acWuDictUser.getCity()+"</td>")
                    .append("<td>"+acWuDictUser.getAddress()+"</td>")
                    .append("<td>"+acWuDictUser.getCodeUsd()+"</td>")
                    .append("<td>"+acWuDictUser.getOperatorNo().toString()+"</td>")
                    .append("<td>"+acWuDictUser.getOperatorFio()+"</td>")
                    .append("<td>C</td>")
                    .append("<td>"+acWuDictUser.getCodeTerminal()+"</td>")
                    .append("</tr>");


            stb.append("</html></body>");
            return stb.toString();
        }

    }


