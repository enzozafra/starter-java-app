package com.zafra.starterapp.handlers.mutualrank;

import java.util.Map;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MutualRankingTest {

  @Test
  public void hasMutualFirstChoice() {
    var wishlist = Map.of(
        'a', new Character[] {'b', 'c', 'd'},
        'b', new Character[] {'a', 'c', 'd'},
        'c', new Character[] {'d', 'a'},
        'd', new Character[] {'a', 'c'}
    );
    var mutualRanking = new MutualRanking(wishlist);

    assertThat(mutualRanking.hasMutualFirstChoice('a')).isTrue();
    assertThat(mutualRanking.hasMutualFirstChoice('b')).isTrue();

    assertThat(mutualRanking.hasMutualFirstChoice('c')).isFalse();
    assertThat(mutualRanking.hasMutualFirstChoice('z')).isFalse();
  }

  @Test
  public void hasMutualRanking() {
    var wishlist = Map.of(
        'a', new Character[] {'b', 'c', 'd'},
        'b', new Character[] {'a', 'c', 'd'},
        'c', new Character[] {'d', 'a'},
        'd', new Character[] {'a', 'c'}
    );
    var mutualRanking = new MutualRanking(wishlist);

    assertThat(mutualRanking.hasMutualRanking('a', 1)).isTrue();
    assertThat(mutualRanking.hasMutualRanking('b', 0)).isTrue();
    assertThat(mutualRanking.hasMutualRanking('c', 1)).isTrue();

    assertThat(mutualRanking.hasMutualRanking('b', 1)).isFalse();
    assertThat(mutualRanking.hasMutualRanking('c', 100)).isFalse();
    assertThat(mutualRanking.hasMutualRanking('z', 0)).isFalse();
  }

  @Test
  public void changePair() {
    var wishlist = Map.of(
        'a', new Character[] {'b', 'c', 'd'},
        'b', new Character[] {'a', 'c', 'd'},
        'c', new Character[] {'d', 'a'},
        'd', new Character[] {'a', 'c'}
    );
    var mutualRanking = new MutualRanking(wishlist);

    assertThat(mutualRanking.changePair('c', 1)).isEqualTo(new Character[] {'a', 'd'});

    wishlist = Map.of(
        'a', new Character[] {'b', 'c', 'd'},
        'b', new Character[] {'a', 'd', 'c'},
        'c', new Character[] {'d', 'a', 'b'},
        'd', new Character[] {'a', 'c'}
    );
    mutualRanking = new MutualRanking(wishlist);

    assertThat(mutualRanking.changePair('c', 1)).isEqualTo(new Character[] {'a', 'b', 'd'});
  }
}
