package io.daos.dfs;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class DaosFsClientIT {

  private static String poolId;
  private static String contId;

  @BeforeClass
  public static void setup() throws Exception {
    poolId = System.getProperty("pool_id", DaosFsClientTestBase.DEFAULT_POOL_ID);
    contId = System.getProperty("cont_id", DaosFsClientTestBase.DEFAULT_CONT_ID);
  }

  @Test
  public void testCreateFsClientFromSpecifiedContainer() throws Exception {
    DaosFsClient.DaosFsClientBuilder builder = new DaosFsClient.DaosFsClientBuilder();
    builder.poolId(poolId).containerId(contId);
    DaosFsClient client = null;
    try {
      client = builder.build();
      Assert.assertTrue(client != null);
<<<<<<< HEAD
    } finally {
      if (client != null) {
        client.disconnect();
=======
    }finally {
      if(client != null){
        client.close();
>>>>>>> refactored DaosFsClient and its native to make DAOS pool/container/init/finalize common to both FS and Object APIs
      }
    }
  }

  @Test
  public void testCreateFsClientFromRootContainer() throws Exception {
    DaosFsClient.DaosFsClientBuilder builder = new DaosFsClient.DaosFsClientBuilder();
    builder.poolId(poolId);
    DaosFsClient client = null;
    try {
      client = builder.build();
      Assert.assertTrue(client != null);
<<<<<<< HEAD
    } finally {
      if (client != null) {
        client.disconnect();
=======
    }finally {
      if(client != null){
        client.close();
>>>>>>> refactored DaosFsClient and its native to make DAOS pool/container/init/finalize common to both FS and Object APIs
      }
    }
  }

  @Test
  public void testFsClientCachePerPoolAndContainer() throws Exception {
    DaosFsClient.DaosFsClientBuilder builder = new DaosFsClient.DaosFsClientBuilder();
    builder.poolId(poolId).containerId(contId);
    DaosFsClient client = null;
    DaosFsClient client2[] = new DaosFsClient[1];
    try {
      client = builder.build();
      Thread thread = new Thread() {
        @Override
        public void run() {
          try {
            DaosFsClient.DaosFsClientBuilder builder = new DaosFsClient.DaosFsClientBuilder();
            builder.poolId(poolId).containerId(contId);
            client2[0] = builder.build();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      };
      thread.start();
      thread.join();
      Assert.assertEquals(client, client2[0]);
    } finally {
      client.close();
    }
  }

  @Test
  public void testDeleteSuccessful() throws Exception {
    DaosFsClient.DaosFsClientBuilder builder = new DaosFsClient.DaosFsClientBuilder();
    builder.poolId(poolId).containerId(contId);
    DaosFsClient client = null;
    try {
      client = builder.build();
      Assert.assertTrue(client != null);
      client.delete("/ddddddd/zyx", true);
<<<<<<< HEAD
    } finally {
      if (client != null) {
        client.disconnect();
=======
    }finally {
      if(client != null){
        client.close();
>>>>>>> refactored DaosFsClient and its native to make DAOS pool/container/init/finalize common to both FS and Object APIs
      }
    }
  }

  @Test
  public void testMultipleMkdirs() throws Exception {
    DaosFsClient.DaosFsClientBuilder builder = new DaosFsClient.DaosFsClientBuilder();
    builder.poolId(poolId).containerId(contId);
    DaosFsClient client = null;
    try {
      client = builder.build();
      Assert.assertTrue(client != null);
      client.mkdir("/mkdirs/1", true);
      client.mkdir("/mkdirs/1", true);
<<<<<<< HEAD
    } finally {
      if (client != null) {
        client.disconnect();
=======
    }finally {
      if(client != null){
        client.close();
>>>>>>> refactored DaosFsClient and its native to make DAOS pool/container/init/finalize common to both FS and Object APIs
      }
    }
  }

  @Test(expected = IOException.class)
  public void testMultipleMkdir() throws Exception {
    DaosFsClient.DaosFsClientBuilder builder = new DaosFsClient.DaosFsClientBuilder();
    builder.poolId(poolId).containerId(contId);
    DaosFsClient client = null;
    try {
      client = builder.build();
      Assert.assertTrue(client != null);
      client.mkdir("/mkdir/1", false);
      client.mkdir("/mkdir/1", false);
<<<<<<< HEAD
    } finally {
      if (client != null) {
        client.disconnect();
=======
    }finally {
      if(client != null){
        client.close();
>>>>>>> refactored DaosFsClient and its native to make DAOS pool/container/init/finalize common to both FS and Object APIs
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveWithOpenDirsIllegalSrcName() throws Exception {
    DaosFsClient.DaosFsClientBuilder builder = new DaosFsClient.DaosFsClientBuilder();
    builder.poolId(poolId).containerId(contId);
    DaosFsClient client = null;
    try {
      String fileName = "srcFile/zb";
      client = builder.build();
      client.move(0, fileName, 0, "destFile");
<<<<<<< HEAD
    } finally {
      if (client != null) {
        client.disconnect();
=======
    }finally {
      if(client != null){
        client.close();
>>>>>>> refactored DaosFsClient and its native to make DAOS pool/container/init/finalize common to both FS and Object APIs
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveWithOpenDirsIllegalDestName() throws Exception {
    DaosFsClient.DaosFsClientBuilder builder = new DaosFsClient.DaosFsClientBuilder();
    builder.poolId(poolId).containerId(contId);
    DaosFsClient client = null;
    try {
      String fileName = "srcFile";
      client = builder.build();
      client.move(0, fileName, 0, "/destFile");
<<<<<<< HEAD
    } finally {
      if (client != null) {
        client.disconnect();
=======
    }finally {
      if(client != null){
        client.close();
>>>>>>> refactored DaosFsClient and its native to make DAOS pool/container/init/finalize common to both FS and Object APIs
      }
    }
  }

  @Test
  public void testMoveWithOpenDirs() throws Exception {
    DaosFsClient.DaosFsClientBuilder builder = new DaosFsClient.DaosFsClientBuilder();
    builder.poolId(poolId).containerId(contId);
    DaosFsClient client = null;
    try {
      String fileName = "srcFile";
      client = builder.build();
      DaosFile srcDir = client.getFile("/mdir1");
      srcDir.mkdirs();
      DaosFile srcFile = client.getFile(srcDir, fileName);
      srcFile.createNewFile();

      DaosFile destDir = client.getFile("/mdir2");
      destDir.mkdirs();
      String destFileName = "destFile";
      client.move(srcDir.getObjId(), fileName, destDir.getObjId(), destFileName);
      Assert.assertFalse(srcFile.exists());
      Assert.assertTrue(client.getFile(destDir, destFileName).exists());
<<<<<<< HEAD
    } finally {
      if (client != null) {
        client.disconnect();
=======
    }finally {
      if(client != null){
        client.close();
>>>>>>> refactored DaosFsClient and its native to make DAOS pool/container/init/finalize common to both FS and Object APIs
      }
    }
  }

  @Test
  public void testFsClientReferenceOne() throws Exception {
    DaosFsClient.DaosFsClientBuilder builder = new DaosFsClient.DaosFsClientBuilder();
    builder.poolId(poolId).containerId(contId);
    DaosFsClient client = null;
    try {
      client = builder.build();
      Assert.assertEquals(1, client.getRefCnt());
<<<<<<< HEAD
    } finally {
      if (client != null) {
        client.disconnect();
=======
    }finally {
      if(client != null){
        client.close();
>>>>>>> refactored DaosFsClient and its native to make DAOS pool/container/init/finalize common to both FS and Object APIs
        Assert.assertEquals(0, client.getRefCnt());
      }
    }
    Exception ee = null;
    try {
      client.incrementRef();
    } catch (Exception e) {
      ee = e;
    }
    Assert.assertTrue(ee instanceof IllegalStateException);
  }

  @Test
  public void testFsClientReferenceMore() throws Exception {
    DaosFsClient.DaosFsClientBuilder builder = new DaosFsClient.DaosFsClientBuilder();
    builder.poolId(poolId).containerId(contId);
    DaosFsClient client = null;
    int cnt = 0;
    try {
      client = builder.build();
      cnt = client.getRefCnt();
      builder.build();
      Assert.assertEquals(cnt + 1, client.getRefCnt());
      client.close();
      client.incrementRef();
      Assert.assertEquals(cnt + 1, client.getRefCnt());
      client.decrementRef();
    } catch (Exception e) {
      e.printStackTrace();
<<<<<<< HEAD
    } finally {
      if (client != null) {
        client.disconnect();
=======
    }finally {
      if(client != null){
        client.close();
>>>>>>> refactored DaosFsClient and its native to make DAOS pool/container/init/finalize common to both FS and Object APIs
        Assert.assertEquals(cnt - 1, client.getRefCnt());
      }
    }
  }
}
