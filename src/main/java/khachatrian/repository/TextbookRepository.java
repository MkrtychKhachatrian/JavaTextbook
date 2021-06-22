package khachatrian.repository;

import khachatrian.model.Textbook;
import org.springframework.data.jpa.repository.JpaRepository;
import khachatrian.model.enums.Subject;
import khachatrian.model.enums.Color;
import khachatrian.model.enums.Size;
import khachatrian.model.enums.Material;

import java.util.List;
import java.util.Optional;

public interface TextbookRepository extends JpaRepository<Textbook, Long> {

     Optional<Textbook> findByName (String name);

     List<Textbook> findAllBySize(Size s);
     List<Textbook> findAllByColor(Color c);
     List<Textbook> findAllBySubject(Subject s);
     List<Textbook> findAllByMaterial(Material m);
}

