package com.ssau;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
  public static void main(String[] args) {
    String host = "localhost";
    int port = 12345;

    try (Socket socket = new Socket(host, port);
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
         Scanner scanner = new Scanner(System.in)) {

      System.out.println("Клиент подключился к серверу " + host + " на порту " + port);

      while (true) {
        System.out.println("Введите координаты двух точек (x1 y1 x2 y2) или 'exit' для завершения:");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
          out.println("exit");
          break;
        }
        out.println(input);
        String response = in.readLine();
        if (response == null) {
          System.out.println("Сервер завершил соединение.");
          break;
        }
        System.out.println("Ответ сервера: " + response);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
