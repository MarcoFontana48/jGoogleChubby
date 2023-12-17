package org.node;

public class FileNode extends Node {
    // monotonically increasing number (counters that are only increased but never decreased or resetted)
    private long contentGenerationNumber;   //increases by one each time the file's contents are written (TODO: if changes are detected or either if nothing has changed? - remember to add 'this.contentGenerationNumber++' to increase counter))

    public FileNode(String nodeName, NodeProperty nodeProperty, String absolutePath) {
        super(nodeName, nodeProperty, absolutePath);
        this.contentGenerationNumber = Long.MIN_VALUE;
    }

    public long getContentGenerationNumber() {
        return contentGenerationNumber;
    }

    public void setContentGenerationNumber(long contentGenerationNumber) {
        this.contentGenerationNumber = contentGenerationNumber;
    }
}
