package rikkei.service;

import rikkei.model.entity.Song;

import java.util.List;

public interface ISongService {
    List<Song> findALl();
    void save(Song song);
    void delete(int id);
   Song findById(int id);
}
