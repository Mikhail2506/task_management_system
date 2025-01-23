package by.toukachmikhail.taskmanagementsystem.dto;

import by.toukachmikhail.taskmanagementsystem.validators.digits.ValidIsDigit;
import lombok.Builder;

@Builder
public record CommentDto(

    String text
) {
}
