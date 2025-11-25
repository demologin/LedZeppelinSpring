package by.it.course.dc.impl.note;

import by.it.course.dc.impl.note.model.NoteRequestTo;
import by.it.course.dc.impl.note.model.NoteResponseTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1.0/notes")
@Data
@AllArgsConstructor
@Valid
public class NoteController {

    private NoteService noteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @AllowFiltering
    public List<NoteResponseTo> findAll() {
        return noteService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteResponseTo findOne(@PathVariable("id") long id) {
        return noteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponseTo create(@RequestBody NoteRequestTo dto, HttpServletRequest request, Locale locale) {
        return noteService.create(dto, locale);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public NoteResponseTo update(@Valid @RequestBody NoteRequestTo dto, HttpServletRequest request) {
        return noteService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        noteService.removeById(id);
    }
}