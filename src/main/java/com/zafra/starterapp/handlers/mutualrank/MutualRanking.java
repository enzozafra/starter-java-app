package com.zafra.starterapp.handlers.mutualrank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MutualRanking {
  private Map<Character, Character[]> wishlist;

  public MutualRanking(Map<Character, Character[]> wishlist) {
    this.wishlist = wishlist;
  }

  public boolean hasMutualFirstChoice(char username) {
    return hasMutualRanking(username, 0);
  }

  //
  public boolean hasMutualRanking(char username, int rank) {
    Character choice = getUsersChoiceAtRanking(username, rank);
    return choice != null && getUsersChoiceAtRanking(choice, rank).equals(username);
    // return hasMutualRanking(username, rank, rank);
  }

  public boolean hasMutualRanking(char username, int rank, int otherRank) {
    Character choice = getUsersChoiceAtRanking(username, rank);
    return choice != null && getUsersChoiceAtRanking(choice, otherRank).equals(username);
  }

  public Character[] changePair(char user, int index) {
    if (!this.wishlist.containsKey(user) && index >= this.wishlist.get(user).length) {
      return new Character[0];
    }

    List<Character> results = new ArrayList<>();
    Character[] currentRankings = this.wishlist.get(user);

    for (int i = 0; i < currentRankings.length; i++) {
      if (hasMutualRanking(user, i)) {
        results.add(currentRankings[i]);
      }
    }

    if (index > 0) {
      // simulate the swap by checking the other index
      if (hasMutualRanking(user, index, index - 1)) {
        results.add(currentRankings[index]);
      }
      // test the other side of the swap (index - 1)
      if (hasMutualRanking(user, index - 1, index)) {
        results.add(currentRankings[index - 1]);
      }
    }

    return results.toArray(new Character[0]);
  }

  public Character[] changed_pairings(char user, int index) {
    if (!this.wishlist.containsKey(user) && index >= this.wishlist.get(user).length) {
      return new Character[0];
    }

    List<Character> results = new ArrayList<>();
    Character[] currentRankings = this.wishlist.get(user);

    if (index > 0) {
      // mutual changed after swapping
      if (hasMutualRanking(user, index) != hasMutualRanking(user, index, index - 1)) {
        results.add(currentRankings[index]);
      }
      // Check the other swapped position
      if (hasMutualRanking(user, index - 1) != hasMutualRanking(user, index - 1, index)) {
        results.add(currentRankings[index - 1]);
      }
    }

    return results.toArray(new Character[0]);
  }

  private Character getUsersChoiceAtRanking(char user, int rank) {
    if (rank < 0 || !hasRankings(user) || rank >= this.wishlist.get(user).length) {
      return null;
    }

    return this.wishlist.get(user)[rank];
  }

  private boolean hasRankings(char username) {
    return this.wishlist.containsKey(username) && this.wishlist.get(username).length != 0;
  }
}
