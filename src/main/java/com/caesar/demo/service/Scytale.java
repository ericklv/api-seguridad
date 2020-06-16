package com.caesar.demo.service;

import java.util.Optional;

public class Scytale {
    //generamos la escitala, t es el flag para obtener una matriz transpuesta
    public static String[][] board (String text, int sides, boolean t){
        int rows = (int)Math.ceil(((float)text.length())/sides);
        return t?new String[sides][rows]:new String[rows][sides];
    }

    //en caso no tener un orden para leer las caras se pone un por defecto
    public static int[] checkOrder(int sides, int[] order){
        int[] order_ = new int[sides];
        if(order!=null && order.length>0) return order;

        for(int i=0; i<sides;i++) order_[i] = i;
        return order_;
    };

    //escribe en la escitala columna por columna
    public static void writeBoard (String[][] board, String text, int[] order){
        int rows = board.length;
        int order_col = 0;
        int order_row = 0;
        String[] text_ = text.split("");

        for(int i=0;i<text_.length;i++){
            board[order_row][order[order_col]] = text_[i];
            order_row++;

            if(order_row+1>rows) {
                order_row=0;
                order_col++;
            };
        }
    };

    //escribe en la escitala fila por fila
    public static void writeBoardRow (String[][] board, String text, int[] order){
        int cols = board[0].length;
        int rows = board.length;
        int order_col = 0;
        int order_row = 0;
        String[] text_ = text.split("");

        for(int i=0;i<text_.length;i++){
            board[order_row][order_col] = text_[i];
            order_row++;

            if(order_row+1>rows) {
                order_row=0;
                order_col++;
            };
        }
    };

    public static String encrypt(String text, int sides, int[] order){
        String [][] board = board(text,sides,false);
        writeBoard(board,text, order);
        return getString(board);
    };

    public static String decrypt(String text, int sides, int[] order){
        String [][] board = board(text,sides, true);
        writeBoardRow(board,text, order);
        return getStringSorted(board, order);
    };

    //lee fila por fila la escitala y concatena para generar el mensaje
    public static String getString (String[][] board){
        StringBuilder sb = new StringBuilder();
        for(String [] row: board){
            for(String c: row){
                String c_ = Optional.ofNullable(c).orElse(" ");
                sb.append(c_);
            }
        }
        return sb.toString();
    };

    public static  String getStringSorted (String[][] board, int[] order){
        StringBuilder sb = new StringBuilder();
        for(int i:order){
            for(int k=0;k<board[0].length;k++){
                String c_ =  Optional.ofNullable(board[i][k]).orElse(" ");
                sb.append(c_);
            }
        }
        return sb.toString();
    }
}
