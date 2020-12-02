package br.com.itconsulting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itconsulting.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
