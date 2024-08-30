package com.julio.whorkshop.DTO;

import java.util.Date;

public record CommentDto(String text, Date date, AuthorDTO author) {
}
