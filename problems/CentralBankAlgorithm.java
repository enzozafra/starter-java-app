import java.util.*;

/*
 Looks like question is similar to https://leetcode.com/problems/optimal-account-balancing/

 Netting Engine - ACH association
 Minimum number of transfers required at the end of the day based on the net balances of individual banks
 A B C banks -> list of input transfers -> output transfers

    Central Bank Algorithm
    ----------------------

    1. Calc net balance of each bank
    2. Generate one transfer with either send/receive the net amount

STEP 1
    // A, B, amount
    // int[] sendAmts {}
    // int[] receiveAmts {}
    // int[] netbalance {}

STEP 2
    // If the balance is positive, there is transfer from Bank A to Bank i with amount netbalance
    // Else if the balance is negative, there is a transfer from Bank i to Bank A with amount mod(netbalance)

    positive balances
    _ _ _ -> _A_ dest post balance

    negative balance
    _ _ _ -> src _A_ Mod(-neg balance)

Examples:

    Input:
        AB1
        BA2
        ------
        BA1

    Input:
        AB1         A 1   s:2 r:3 -> 1
        BA2 -> BA1  B -2  s:2+1 r:1 -> -2
        BC1 -> BC1  C 1   r:2 -> 2
        AC1         D     s:1 -> -1
        DA1

        Net Balances:
        A: 1  (send 1, receive 2) [receive - send] = net balance
        B: -2 (send 2, receive 1, send 1)
        C: 1  (receive 1)

     Output:
        BA2
        AC2
        DA1

            B,C,D --------> (A) ------->    B,C,D


Input
    B:-2 C:0 D:2

    BC2
    CD2

    B,C,D --------> (A) ------->    B,C,D

Output
    BA2
    AD2

*/

public class Solution {

  static final String CENTRAL_BANK = "A";
  static final int CENTRAL_BANK_ID = 0;

  private List<String> generateMinimumTransfers(List<String> transferDetails) {
    List<String> transfersList = new ArrayList<>();
    if (transferDetails == null || transferDetails.size() == 0) {
      return transfersList;
    }

    int[] sendAmt = new int[26];
    int[] receiveAmt = new int[26];
    int[] netBalance = new int[26];

    for (String t : transferDetails) {
      int srcIdx = t.charAt(0) - 'A';
      int destIdx = t.charAt(1) - 'A';
      int amount = getAmount(t);

      sendAmt[srcIdx] += amount;
      receiveAmt[destIdx] += amount;
    }

    for (int i = 0; i < 26; i++) {
      if (i == CENTRAL_BANK_ID) {
        continue;
      }

      netBalance[i] = receiveAmt[i] - sendAmt[i];
      if (netBalance[i] > 0) {
        transfersList.add(CENTRAL_BANK + (char) (i + 'A') + netBalance[i]);
      } else if (netBalance[i] < 0) {
        transfersList.add((char) (i + 'A') + CENTRAL_BANK + Math.abs(netBalance[i]));
      }
    }

    return transfersList;
  }

  private int getAmount(String transaction) {
    return Integer.valueOf(transaction.substring(2), 10);
  }

  public static void main(String[] args) {
    Solution obj = new Solution();
    System.out.println();

    System.out.println(obj.generateMinimumTransfers(Arrays.asList("AB1", "BA2")));
    System.out.println(obj.generateMinimumTransfers(Arrays.asList("AB1", "BA2", "BC1")));
    System.out.println(obj.generateMinimumTransfers(Arrays.asList("AB1", "BA2", "BC1", "DA1")));
    System.out.println(obj.generateMinimumTransfers(Arrays.asList("BC1", "CD1")));
    System.out.println(obj.generateMinimumTransfers(Arrays.asList("BC2", "CD1")));
    System.out.println(obj.generateMinimumTransfers(Arrays.asList("BC203", "CD198")));
    System.out.println(obj.generateMinimumTransfers(Arrays.asList("BC2", "CD009")));
  }
}
