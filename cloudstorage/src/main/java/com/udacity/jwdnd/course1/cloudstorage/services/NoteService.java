package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int saveNote(Note note){
        return noteMapper.saveNote(note);
    }

    public List<Note> getNoteListbyUser(Integer userid){
        return noteMapper.getNoteList(userid);
    }

    public void deleteNote(Integer noteId) { noteMapper.deleteNote(noteId); }

    public int updateNote(Note note) { return noteMapper.updateNote(note); }


}
