package string;

public class Numeronym {
  /*

  Everyday we look at a lot of URLs, for example in our log files from client requests.
  We want our data science team to perform analytics and machine learning, but:

  1. we want to preserve the privacy of the user, but without completely obfuscating/hashing
  the URLs and making them useless,
  2. we simply have a lot of data and we want to reduce our storage/processing costs
  In real world, we may solve this with hashing; due to the time constraints of the interview,
  we use numeronyms (noo-MER-o-nim, number-based world like "S12n") instead to compress Strings.


  Part 1:
  Given a String, split it into **major parts** separated by special char '/'. For each
  major part that's split by '/', we can further split it into **minor parts** separated by '.'.

  We assume the given Strings:
  - Only have lower case letters and two separators ('.', '/').
  - Have no empty minor parts (no leading / trailing separators or consecutive separators
    like a "/a", "a/", "./..").
  - Have >= 3 letters in each minor part.

  Ex:
  stripe.com/payments/checkout/customer.maria

  =>
  Major Part 1:
    minor part 1:
      stripe
    minor part 2:
      com
  Major Part 2:
    minor part 1:
      payments
  Major Part 3:
    minor part 1:
      checkout
  Major Part 4:
    minor part 1:
      customer
    minor part 2:
      maria

   => Output String: s4e.c1m/p6s/c6t/c6r.m3a


  Part 2:
  In some cases, major parts consists of dozens of minor parts, that can still make the ouput
  String large. For example, imagine compressing a URL such as
  "section/how.to.write.a.java.program.in.one.day". After compressing it by following the rules
  in Part 1, the second major part still has 9 minor parts after compression.

  **Task**: Therefore, to further compress the String, we want to only keep m (m>0) compressed
  minior parts from Part 1 **within** each major part. If a major part has more than m minor parts,
  we keep the first (m-1) minor parts as is, but concatenate the first letter of the m-th minor
  part and the last letter of the last minor part with the count.


  Tips from 1point3acres:

  Not clear what is the meaing of the `m`.
  Just assume `m` means at most keep m minors for each major,
  when there are more than `m` minors in a major,
  the first m-1 one are handled as usual,
  but left minors [mth,last] will be merged as the m-th one

  Java:
    String.split("\\."); // when it is . need escape
   */
  public static String compress(String input, int m) {
    StringBuilder r = new StringBuilder();
    // assume input is valid, else need validation in advance
    String[] majs = input.split("/");
    for (int i = 0; i < majs.length; i++) {
      String maj = majs[i];
      String[] mins = maj.split("\\.");
      int j = 0;
      int sum = 0;
      for (; j < Math.min(m - 1, mins.length); j++) {
        String min = mins[j];
        sum += min.length();
        r.append(min.charAt(0));
        r.append(min.length() - 2);
        r.append(min.charAt(min.length() - 1));
        if (j != mins.length - 1) r.append(".");
      }
      if (j < mins.length) {
        r.append(maj.charAt(sum));
        sum = 0;
        for (; j < mins.length; j++) sum += mins[j].length();
        r.append(sum - 2);
        r.append(maj.charAt(maj.length() - 1));
      }
      if (i != majs.length - 1) r.append("/");
    }
    return r.toString();
  }

  public static void main(String args[]) {
    System.out.println(
        compress("stripe.com/checkout/payments/customer.maria.bay.area.next.job", 3)
            .equals("s4e.c1m/c6t/p6s/c6r.m3a.b12b"));
  }
}
