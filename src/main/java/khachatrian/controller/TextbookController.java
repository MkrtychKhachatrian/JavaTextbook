package khachatrian.controller;

import khachatrian.model.Textbook;
import khachatrian.util.TextbookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import khachatrian.service.TextbookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/textbook")
public class TextbookController {


    private final TextbookService textbookService;


    @GetMapping("/")
    public List<Textbook> allTextbooks() {
        return textbookService.getTextbooks();
    }

    @GetMapping("name/{name}")
    public Textbook findTextbookByName(@PathVariable String name) throws TextbookNotFoundException {
            return textbookService.getTextbookByName(name);
    }

    @GetMapping("{id}")
    public Textbook findTextbookById(@PathVariable Long id) {
        return textbookService.getTextbookById(id);
    }

    @GetMapping("size/{s}")
    public List<Textbook> findTextbooksBySize(@PathVariable String s) {
        return textbookService.findTextbooksBySize(s);
    }

    @GetMapping("color/{c}")
    public List<Textbook> findTextbooksByColor(@PathVariable String c) {
        return textbookService.findTextbooksByColor(c);
    }

    @GetMapping("subject/{sub}")
    public List<Textbook> findTextbooksBySubject(@PathVariable String sub) {
        return textbookService.findTextbooksBySubject(sub);
    }

    @GetMapping("material/{m}")
    public List<Textbook> findLooksByMaterial(@PathVariable String m) {
        return textbookService.findTextbooksByMaterial(m);
    }

    @PostMapping
    public List<Textbook> saveTextbook(@RequestBody Textbook textbook) {
        textbookService.saveTextbook(textbook);
        return textbookService.getTextbooks();
    }

    @PutMapping("{id}")
    public List<Textbook> updateTextbook(@PathVariable Long id, @RequestBody Textbook textbook) {
        textbookService.updateTextbook(id, textbook);
        return textbookService.getTextbooks();
    }

    @DeleteMapping("{id}")
    public void deleteTextbook(@PathVariable Long id) {
        textbookService.deleteTextbookById(id);
    }
}

