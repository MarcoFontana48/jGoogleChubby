package org.node;

import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import java.util.concurrent.CompletableFuture;

//! controllare le classi: AUTH, CLIENT, LOCK, LEASE, CLUSTER, WATCH; dentro a "io.etcd.jetcd"

public class FileSystem {
    // Initialize client - http://localhost:2379 is where the kv store is hosted
    Client client = Client.builder().endpoints("http://localhost:2379").build();

    // Get KV client
    KV kvClient = client.getKVClient();

    // Define key and value
    ByteSequence key = ByteSequence.from("test_key".getBytes());
    ByteSequence value = ByteSequence.from("test_value".getBytes());

    // Put operation
    CompletableFuture<PutResponse> putFuture = kvClient.put(key, value);
    putFuture.get();  // You might want to add necessary exception handling here

    // Get operation
    CompletableFuture<GetResponse> getFuture = kvClient.get(key);
    GetResponse response = getFuture.get();  // You might want to add necessary exception handling here

    // Close the client
    client.close();
}
