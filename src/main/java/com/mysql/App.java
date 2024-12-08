package com.mysql;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in); // Scanner는 프로그램 전체에서 1번만 생성
        boolean condition = true;
        try {
            // MySQL 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.56.102:4567/UniversityDB",
                    "goyoungchan", "330931cks");
            System.out.println("MySQL 연결 완료!");
            while (condition) {
                System.out.println("메뉴");
                System.out.println("0. 종료\t 1.학생 추가\t 2.학생 찾기\t3.학생 삭제");

                int menu = scanner.nextInt();
                scanner.nextLine();

                switch (menu) {
                    case 1:
                        StudentOperations.InsertStudent(con, scanner);
                        break;
                    case 2:
                        StudentOperations.FindStudent(con, scanner);
                        break;
                    case 3:
                        StudentOperations.DeleteStudent(con,scanner);
                        break;
                    case 0:
                        System.out.println("프로그램을 종료합니다.");
                        con.close();
                        condition = false;
                        break;
                    default:
                        System.out.println("올바른 메뉴를 선택하세요.");
                }
            }

        } catch (SQLException e) {
            System.out.println("MySQL 연결 실패: " + e.getMessage());
        }

        scanner.close();
    }
}
