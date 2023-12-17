package org.node;

public enum LockState {
    //FREE == not held by any client
    //HELD == held by a client in any mode (EXCLUSIVE == write, SHARED == read)
    FREE, HELD_EXCLUSIVE, HELD_SHARED;
}
