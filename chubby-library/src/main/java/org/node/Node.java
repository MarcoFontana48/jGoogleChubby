package org.node;

import io.etcd.jetcd.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

//TODO
// - ricorda che nel paper c'è scritto che evita permessi con sintassi basati sulla path del nodo: "... avoid
// path-dependent permission semantics (that is, access to a file is controlled by the permissions on the file itself
// rather than on directories on the path leading to the file)..." --> sezione 2.3 pagina 4 in basso a sinistra
// - if NodeType == DIRECTORY then it can be deleted only if it has no children
// - if NodeType == DIRECTORY then it can have children nodes (come li rappresento? provare con jetcd, o controllare i
// lab, oppure 'oop' week09)
// - if NodeProperty == EPHIMERAL then it can be deleted if the client uses the respective function OR it must be deleted
// when no clients have any kind of handle on them, however in any case if NodeType == DIRECTORY then is must have no
// children to be deleted
// - gli handle vanno creati nel momento in cui il client fa OPEN sul nodo, e sono rimossi quando fa DELETE (aggiungere
// un metodo createHandle(...) per creare un handle da questo nodo per il client

//! guarda github chubbyGo file 'BaseServer/fileSystem.go'

public class Node {
    private static final Logger logger = LogManager.getLogger(Node.class);
    private final String nodeName;
    private final NodeProperty nodeProperty;
    private final String absolutePath;
    private LockState lockState;
    private HashMap<Client, Handle> clientHandleHashMap;    //! lista di tutti gli handle aperti su questo nodo (KEY: il client che ha l'handle, VALUE: l'handle) (cos'è l'handle: si collega al client (possono esserci più handle, uno per client), serve per eseguire azioni da/su questo nodo dal client)





    private HashMap<String, List<Node>> --> KEY:pathAssolutaFinoAQui, VALUE:tuttiINodiFigliDiQuestaDirectory (ogni value ha a sua volta una mappa di questo tipo con tutti i nodi figli)





    //! NO --> questo oggetto serve solo per il meta-data, puntatori ai dati che sono salvati nel KV store, il
    //! contenuto del file non è contenuto qui, è accessibile mediante query al KV store
    //! questo oggetto NON va nel KV store, ha solo i puntatori al KV store che contengono il contenuto del file che
    //! rappresenta questo nodo
    //! KEY:path, VALUE:fileContent --- e questi metadati dove li salvo?
    //! MA SE E' COSì COME SALVO I NODI NEL KV STORE? DEVO SALVARE TUTTO, ANCHE I META-DATI --> la soluzione sarebbe
    //! salvare anche il fileContent qui e poi mettere nel KV store KEY:path, VALUE:node (proprio l'oggetto node)
    //private String fileContent; //! accepts only text files and of small dimensions (order of kilobytes) so they can fit in a String






    //this node's ACL IDs
    private final long readAcl;
    private final long writeAcl;
    private final long modifyAcl;

    //------ monotonically increasing numbers (counters that are only increased but never decreased or resetted) -------
    //id of the node in case of same-name nodes, always greater than previous nodes that share the same name, for this
    //reason is the only one of the four that is final
    private final long instanceNumber;

    //increases by one when the node’s lock transitions from free to held (not the opposite)
    private long lockGenerationNumber;

    //increases by one when the node's ACL names are written (TODO: remember to add 'this.aclGenerationNumber++' to increase counter)
    private long aclGenerationNumber;
    // -----------------------------------------------------------------------------------------------------------------

    //checksum
    // TODO: changes each time the file has changed (is written) --> va rigenerato ad ogni write del file (anche se il
    //  write non ha apportato modifiche?)
    // TODO: è usata per controllare che il contenuto del file non sia cambiato, il checksum legge tutto il file e fa
    //  passare il contenuto attraverso una hash function che genera un codice a 'n' bit che può essere utilizzato per
    //  confrontare rapidamente il contenuto del file con quello ad un momento precedente (quello di quando era stato
    //  generato il checksum)
    private long /*or String (?)*/ fileContentChecksum;

    // TODO (ACL): "unless overridden, a node inherits the acl of the parent node" (che valori dovrebbe avere il 'acl' in caso di ovveride? --> valori di default (?))
    public Node(String nodeName, NodeProperty nodeProperty, String absolutePath) {
        this.nodeName = nodeName;   //TODO make sure that the name does not contain any regex character
        this.nodeProperty = nodeProperty;
        this.absolutePath = absolutePath;
        this.lockState = LockState.FREE;
        this.instanceNumber = -1;   //TODO assign a number to this variable. It represents the id of the node in case of same-name nodes, always greater than previous nodes that share the same name
        this.lockGenerationNumber = Long.MIN_VALUE;
        this.aclGenerationNumber = Long.MIN_VALUE;

        logger.debug("created node '" + this.nodeName + "' with 'instanceNumber': " + this.instanceNumber + ", at " + this.absolutePath);
    }

    /**
     * Deletes this node from the KV store.
     * A node is deleted only if it does not contain child nodes of any type (directories or files).
     *
     * @return true if deleted, false otherwise
     */
    public boolean delete(/*controlla checksum del client e del nodo, se combaciano prosegui*/) {

    }

    public NodeProperty getNodeProperty() {
        return nodeProperty;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public String getNodeName() {
        return nodeName;
    }

    public LockState getLockState() {
        return lockState;
    }

    public void setLockState(LockState lockState) {
        logger.debug("invoked method to switch lockState of Node '" + this.nodeName + "' from " + this.lockState + " to " + lockState);

        //if this node switches state from FREE to HELD update the 'lockGenerationNumber' counter
        if (this.lockState.equals(LockState.FREE) && (lockState.equals(LockState.HELD_EXCLUSIVE) || lockState.equals(LockState.HELD_SHARED))) {
            logger.debug("detected request to switch lockState from FREE to HELD, increasing 'lockGenerationNumber' of Node '" + this.nodeName + "' by 1, old value: " + this.lockGenerationNumber);
            this.lockGenerationNumber++;
            logger.debug("increased 'lockGenerationNumber' of Node '" + this.nodeName + "' by 1, new value: " + this.lockGenerationNumber);
        }

        this.lockState = lockState;
        logger.debug("switched lockState of Node '" + this.nodeName + "' to " + lockState);
    }

    public long getInstanceNumber() {
        return instanceNumber;
    }

    public long getLockGenerationNumber() {
        return lockGenerationNumber;
    }

    public long getAclGenerationNumber() {
        return aclGenerationNumber;
    }

    public long getFileContentChecksum() {
        return fileContentChecksum;
    }
}
