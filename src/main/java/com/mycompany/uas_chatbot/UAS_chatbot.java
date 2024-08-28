/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.uas_chatbot;

/**
 *
 * #
 */
import java.awt.List;
import java.awt.SystemColor;
import static java.awt.SystemColor.text;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.*;
import java.util.Date;
import javax.swing.JOptionPane;

public class UAS_chatbot extends TelegramLongPollingBot{
     private static final String DB_URL = "jdbc:mysql://localhost:3306/chats_bot";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) throws TelegramApiException{
         UAS_chatbot bot = new UAS_chatbot();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long ChatId = update.getMessage().getChatId();

            if (messageText.startsWith("/Daftar")) {
                    // Menangani perintah /register
                    String[] parts = messageText.split(" ");
                    if (parts.length == 2) {
                        String username = parts[1];
                        String RegisterUser = registerUser(ChatId, username);
                        sendMessage(ChatId, RegisterUser);
                       
                    }
                     
                    else {
                     //   sendMessage(ChatId, "Perintah Tidak ada . mohon daftar ulang /Daftar username.");
                    }
            }
//         if (messageText.startsWith("/update")){ 
//                     String[] parts = messageText.split(" ");
//                       if (parts.length == 3) {
//                        String username = parts[2];
//                        String UpdateUser = updateUser(ChatId, username);
//                        sendMessage(ChatId, UpdateUser);
//                }else {
//                        sendMessage(ChatId, "Invalid command format. Please use /update username.");
//                    }
//                }
            if (isAuthorizedUser(ChatId)) {
                // Pengguna telah terotorisasi
                if (messageText.startsWith("/mulai")) {
                // Memulai percakapan dengan pengguna
                startConversation(ChatId);
                } else if (messageText.startsWith("/hallo")) {
                    sapa(ChatId, "Halo aku Fadil Bot  " );
                } else if (messageText.startsWith("/profile")) {
                    // Mendapatkan informasi pengguna dari database
                    String userInfo = getUserInfo(ChatId);
                    sendMessage(ChatId, userInfo);
                } else if (messageText.startsWith("/lapor")) {
                    // Mendapatkan informasi pengguna dari database
                     saveMessage(messageText, "out", "out");
                   sendMessage(ChatId, "Laporan Di terima " );
                } else if (messageText.startsWith("/alamat")) {
                    // Mendapatkan informasi pengguna dari database
                    saveMessage(messageText, "out", "out");
                   sendMessage(ChatId, "Tinggal di Semarang " );
                }else if (messageText.startsWith("/pembuat")) {
                    // Mendapatkan informasi pengguna dari database
                    saveMessage(messageText, "out", "out");
                   sendMessage(ChatId, "dibuat oleh Fadil " + "\n NIM :A11.2021.13925");
                }else {
                    // Menyimpan pesan pengguna ke database
//                    saveMessage(chatId, messageText);
//                    sendMessage(chatId, "Bot tidak mengenali perintah ");
                    String response = processMessage(messageText);
                    sendResponse(ChatId, response);
                    saveMessage(messageText, "out", "out");
                }
            }else{
                sendMessage(ChatId, "Anda belum melakukan Registerasi. Silahkan Registrasi dengan cara /Daftar username");
            }         
        }
    }

    private void startConversation(long chatId) {
        String welcomeMessage = "Welcome to the Fadil Bot! "
                + "\n Anda dapat menggunakan beberapa perintah seperti di bawah"
                + "\n-------------------------------"
                 + "\n | /hallo untuk menyapa chat bot                |"    
                + "\n | /Daftar username untuk register user                  |"            
                + "\n | /profile untuk name anda melihat username anda dan id anda|"
                + "\n | /lapor anda ingin Lapor                                 |"
                + "\n | /alamat tinggal dimana?                                 |"
                + "\n | /pembuat akan memberitahu yang membuat chatbot          |"
                +"\n-------------------------------"
                + "\n semua isi chat dari user  akan otomatis masuk ke database ";
        sendMessage(chatId, welcomeMessage);
    }

    private void saveMessage(long ChatId,String message, String text, String out) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO message (user_id, message, Date) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            java.util.Date tanggal = new java.util.Date();
            java.text.SimpleDateFormat TanggalFormat= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String CreateDate=TanggalFormat.format(tanggal);
            statement.setLong(1, ChatId);
            statement.setString(2, message);
            statement.setString(3, CreateDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private String registerUser(long ChatId, String username) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM user WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, ChatId);
            ResultSet resultSet = statement.executeQuery();
            java.util.Date tanggal = new java.util.Date();
            java.text.SimpleDateFormat TanggalFormat= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String CreateDate=TanggalFormat.format(tanggal);

            if (resultSet.next()) {
                return "User Sudah mendaftar";
            } else {
                String query1 = "INSERT INTO user (user_id , username , CreateDate) VALUES (?, ?, ?)";
                PreparedStatement statement1 = connection.prepareStatement(query1);
                statement1.setLong(1, ChatId);
                statement1.setString(2, username);
                statement1.setString(3,CreateDate );
                statement1.executeUpdate();
                return "Regsitrasi Berhasil"
                        + "untuk memulai percakapan komen /mulai" ;
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
//    private String updateUser(long ChatId, String username) {
//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
//            String query = "UPDATE user SET user WHERE user_id = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setLong(1, ChatId);
//            ResultSet resultSet = statement.executeQuery();
//            java.util.Date tanggal = new java.util.Date();
//            java.text.SimpleDateFormat TanggalFormat= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String CreateDate=TanggalFormat.format(tanggal);
//
//            if (resultSet.next()) {
//                return "User Sudah Update";
//            } else {
//                String query1 = "UPDATE user SET (user_id, username, CreateDate) VALUES (?, ?, ?)";
//                PreparedStatement statement1 = connection.prepareStatement(query1);
//                statement1.setLong(1, ChatId);
//                statement1.setString(2, username);
//                statement1.setString(3,CreateDate );
//                statement1.executeUpdate();
//                return "Update Berhasil"
//                        + "untuk memulai percakapan komen /mulai" ;
//                
//            }
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
private void sendReplyMessage(long chatId, String replyText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(replyText);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private String getUserInfo(long ChatId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM user WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, ChatId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                return "berikut ini info user:\nUsername: " + username +
                        "\nID      : " +ChatId;
            } else {
                return "User not found!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void sendMessage(long ChatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(ChatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
    private void sapa(long ChatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(ChatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
    private boolean isAuthorizedUser(long ChatId) {

    ResultSet resultSet = null;
        
    try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        String query = "SELECT * FROM user WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, ChatId);
        resultSet = statement.executeQuery();

        return resultSet.next();
    } catch (SQLException e) {
        e.printStackTrace();
    } 
    return false;
    }
    
    public void sendBroadcast(String message) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            
//             String query1 = "SELECT * FROM broadcast WHERE text = ?";
//        PreparedStatement statement1 = conn.prepareStatement(query1);
//        statement1.setString(1, text);
//        ResultSet resultSet1 = statement1.executeQuery();
//            if (resultSet1.next()) {
//                String username = resultSet1.getString("text");
//                return "Bot menjawab: " +username;
//            }else{
            
            
            String query = "SELECT user_id FROM user";
            
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String ChatId = resultSet.getString("user_id");

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(ChatId);
                sendMessage.setText(message);

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            //}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            JOptionPane.showMessageDialog(null, "Broadcast berhasil dikirim");
        }
    }
    
    
    public void saveBroadcast(String message) {
       try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT id FROM broadcast";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            statement.setString(1, message);
            statement.executeUpdate();

             while (resultSet.next()) {
                String ChatId = resultSet.getString("id");

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(ChatId);

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            
//            String userInfo = getUserInfo(ChatId);
//                    sendMessage(ChatId, userInfo);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void sendResponse(long ChatId, String response) {
        SendMessage message = new SendMessage();
        message.setChatId(ChatId);
        message.setText(response);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
    private String processMessage(String messageText) {
        String response = "";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT answer FROM questions WHERE question = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, messageText);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                response = resultSet.getString("answer");
            } else {
                response = "Maaf, Pertanyaan tidak  di temukan atau terjadi kesalahan.";
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            response = "Terjadi kesalahan dalam memproses pertanyaan.";
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
         return response;
    }
//    
//     private void sendBroadcastMessage(String text) {
//        List<String> user_list = fetchUserList();
//        for (String userId : user_list) {
//            SendMessage message = new SendMessage();
//            message.setChatId(userId);
//            message.setText(text);
//            try {
//                execute(message);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//     
//      private void handleOutgoingMessage(String text) {
//        sendBroadcastMessage(text);
//        String ChatId = "Siaran";
//
//        // Simpan pesan keluar dengan user_id "outgoing"
//        saveMessage(ChatId, text, "out");
//    }
    

  
    @Override
    public String getBotUsername() {
        return "PBOfadil_bot";
    }

    @Override
    public String getBotToken() {
        return "6008817131:AAH3ZUTZEnNTljqr2JjJqYN7FcF8WQtyTz0";
    }

    private void saveMessage() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void sendBroadcast(ResultSet RsProduk) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void sendBroadcast(int executeUpdate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void saveBroadcast() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void sendBroadcast() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void saveMessage(String messageText, String out, String out1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    }

