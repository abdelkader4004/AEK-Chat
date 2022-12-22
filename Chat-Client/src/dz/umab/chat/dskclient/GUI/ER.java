package dz.umab.chat.dskclient.GUI;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ER {
    public static void main(String args[]) {
        try {
            Pattern p = Pattern.compile("(?:\\w|[\\-_])+(?:\\.(?:\\w|[\\-_])+)*");
            String entree = "hghhhhjj---klj";
            //entree=entree.toLowerCase();
            Matcher m = p.matcher(entree);
            System.out.println(m.matches());
            while (m.find())
                System.out.println(entree.substring(m.start(), m.end()));
        } catch (PatternSyntaxException pse) {
        }
    }
}