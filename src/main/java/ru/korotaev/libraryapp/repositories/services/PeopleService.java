package ru.korotaev.libraryapp.repositories.services;

import jakarta.persistence.NoResultException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.repositories.PeopleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<User> findAll(){
        return peopleRepository.findAll();
    }

    public User findOne(int id){
        Optional<User> foundPerson =  peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(User person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, User updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public User validateEmail(String email) {
        List<User> peopleWithEmail = peopleRepository.findByEmail(email);
        return  peopleWithEmail.size()==0?null:peopleWithEmail.get(0);
    }

    public User validateName(String name) {
        List<User> peopleWithName = peopleRepository.findByName(name);
        return  peopleWithName.size()==0?null:peopleWithName.get(0);
    }

    public List<Book> getAllBooksByUser(int userId) {
        User user = peopleRepository.findById(userId).orElse(null);
        if (user != null) {
            Hibernate.initialize(user.getBooks());
            return user.getBooks();
        }
        return Collections.emptyList();
    }

}