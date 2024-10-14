package com.practice.filmorate.storage;


public interface LikeStorage {
    void addLike(int filmId, int userId);
    void removeLike(int filmId, int userId);
}
