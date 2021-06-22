package khachatrian.service;

import khachatrian.model.Textbook;

import java.util.List;

public interface TextbookService {
    List<Textbook> getTextbooks();
    Textbook getTextbookByName(String name);
    Textbook getTextbookById(Long id);

    void saveTextbook(Textbook textbook);
    void updateTextbook(Long id, Textbook textbook);
    void deleteTextbookById(Long id);

    List<Textbook> findTextbooksBySize(String s);
    List<Textbook> findTextbooksByColor(String c);
    List<Textbook> findTextbooksBySubject(String sub);
    List<Textbook> findTextbooksByMaterial(String m);
}

