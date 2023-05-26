package rikkei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rikkei.model.entity.Song;
import rikkei.serviceImpl.SongServiceIpm;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller

public class SongController {

    private SongServiceIpm songServiceIpm = new SongServiceIpm();
    @GetMapping("/listSong")
    public String findAll(Model model){
        List<Song> songs = songServiceIpm.findALl();
        model.addAttribute("list",songs);
        return "listSong";
    }@GetMapping("formCreateSong")
    public String formCreateSong(Model model){
        Song song = new Song();
        model.addAttribute("song",song);
        return "createSong";
    }

    @PostMapping ("/createSong")
    public String formAdd(Model model,@RequestParam("files") MultipartFile files,@ModelAttribute("song") Song song) throws IOException {
        String uploadPath = "C:\\Users\\Admin\\Desktop\\rikkei_data\\MySQL\\module5\\Customer-Manage-ORM\\src\\main\\resources\\assets\\files\\";
        File file = new File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = files.getOriginalFilename();
        FileCopyUtils.copy(files.getBytes(), new File(uploadPath + File.separator + fileName));
        model.addAttribute("fileName", fileName);
        Song songC = new Song(song.getSongName(),song.getAuthor(),song.getCatalog(),fileName);
        songServiceIpm.save(songC);
        return "redirect:listSong";
    }
}
