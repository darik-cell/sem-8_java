package com.ssau;

import java.io.*;
import java.net.*;

public class ServerSequential {
  public static void main(String[] args) {
    int port = 12345;

    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("Сервер (последовательная обработка) запущен и слушает порт " + port);
      Socket clientSocket = serverSocket.accept();
      System.out.println("Клиент подключился: " + clientSocket.getInetAddress());

      try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
          if (inputLine.equalsIgnoreCase("exit")) {
            System.out.println("Клиент завершил сессию.");
            break;
          }
          String[] tokens = inputLine.split("\\s+");
          if (tokens.length != 4) {
            out.println("Ошибка: неверный формат данных.");
            continue;
          }
          try {
            double x1 = Double.parseDouble(tokens[0]);
            double y1 = Double.parseDouble(tokens[1]);
            double x2 = Double.parseDouble(tokens[2]);
            double y2 = Double.parseDouble(tokens[3]);

            double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
            if (distance == 0) {
              out.println("Ошибка: расстояние между точками равно 0.");
            } else {
              out.println("Расстояние: " + distance);
            }
          } catch (NumberFormatException e) {
            out.println("Ошибка: неверный формат чисел.");
          }
        }
      }
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
