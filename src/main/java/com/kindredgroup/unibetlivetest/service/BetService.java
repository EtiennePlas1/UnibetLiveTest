package com.kindredgroup.unibetlivetest.service;

import static java.lang.String.format;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.kindredgroup.unibetlivetest.entity.Bet;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.repository.BetRepository;
import com.kindredgroup.unibetlivetest.types.BetState;
import com.kindredgroup.unibetlivetest.types.ExceptionType;
import com.kindredgroup.unibetlivetest.types.SelectionResult;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import com.kindredgroup.unibetlivetest.utils.Helpers;
import com.kindredgroup.unibetlivetest.v1.model.BetRequest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BetService {

    @Resource
    private BetRepository betRepository;
    
    @Resource
    private SelectionService selectionService;
    
    @Resource
    private CustomerService customerService;
    
    /** 
     * 1. Récupère la séléction et le client associés au pari pris 
     * 
     * 2. Vérifie la cote et si la sélection est ouverte, 
     * 	  puis si le client a les fonds suffisant et s'il n'a pas déjà parié sur cette sélection     * 
     * 
     * 3. Insère le client et le nouveau pari en base
     * 
     * @throws CustomException 
     */
    public void buildBet(BetRequest betRequest) {
        Selection selection = selectionService.findById(betRequest.getSelectionId());        

        if(selection.getState() == SelectionState.CLOSED) {
            throw new CustomException(format("la selection %s est fermée", selection.getName()), ExceptionType.SELECTION_FERMEE);
        }
        
        if(selection.getCurrentOdd().compareTo(betRequest.getCote()) != 0) {
            throw new CustomException(format("la cote de la selection %s a changé", selection.getName()), ExceptionType.CHANGEMENT_DE_COTE);
        }
        
        Customer customer = customerService.findCustomerByPseudo("unibest");

        if(getBetsByCustomerId(customer.getId()).stream().anyMatch(b -> b.getSelection().getId().equals(selection.getId()))) {
            throw new CustomException(format("Pari déjà en cours pour le client %s", customer.getId()), ExceptionType.PARI_DEJA_EN_COURS);
        }        

        
        if(customer.getBalance().compareTo(betRequest.getMise()) == -1) {
            throw new CustomException(format("le client %s n'a pas assez de fonds", customer.getId()), ExceptionType.BALANCE_INSUFFISANTE);
        }
        
        insertBet(betRequest, selection, customer);
        log.info("pari de {} par {} sur {} enregistré avec succès", betRequest.getMise(), customer.getPseudo(), selection.getName());
        
    }
    
    //Insertion du client et du pari en base sous forme  de transaction pour rollback en cas de bug.
    @Transactional
    private void insertBet(BetRequest betRequest, Selection selection, Customer customer) {        
        customer.setBalance(customer.getBalance().subtract(betRequest.getMise()));
        customer.setBets(new ArrayList<>());
        customer.getBets().add(saveBet(new Bet().setDate(new Date()).setSelection(selection).setMise(betRequest.getMise()).setCustomer(customer)));
        customerService.saveCustomer(customer); 
    }
    
    /** 
     * 1. Récupération des paris non résolus d'une sélection
     * 
     * 2. Assocation par paire client-pari
     * 
     * 3. Paiement des clients en fonction du résultat de la sélection
     * 
     * 4. Sauvegarde de la paire client-pari en cas de victoire
     * 	  ou uniquement du pari en cas de défaite
     */    
    public int payBets(Selection selection) {
        List<Bet> unpaidBets = betRepository.getBySelectionIdAndBetStateNull(selection.getId());
        
        Map<Customer,Bet> customerIdToBet = unpaidBets.stream().collect(Collectors.toMap(Bet::getCustomer, b->b));
        
        customerIdToBet.forEach((c,b) -> {
            if(selection.getResult() == SelectionResult.WON) {
                payBet(selection, c, b);
                
                try {
                    saveBetAndCustomer(c, b);
                } catch (Exception e) {
                    log.error("Erreur lors de l'update en DB des paris du client {} : {}",c.getId(), e.getMessage());
                }
                
            } else {
                b.setBetState(BetState.LOST);
                log.info("Défaite pour {} sur la sélection {}", c.getPseudo(), selection.getName());
                
                try {
                    saveBet(b);
                } catch (Exception e) {
                    log.error("Erreur lors de l'update en DB des paris du client {} : {}",c.getId(), e.getMessage());
                }
            }
            
            
        });
        
        return unpaidBets.size();
        
    }
    
    private void payBet(Selection selection, Customer customer, Bet bet) {
        customer.setBalance(customer.getBalance().add(bet.getMise().multiply(selection.getCurrentOdd())));//Multiplication de la mise par la cote avant la sauvegarde en base
        bet.setBetState(BetState.WON);
        log.info("Victoire pour {} sur la séléction {}, montant gagné : {}", customer.getPseudo(), selection.getName(), bet.getMise().multiply(selection.getCurrentOdd()));
    }   
    
    @Transactional
    private void saveBetAndCustomer(Customer customer, Bet bet) {
        customerService.saveCustomer(customer);
        betRepository.save(bet);        
    }

    public Bet saveBet(Bet bet) {
        betRepository.save(bet);
        return bet;
    }

    public List<Bet> getBetsByCustomerId(Long customerId){
        return betRepository.getByCustomer_idEquals(customerId);
    }
    
    
    /** 
     * 1. Récupération de toutes les sélections et du client
     * 
     * 2. choix d'une sélection au hasard
     * 
     * 3. création d'une requete de Pari avec des paramètre 
     *    aléatoires
     * 
     * 4. lancement du process d'enregistrement du pari 
     *    comme pour une requête via API
     * 
     *  L'objectif de cette fonction est de simuler un pari 
     *  d'un utilisateur avec des paramètres aléatoirement 
     *  valides ou non  
     */      
    public void betRandomly() {
        List<Selection> selections = selectionService.findAll();
        Customer customer = customerService.findCustomerByPseudo("unibest");//inclure les selections fermées permet de tester l'exception SELECTION_FERMEE
        Selection s = selections.get(Helpers.getRandomIndex(0, selections.size()));
        
        BetRequest b = new BetRequest();
        b.setSelectionId(Helpers.getRandomIndex(0, 5) > 0 ? s.getId() : 3000);//test SELECTION_NOT_FOUND
        b.setMise(Helpers.getRandomIndex(0, 5) > 0 ? customer.getBalance().divide(new BigDecimal(3),2,RoundingMode.HALF_UP) : new BigDecimal(51));//test BALANCE_INSUFFISANTE
        b.setCote(Helpers.getRandomIndex(0, 5) > 0 ? s.getCurrentOdd() : new BigDecimal(0));////test CHANGEMENT_DE_COTE
        
        buildBet(b);
    }
}
