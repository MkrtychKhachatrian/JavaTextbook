package khachatrian.service.implementation;

import khachatrian.model.Textbook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import khachatrian.exception.ObjectNotFoundException;
import khachatrian.model.enums.Subject;
import khachatrian.model.enums.Color;
import khachatrian.model.enums.Size;
import khachatrian.model.enums.Material;
import khachatrian.repository.TextbookRepository;
import khachatrian.service.TextbookService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TextbookServiceImpl implements TextbookService {

    private final TextbookRepository textbookRepository;

    public List<Textbook> getTextbooks() {
        return textbookRepository.findAll();
    }

    public Textbook getTextbookById(Long id) {
        return textbookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Textbook.class.getName(), id));
    }
    
    public Textbook getTextbookByName(String name) {
        return textbookRepository.findByName(name).orElseThrow(() -> new ObjectNotFoundException(Textbook.class.getName(), name));
    }

    public List<Textbook> findTextbooksBySize(String s) {
        return  textbookRepository.findAllBySize(Size.getTypeByUrl(s));
    }
    public List<Textbook> findTextbooksByColor(String c) {
        return  textbookRepository.findAllByColor(Color.getTypeByUrl(c));
    }
    public List<Textbook> findTextbooksBySubject(String sub) {
        return  textbookRepository.findAllBySubject(Subject.getTypeByUrl(sub));
    }
    public List<Textbook> findTextbooksByMaterial(String m) {
        return  textbookRepository.findAllByMaterial(Material.getTypeByUrl(m));
    }

    public void saveTextbook(Textbook textbook) {
        textbookRepository.save(textbook);
    }
    public void updateTextbook(Long id, Textbook textbook) {
        if(!textbookRepository.findById(id).isPresent())
            throw new ObjectNotFoundException(Textbook.class.getName(),id);
        textbook.setId(id);
        textbookRepository.save(textbook);
    }
    public void deleteTextbookById(Long id) {
        if(!textbookRepository.findById(id).isPresent())
            throw new ObjectNotFoundException(Textbook.class.getName(),id);
        textbookRepository.deleteById(id);
    }


}

