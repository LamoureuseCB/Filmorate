package com.practice.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum Friendship {
    CONFIRMED("дружба подтверждена"),
    UNCONFIRMED("дружба не подтверждена");
    private String statusDescription;

}
