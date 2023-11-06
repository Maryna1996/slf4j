package org.example;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.out;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            System.out.println("Connected to the server. You can start sending messages.");

            while (true) {
                String message = scanner.nextLine();
                out.println(message);

                if ("exit".equalsIgnoreCase(message)) {
                    System.out.println("Disconnected from the server.");
                    break;
                } else if (message.startsWith("-file")) {
                    sendFile(socket, message);
                }

                String response = in.readLine();
                System.out.println("Server says: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(Socket socket, String message) {
        try {
            String[] parts = message.split(" ");
            if (parts.length != 2) {
                out.println("Invalid file command format. Use: -file [file_path]");
                return;
            }
            String filePath = parts[1];

            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    OutputStream outputStream = socket.getOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                    out.println("File sent: " + filePath);
                }
            } else {
                out.println("File not found: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String message) {
        out.println(message);
        return message;
    }


    public void setOut(PrintWriter mockOut) {
    }
}
