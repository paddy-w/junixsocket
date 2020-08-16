/**
 * junixsocket
 *
 * Copyright 2009-2020 Christian Kohlschütter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.newsclub.net.mysql;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import com.mysql.jdbc.SocketFactory;

/**
 * Connect to mysql databases (and compatibles) using UNIX domain sockets.
 * 
 * NOTE: This SocketFactory currently implements the "old" Connector/J SocketFactory. This may
 * change in the future.
 * 
 * For the time being, see AFUNIXDatabaseSocketFactoryCJ to forcibly use the new "CJ"-style
 * SocketFactory.
 * 
 * @see AFUNIXDatabaseSocketFactoryCJ
 */
@SuppressWarnings("deprecation")
public class AFUNIXDatabaseSocketFactory implements SocketFactory {
  private Socket socket = null;

  public AFUNIXDatabaseSocketFactory() {
  }

  @Override
  public Socket afterHandshake() throws SocketException, IOException {
    return socket;
  }

  @Override
  public Socket beforeHandshake() throws SocketException, IOException {
    return socket;
  }

  @Override
  public Socket connect(String host, int portNumber, Properties props) throws SocketException,
      IOException {
    // Adjust the path to your MySQL socket by setting the
    // "junixsocket.file" property
    // If no socket path is given, use the default: /tmp/mysql.sock
    final File socketFile = new File(props.getProperty("junixsocket.file", "/tmp/mysql.sock"));

    socket = AFUNIXSocket.connectTo(new AFUNIXSocketAddress(socketFile));
    return socket;
  }
}
