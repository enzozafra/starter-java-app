import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpHeader {
  public static List<String> parseAcceptLanguage(String required, List<String> supported) {
    List<String> r = new ArrayList<String>();
    if (required == null || supported == null) return r;
    List<String> list = new ArrayList<>(supported);

    String[] ls = required.split(",\\s+");
    for (String l : ls) {
      if (l.length() == 5) {
        if (list.contains(l)) {
          r.add(l);
          list.remove(l);
        }
      } else if (l.length() == 2) {
        List<String> matchs = new ArrayList<>();
        for (String s : list) {
          if (s.startsWith(l)) {
            matchs.add(s);
          }
        }
        for (String s : matchs) {
          r.add(s);
          list.remove(s);
        }
      } else if (l.equals("*")) {
        for (String left : list) {
          r.add(left);
        }
      } else {
        throw new RuntimeException("not supported tag");
      }
    }
    return r;
  }

  public static void main(String[] args) {
    System.out.println(
        parseAcceptLanguage("en-US, fr-CA, fr-FR", Arrays.asList("fr-FR", "en-US"))
            .toString()
            .equals("[en-US, fr-FR]"));
    System.out.println(
        parseAcceptLanguage("fr-CA, fr-FR", Arrays.asList("en-US", "fr-FR"))
            .toString()
            .equals("[fr-FR]"));
    System.out.println(
        parseAcceptLanguage("en-US", Arrays.asList("en-US", "fr-CA")).toString().equals("[en-US]"));
    // part 2
    System.out.println(
        parseAcceptLanguage("en", Arrays.asList("en-US", "fr-CA", "fr-FR"))
            .toString()
            .equals("[en-US]"));
    System.out.println(
        parseAcceptLanguage("fr", Arrays.asList("en-US", "fr-CA", "fr-FR"))
            .toString()
            .equals("[fr-CA, fr-FR]"));

    System.out.println(
        parseAcceptLanguage("fr-FR, fr", Arrays.asList("en-US", "fr-CA", "fr-FR"))
            .toString()
            .equals("[fr-FR, fr-CA]"));
    // part 3
    System.out.println(
        parseAcceptLanguage("en-US, *", Arrays.asList("en-US", "fr-CA", "fr-FR"))
            .toString()
            .equals("[en-US, fr-CA, fr-FR]"));

    System.out.println(
        parseAcceptLanguage("fr-FR, fr, *", Arrays.asList("en-US", "fr-CA", "fr-FR"))
            .toString()
            .equals("[fr-FR, fr-CA, en-US]"));
  }
}
