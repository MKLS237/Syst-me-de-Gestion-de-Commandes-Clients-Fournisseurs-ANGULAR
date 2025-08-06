package com.client.commandes.services;

import com.client.commandes.models.Fournisseur;
import com.client.commandes.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FournisseurServiceImpl implements FournisseurService {

    @Autowired
    private FournisseurRepository repository;

    @Override
    public Fournisseur save(Fournisseur fournisseur) {
        return repository.save(fournisseur);
    }

    @Override
    public List<Fournisseur> getAll() {
        return repository.findAll();
    }

    @Override
    public Fournisseur getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Fournisseur update(Long id, Fournisseur f) {
        Fournisseur existant = getById(id);
        if (existant != null) {
            existant.setNom(f.getNom());
            existant.setAdresse(f.getAdresse());
            existant.setTelephone(f.getTelephone());
            existant.setEmail(f.getEmail());
            existant.setEntreprise(f.getEntreprise());
            return repository.save(existant);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
