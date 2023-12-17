package org.node;

import io.etcd.jetcd.Client;

//! handle è l'interfaccia con cui il client esegue azioni dal/sul nodo, chubby di per se può eseguire azioni sui nodi
//! come gli pare e non deve usare HANDLES (sono solo per uso del client)
//? L'handle serve per eseguire azioni sui nodi (ne viene creato uno ogni volta che si richiede lettura o scrittura di
//? un nodo) legge gli input dal client ed esegue le azioni dal nodo di directory in cui è posizionato:
//? è come su UNIX che non devi sempre inserire da dove vuoi effettuare le azioni ma ogni volta che usi i comandi esegue
//? azioni ma tiene in memoria in quale directory è posizionato il client e lancia in automatico i comandi da quel
//? handle per la directory che rappresenta, in questo modo permette di effettuare azioni su essa e lo distrugge quando
//? passa alla directory figlia.
//? di base i client ottengono un handle su "/" che è il nodo di root, in modo da poter eseguire comandi da quello da
//? subito
//? i comandi hanno lo stesso nome di quelli UNIX (deciso io) e lanciano i metodi di questa classe per eseguirli
//? esempio: ho un handle in "/" e voglio eseguire il comando "ls" (COSA VEDE IL CLIENT: "chubby: /> ls")
//?          cosa esegue questa classe --> quando in ingresso arriva un segnale 'ls' dal client esegue il metodo:
//?          readDir() { ... } --> che fa una query a KV store di jetcd e restituisce tutte le key (path) e value (nodi)
//?          figli di questo nodo e mette in output (stampa a video) tutte le path e i meta-dati dei nodi:
//?          esempio di output:
//?                             cell/dir  [instanceNumber, lockGenerationNumber, aclGenerationNumber, contentGenerationNumber]
//?                             cell/dir  [49151, 138591, 953195, 89135]
//?                             cell/dir  [inst_n:49151, lock_gen_n:138591, acl_gen_n:953195, cont_gen_n:89135]
//?
//?                             cell/dir  [49151, 138591, 953195, 89135]
//?                             cell/abc  [7156, 6498, 31431, 8648]
//?                             cell/def  [341351, 64646, 462642, 35235]
//?
//?                             (perchè sono contenuti in /ls/cell/*)
//TODO: (non è un TODO vero, solo un reminder): gli handle sono forniti nel momento in cui il client fa OPEN sul nodo
public class Handle {
    //! every client has a handle on the directory node they currently are in to execute command from that directory
    //! node
    private final Client client;  //client that has this Handle
    private final HandleType handleType;
    private final Node node;
    private final int checkDigit;
    private final int sequenceNumber;

    //TODO mettere qui un lettore di input dal client che in base all'input fornito esegue le azioni da questa
    // nodeDirectory che sono implementate sotto (open, delete, readDir, ...)

    public boolean open(/*nodeName*/) {
        //! QUANDO DICE NEL PAPER 'HANDLES ARE CREATED BY OPEN' INTENDE L'HANDLE CHE E' GENERATO DALL'HANDLE PADRE PER
        //! POTER PASSARE ALLA DIRECTORY_NODE FIGLIA!
        //! OPEN è l'unica funzione che richiede una PATH! (vd. sezione 2.6 API)
        //TODO --> OPENS THE HANDLE OF THE DIRECTORY_NODE'S CHILD OF THE DIRECTORY_NODE WHERE THIS HANDLE IS IN (see section 2.6 API)
        //restituisce true se p andata a buon fine, false altrimenti
    }

    public boolean close() {
        //TODO elimina questo handle (associazione tra il client e la directory dove è questo handle) per passare a
        // quella padre (vd. funzione open)
        //restituisce true se p andata a buon fine, false altrimenti
    }

    public readDir() {

    }

    public delete() {
        this.node.delete();
    }
}
