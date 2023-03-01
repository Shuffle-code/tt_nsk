package site.tt_nsk.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TourStatus {
    UPCOMING("Предстаящий турнир"), ACTIVE("Текущий турнир"), FINISHED("Завершенный турнир"), DELETED("Удалён");
//    ACTIVE("Участник следующего турниров"), DISABLE("Регулярный участник турнира");
    private final String title;
}
