package site.tt_nsk.entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class Pair {
    Player player1;
    Player player2;
}